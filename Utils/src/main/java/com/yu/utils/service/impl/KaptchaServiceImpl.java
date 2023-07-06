package com.yu.utils.service.impl;

import com.yu.utils.service.CommonUtils;
import com.yu.utils.service.KaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Service
public class KaptchaServiceImpl implements KaptchaService {

    @Autowired
    private CommonUtils utils;

    @Autowired
    private HttpServletResponse response;

    @Override
    public String render() {
        this.response.setDateHeader("Expires", 0L);
        this.response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        this.response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        this.response.setHeader("Pragma", "no-cache");
        this.response.setContentType("image/jpeg");
        Random random=new Random();

        BufferedImage image=new BufferedImage(200,60,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.cyan);
        graphics.fillRect(3,3,195,55);

        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int is= 0;is<4;is++){
            graphics.setColor(Color.blue);
            graphics.rotate(random.nextDouble(0.1),10+35*is,30.0);
            graphics.setFont(new Font("忘忧草_YS",2,50));
            char c = str.charAt(random.nextInt(str.length()));
            graphics.drawString(c+"",10+35*is,40);
        }

        graphics.rotate(0);
        for (int i = 0; i < 50; i++) {
            graphics.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            final int x=random.nextInt(200);
            final int y=random.nextInt(60);
            final int w=random.nextInt(20);
            final int h=random.nextInt(20);
            final int a=random.nextBoolean()?1:0;
            final int b=random.nextBoolean()?1:0;
            graphics.drawLine(x,y,x+a*w,y+b*h);
        }
        try {
            ImageIO.write(image,"jpg",response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "random";
    }
}
