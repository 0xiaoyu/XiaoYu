package com.yu.email.service;

import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Map;

public interface EmailService {

    /**
     * 发送简单邮件
     * @param titile
     * @param context
     * @param user
     * @return
     */
    String sendEmail(String titile,String context, String[] user);

    /**
     * 发送html文件
     * @param titile
     * @param context
     * @param user
     */
    void sendHtmlEmail(String titile,String context, String[] user);

    /**
     * 发送邮寄并可以携带多个附件
     * @param titile
     * @param context
     * @param user
     * @param files
     */
    void sendHtmlEmail(String titile, String context, String[] user, Map<String,File> files);

    /**
     * 发送一个附件
     * @param titile
     * @param context
     * @param user
     * @param file
     */
    void sendAttachmentsEmail(String titile, String context, String[] user, File file);

    /**
     * 发送带有模板的邮件
     * @param user
     * @param context
     * @return
     */
    String sendTemplateEmail(String[] user, Context context);

    /**
     * 发送模板验证码邮件
     * @param user
     * @param titile
     * @param time
     * @param code
     * @return
     */
    String sendTemplateEmail(String[] user, String titile, String time, String code);
}
