package com.yu.email.test;

import com.yu.email.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class Test1 {
    String[] user=new String[]{"zhouanyuxx@outlook.com"};
    @Autowired
    private EmailService emailService;

    @Test
    public void testEmailService(){
        String s = emailService.sendEmail("你好", "第二次实验",user );
        System.out.println(s);
    }

    @Test
    public void testHtmlEmailService(){
        File f=new File("D:\\Huawei Share\\Screenshot\\capture_20220910223836649.bmp");
        Map<String,File> files=new HashMap<>();
        files.put("img",f);
        emailService.sendHtmlEmail("你好HtmlEmail",
               "<!DOCTYPE html>\n" +
                       "<html lang=\"zh\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                       "<head>\n" +
                       "    <meta charset=\"UTF-8\">\n" +
                       "    <title>真高兴看见你</title>\n" +
                       "</head>\n" +
                       "<body>\n" +
                       "    你好\n" +
                       "<img src='cid:img'/>"+
                       "</body>\n" +
                       "</html>",user,files);
    }
    @Test
    public void test1(){
        File[] f=null;
        for (File file : f) {
            System.out.println(file);
        }
    }
}
