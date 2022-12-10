package com.springboard.webboard.service;

import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements MailService{

    @Autowired
    JavaMailSender emailSender;

    public static String authNum = null;

    private MimeMessage createMessage(String to) throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);
        message.setSubject("어니언 웹사이트 회원가입 인증메일");
        authNum = createKey();

        String body = "어니언 웹사이트에 가입해주셔서 감사합니다.\n\n" +
                "[인증번호] " + authNum + " 입니다."; //메일 발송시 내용 작성

        message.setText(body);//내용
        message.setFrom(new InternetAddress("dkstkddjs318@gmail.com","어니언 웹 서비스"));//보내는 사람


        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) {
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    @Override
    public String sendSimpleMessage(String to)throws Exception {
        MimeMessage message = createMessage(to);
        try{
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return authNum;
    }
}