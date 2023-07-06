package com.yu.email.service.impl;

import com.yu.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private TemplateEngine templateEngine;


    @Override
    public String sendEmail(String titile, String context, String[] user) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(username);
            message.setTo(user);
            message.setSubject(context);
            message.setText(context);
            javaMailSender.send(message);
            return "发送成功";
        }catch (Exception e) {
            log.error("发送失败");
            return "失败";
        }
    }

    @Override
    public void sendHtmlEmail(String titile, String context, String[] user) {
        this.sendHtmlEmail(titile, context, user, null);
    }

    @Override
    public void sendHtmlEmail(String titile, String context, String[] user, Map<String,File> files) {
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(user);
            helper.setText(context, true);
            helper.setSubject(titile);
            if (files != null) {
                Set<Map.Entry<String, File>> entries = files.entrySet();
                for (Map.Entry<String, File> entry : entries) {
                    helper.addAttachment(entry.getKey(), entry.getValue());
                }
            }
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendAttachmentsEmail(String titile, String context, String[] user, File file) {
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(user);
            helper.setText(context, true);
            helper.setSubject(titile);
            FileSystemResource f = new FileSystemResource(file);
            helper.addAttachment(file.getName(), f);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendTemplateEmail(String[] user, String titile, String time, String code) {
        MimeMessage message = null;
        try {
            // 处理邮件模板
            Context context = new Context();
            context.setVariable("code", code);
            context.setVariable("titile", titile);
            context.setVariable("time", time);
            this.sendTemplateEmail(user,context);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String sendTemplateEmail(String[] user, Context context) {
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(user); // 接收地址
            helper.setSubject("邮件摸板测试"); // 标题
            // 处理邮件模板
            String template = templateEngine.process("emailTemplate", context);
            helper.setText(template, true);
            javaMailSender.send(message);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


}
