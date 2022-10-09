package com.yu.utils.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class sceenshot {
    public static void main(String[] args) {
        String fileName = null;
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(screenSize.width, screenSize.height);
            // 捕获屏幕
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);
            //存放位置
            fileName = System.currentTimeMillis() + ".png";
            // 把捕获到的屏幕 输出为 图片
            ImageIO.write(screenCapture, "png", new File("D:" + File.separator + fileName));

        } catch (Exception e) {
            log.error("获取屏幕截屏发生异常", e);
        }
        RectD rd = new RectD();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
        gd.setFullScreenWindow(rd);

    }
    static class RectD extends JFrame {
        private static final long serialVersionUID = 1L;
        int orgx,orgy,endx,endy;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        BufferedImage image;
        BufferedImage tempImage;
        BufferedImage saveImage;
        Graphics g;

        @Override
        public void paint(Graphics g) {
            RescaleOp ro = new RescaleOp(0.8f, 0, null);
            tempImage = ro.filter(image, null);
            g.drawImage(tempImage, 0, 0, this);

        }

        public RectD() {
            snapshot();
            setVisible(true);//setSize(d);//最大化窗口
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                orgx = e.getX();
                orgy = e.getY();
            }
            });
            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged (MouseEvent e){
                    endx = e.getX();
                    endy = e.getY();
                    g = getGraphics();
                    g.drawImage(tempImage, 0, 0, RectD.this);
                    int x = Math.min(orgx, endx);
                    int y = Math.min(orgy, endy);
                    int width = Math.abs(endx - orgx) + 1;
                    int height = Math.abs(endy - orgy) + 1;//加上1，防止width或height为0

                    g.setColor(Color.BLUE);
                    g.drawRect(x - 1, y - 1, width + 1, height + 1);//减1，加1都是为了防止图片将矩形框覆盖掉

                    saveImage = image.getSubimage(x, y, width, height);
                    g.drawImage(saveImage, x, y, RectD.this);

                }

            });
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {//按Esc键退出
                    if (e.getKeyCode() == 27) {
                        saveToFile();
                        System.exit(0);
                    }
                }
            });
        }

        public void saveToFile() {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
            String name = sdf.format(new Date());
            File path = FileSystemView.getFileSystemView().getHomeDirectory();
            String format = "jpg";
            File f = new File("D:" + File.separator + name + "." + format);
            try {
                ImageIO.write(saveImage, format, f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void snapshot() {
            try {
                Robot robot = new Robot();
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                image = robot.createScreenCapture(new Rectangle(0, 0, d.width,
                        d.height));
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }

    }
}
