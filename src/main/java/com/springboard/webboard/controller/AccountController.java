package com.springboard.webboard.controller;

import com.springboard.webboard.config.SessionUser;
import com.springboard.webboard.dto.UserDto;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.UserRepository;
import com.springboard.webboard.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Variable;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private HttpSession httpSession;

    @GetMapping("/login")
    public String login(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null) {
            model.addAttribute("username", user.getUsername());
        }
        return "account/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userDto", new User());
        return "account/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserDto userDto,
                           BindingResult bindingResult,
                           Model model
                           ){
        if(bindingResult.hasErrors()){
            model.addAttribute("userDto", userDto);
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_"+error.getField(), error.getDefaultMessage());
                log.info("error message : "+error.getDefaultMessage());
            }
            return "/account/register";

        }else {
            userService.processNewUser(userDto);
            model.addAttribute("message", "회원가입이 완료되었습니다.");
            model.addAttribute("searchUrl", "/");
            return "redirect:/";
        }
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            model.addAttribute("error", "wrong.email");
            return "account/checked-email";
        }

        if(!user.getEmailCheckToken().equals(token)) {
            model.addAttribute("error", "wrong.token");
            return "account/checked-email";
        }

        user.setEmailVerified(true);
        model.addAttribute("username", user.getUsername());

        return "account/checked-email";
    }



    @RequestMapping("/kakao/login")
    public ModelAndView login(String code) {
        //카카오 서버가 인자로 전달해준 인증코드가 code라는 변수로 받는다.
        //System.out.println("인증코드 : " + code);

        //카카오서버에서 인증코드를 전달해 주는곳
        ModelAndView mv = new ModelAndView();

        //받은 코드를 가지고 2번째 요청인 토큰을 요청하여 받기위한 작업
        String access_token = "";
        String refresh_token = "";
        //요청하고자하는 서버의 카카오url
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            //웹상의 경로를 객체화 시킨다.
            URL url = new URL(reqURL);

            //웹상의 경로와 연결한다.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST방식으로 요청하기 위해 setDoOutput을 true로 지정해줘야한다.
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            //System.out.println("여기까지올려나");

            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            //전달하고자하는 파라미터들을 보낼 OutputStream준비
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            //파라미터 4개를 만들어서 bw를 통해 카카오서버로 보낸다.
            //파라미터들을 담을 문자열 생성!
            //grant_type=authorization_code&client_id=개인키값...등등보내야한다
            StringBuffer sb = new StringBuffer();
            sb.append("grant_type=authorization_code"); // 이건 문서에서 넣으라해서 넣은거
            sb.append("&client_id=91e81f913533b4cab6ce6fd589cdbfd8");  //본인이 발급받은 key
            sb.append("&redirect_uri=http://localhost:8090/account/kakao/login");   // 본인이 설정해 놓은 경로
            sb.append("&code="+code); // 파라미터로 받은 String code
//            sb.append("&code=").append(code); // 파라미터로 받은 String code

            //연결된 카카오서버(bw로 연결함)로 준비된 파라미터들을 전달!!
            // 왜전달하냐 카카오해서 보내달래!!
            bw.write(sb.toString());
            bw.flush();

            //    결과코드가 200이라면 성공
            int res_Code = conn.getResponseCode();
            // System.out.println("res_Code : " + res_Code);

            if(res_Code == 200) {
                //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
                //response택스트를 얻기위해 이 친구를 준비했다!
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuffer result = new StringBuffer();
                String line = null;

                //한줄단위로 읽어서 result라는 StringBuffer에 적재를 해보자!
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                // 현재 result는 하나의 문자열로 인식을한다.
                // 물론 꺼내서 서브스트링을 이용해서 하나씩 잘라서 사용할 수 있지만 너무 힘들자나
                // 차라리 이 문자열 자체를 json으로 인식을 해버리게 하는거야
                // 그러면 엑세스토큰 값을 주세요 ! 리프레쉬토큰 ㄱ밧을 주세요 !!
                // 하면 바로 나올수 있게 편하게 접근하는 방법을 알아보자
                //System.out.println("result : " +result.toString());

                //잭슨은 언제 쓰나
                // 비동기식 통신이든 나한테 누가 요청을 해 그러면 그 값을
                // 제이슨으로 던져주고 ㅅ싶을때!
                // 내가가지고 싶은 자원을 제이슨으로 던져주고싶을때 쓰고싶은게 젝슨이다!

                //JSON파싱 처리
                //"access_token" "refresh_token" 이 두가지가 필요하다!
                // 카카오api요청을 한 후
                // ModelAndView로 저장한 후 result.jsp로 이동하여 결과를 표현한다.
                // 이놈이 JSON표현식의 값이 하나의 문자열로 되어있는것을
                // JSON객체로 변환해주는 라이브러리!
                // result.toString() --- > JSON 객체! 이렇게해야 토큰값 두가지를 얻어낼 수 있다.
                //**************문자열로 넘어온 제이슨형식의 택스트를 내가원하는 값만 뽑아내기!************************
                JSONParser pars = new JSONParser();

                Object obj = pars.parse(result.toString());
                JSONObject json = (JSONObject) obj;

                access_token = (String) json.get("access_token");
                refresh_token = (String) json.get("refresh_token");

                //System.out.println("access_token : "+access_token);
                //System.out.println("refresh_token : "+refresh_token);
                //**********************************************************

                //마지막 3번째 호출은 사용자 정보요청!!

                String header = "Bearer "+access_token;
                String apiURL = "https://kapi.kakao.com/v2/user/me";

                // 자바객체에서 특정 웹상의 경로를 호출하기위해서는 먼저 URL생성
                URL url2 = new URL(apiURL);

                // 위에 경로를 가지고 커넥션 준비
                HttpURLConnection  conn2 = (HttpURLConnection) url2.openConnection();

                // 커넥션 설정
                conn2.setRequestMethod("POST");
                conn2.setDoOutput(true);

                // 헤더친구를 보내야 하기에 배관하나 준비해주자 문서에서 보내래
                conn2.setRequestProperty("Authorization", header);

                res_Code = conn2.getResponseCode(); // 200이라면 성공한거다
                //System.out.println("res_code : "+ res_Code + "/" +HttpURLConnection.HTTP_OK);

                if(res_Code == HttpURLConnection.HTTP_OK) {
                    //3번쨰 요청에 성공했다면...

                    // 카카오 서버쪽에서 사용자의 정보를 보냈다
                    // 이것을 읽어와서 필요한 정보만쏙쏙빼보자잉
                    BufferedReader brd = new BufferedReader(new InputStreamReader(conn2.getInputStream()));

                    StringBuffer res = new StringBuffer();
                    String str = null;

                    while ((str = brd.readLine()) != null) {
                        res.append(str);
                    }
                    //System.out.println("res : "+res.toString());
                    //카카오서버에서 전달되는 모든 값들이 res에 누적되었다.

                    //받은 객체를 json객체로 변환한다.
                    obj = pars.parse(res.toString());
                    json = (JSONObject) obj;

                    //변환된 josn객체 안에서 다시 json객체로 얻어내야 하는 것이
                    // 바로 "properties"라는 속성이다.
                    JSONObject props = (JSONObject) json.get("properties");

                    //System.out.println("props : "+ props);

                    String nickName = (String) props.get("nickname");
                    String p_img = (String) props.get("profile_image");

                    JSONObject kakao_acc = (JSONObject) json.get("kakao_account");
                    String email = (String) kakao_acc.get("email");
                    System.out.println("email : " + email);

                    JSONObject profile = (JSONObject) kakao_acc.get("profile");
                    nickName = (String) profile.get("nickname");
                    p_img = (String) profile.get("profile_image_url");
                    System.out.println("nickName : " + nickName);
                    System.out.println("p_img : " + p_img);


                    mv.addObject("nickname", nickName);
                    mv.addObject("email" , email);
                    mv.addObject("p_img", p_img);

                }


                br.close();
                bw.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        mv.setViewName("result");
        return mv;
    }
}
