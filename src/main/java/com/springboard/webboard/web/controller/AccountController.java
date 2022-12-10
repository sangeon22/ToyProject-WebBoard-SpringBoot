package com.springboard.webboard.web.controller;

import com.springboard.webboard.config.auth.SessionUser;
import com.springboard.webboard.service.MailService;
import com.springboard.webboard.web.dto.UserDto;
import com.springboard.webboard.domain.user.User;
import com.springboard.webboard.domain.user.UserRepository;
import com.springboard.webboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.springboard.webboard.service.EmailServiceImpl.authNum;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final MailService mailService;

    KakaoAPI kakaoApi = new KakaoAPI();

    @GetMapping("/login")
    public String login(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        return "account/login";
    }

    @RequestMapping(value="/login")
    public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        // 1번 인증코드 요청 전달
        String accessToken = kakaoApi.getAccessToken(code);
        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        System.out.println("login info : " + userInfo.toString());

        if(userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("accessToken", accessToken);
        }
        mav.addObject("userId", userInfo.get("email"));
        mav.setViewName("index");
        return mav;

    }
    @RequestMapping(value="/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        kakaoApi.kakaoLogout((String)session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/register")
    public String register(Model model, String email) {
        model.addAttribute("userDto", new User());
        model.addAttribute("email", email);
        model.addAttribute("authNum",authNum);
        return "account/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserDto userDto,
                           BindingResult bindingResult,
                           Model model,
                           String mailcheck
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
                log.info("error message : " + error.getDefaultMessage());
            }
            return "/account/register";

        }
        else {
//            if (mailcheck.equals("true")){
            userService.save(userDto);
            model.addAttribute("message", "회원가입이 완료되었습니다.");
            model.addAttribute("searchUrl", "/");
            return "message/message";
//            } else {
//                return "/account/register";
//            }
        }
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "wrong.email");
            return "account/checked-email";
        }

        if (!user.getEmailCheckToken().equals(token)) {
            model.addAttribute("error", "wrong.token");
            return "account/checked-email";
        }

        user.setEmailVerified(true);
        model.addAttribute("username", user.getUsername());

        return "account/checked-email";
    }


    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(@RequestParam String email) throws Exception {
        return mailService.sendSimpleMessage(email);
    }

//    @ResponseBody
//    public String sendEmail(@RequestParam String email,
//                            HttpServletRequest request,
//                            HttpServletResponse response,
//                            Model model) throws Exception {
//        String code = mailService.sendSimpleMessage(email);
//        log.info("인증코드 : " + code);
//        return code;
//    }

    /* @PostMapping("/authNum")
     @ResponseBody
     public boolean checkAuthNum(String authNum, UserDto userDto) {
         log.info("=================");
         log.info("=================");
         log.info("=================");
         log.info("인증코드 AuthNum = "+ AuthNum);
         log.info("=================");
         log.info("=================");
         log.info("=================");
         if (AuthNum.equals(authNum)) {
             log.info("입력코드 authNum = {}", authNum);
             log.info("동일!!");
 //            mailcheck = true;
             return userDto.setMailCheck(true);
         } else {
             log.info("authNum = {}", authNum);
             log.info("동일 XXX");
             return userDto.setMailCheck(false);
         }
     }*/

}