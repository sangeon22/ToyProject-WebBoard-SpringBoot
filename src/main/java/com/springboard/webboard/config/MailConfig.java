package com.springboard.webboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class MailConfig {

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.properties.mail.smtp.socketFactory.port}")
    private int socketPort;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;

    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private boolean startlls_required;

    @Value("${spring.mail.properties.mail.smtp.socketFactory.fallback}")
    private boolean fallback;

    @Value("${spring.mail.properties.AdminMail.id}")
    private String id;

    @Value("${spring.mail.properties.AdminMail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");
        return javaMailSender;
    }
    private Properties getMailProperties()
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.socketFactory.port", socketPort);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttls);
        properties.put("mail.smtp.starttls.required", startlls_required);
        properties.put("mail.smtp.socketFactory.fallback",fallback);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return properties;
    }

}