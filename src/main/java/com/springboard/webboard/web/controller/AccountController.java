package com.springboard.webboard.web.controller;

import com.springboard.webboard.config.auth.SessionUser;
import com.springboard.webboard.web.dto.UserDto;
import com.springboard.webboard.domain.user.User;
import com.springboard.webboard.domain.user.UserRepository;
import com.springboard.webboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    private String AuthNum ="00000";
    private String mailcheck = "fff";

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
        model.addAttribute("AuthNum",AuthNum);
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

    @PostMapping("/authNum")
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
    }


    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            Model model) throws Exception {
//        @RequestParam("email") String email,
        AuthNum = Integer.toString((int) ((Math.random() * (99999 - 10000 + 1)) + 10000));

        //메일 관련 정보
        String host = "smtp.naver.com";
        final String username = "아이디"; //네이버 이메일 주소중 @ naver.com 앞주소만 작성
        final String password = "비밀번호"; //네이버 이메일 비밀번호를 작성
        int port = 465;                      //네이버 STMP 포트 번호

        //메일 내용
        String recipient = email; //메일을 발송할 이메일 주소를 기재해 줍니다.
        String subject = "어니언 웹사이트 회원가입 인증메일";    //메일 발송시 제목을 작성
        String body = "어니언 웹사이트에 가입해주셔서 감사합니다.\n" +
                "[인증번호] " + AuthNum + " 입니다."; //메일 발송시 내용 작성

        Properties props = System.getProperties();

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", host);

        //session 생성
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            String un = username;
            String pw = password;

            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(un, pw);
            }
        });
        session.setDebug(true);

        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress("tkddjsdl33@naver.com"));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(body);
        Transport.send(mimeMessage);

        mailcheck = "fasdf";
        model.addAttribute("mailcheck",mailcheck);
        return AuthNum;
    }



}