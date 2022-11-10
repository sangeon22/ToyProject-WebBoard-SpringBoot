package com.springboard.webboard.config.validator;

import com.springboard.webboard.web.dto.BoardDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BoardDto.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        BoardDto b = (BoardDto) obj;

        if (StringUtils.isEmpty(b.getContent())){
            errors.rejectValue("content","key","내용을 입력하세요.");
        }

        if (StringUtils.isEmpty(b.getTitle())){
            errors.rejectValue("title","key","제목을 입력하세요.");
        }

    }
}
