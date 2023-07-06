package com.yu.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ClassUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@SpringBootTest
@Slf4j
public class ScreenshotTest {

    @Test
    public void test(){
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        String fileName = null ;
        try {

            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(500,500);
            // 捕获屏幕
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);
            //存放位置
            String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
            fileName = System.currentTimeMillis() + ".png" ;
            log.info("屏幕截屏路径：{}", path);
            // 把捕获到的屏幕 输出为 图片
            ImageIO.write(screenCapture, "png", new File(path +File.separator + fileName));

        } catch (Exception e) {
            log.error("获取屏幕截屏发生异常", e);
        }

    }
}
