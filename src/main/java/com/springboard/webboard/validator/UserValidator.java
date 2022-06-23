//package com.springboard.webboard.validator;
//
//import com.springboard.webboard.entity.User;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//import org.thymeleaf.util.StringUtils;
//
//@Component
//public class UserValidator implements Validator {
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return User.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object obj, Errors errors) {
//        User u = (User) obj;
//
//        if (StringUtils.isEmpty(u.getUsername())){
//            errors.rejectValue("username","key","아이디를 입력하세요.");
//        }
//
//        if (StringUtils.isEmpty(u.getPassword())){
//            errors.rejectValue("password","key","비밀번호를 입력하세요.");
//        }
//
//        if (StringUtils.isEmpty(u.getBirth())){
//            errors.rejectValue("birth","key","생년월일을 입력하세요.");
//        }
//
//    }
//}
