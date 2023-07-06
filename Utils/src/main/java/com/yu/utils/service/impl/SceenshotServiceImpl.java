package com.yu.utils.service.impl;

import com.yu.utils.service.SceenshotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class SceenshotServiceImpl implements SceenshotService {

    private int orgx, orgy, endx, endy;
    private String savePath;
    private BufferedImage image;
    private BufferedImage tempImage;
    private BufferedImage saveImage;
    private Graphics g;

    @Override
    public void sceenshotSave() {
        this.sceenshotSave("D:");
    }

    @Override
    public void sceenshotSaveAll() {
        try {
            String fileName = null;
            snapshot();
            //存放位置
            fileName = System.currentTimeMillis() + ".jpg";
            // 把捕获到的屏幕 输出为 图片
            ImageIO.write(image, "png", new File("D:" + File.separator + fileName));
        } catch (IOException e) {
            log.error("保存错误");
        }
    }

    @Override
    public void sceenshotSave(String path) {
        RectD rd = new RectD();
        savePath = path;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
        gd.setFullScreenWindow(rd);
    }

    //截取全屏
    public void snapshot() {
        try {
            Robot robot = new Robot();
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            image = robot.createScreenCapture(new Rectangle(0, 0, d.width,
                    d.height));
        } catch (AWTException e) {
            e.printStackTrace();
            log.error("获取屏幕截屏发生异常", e);
        }
    }

    //保存截图
    public void saveToFile(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
        String name = sdf.format(new Date());
        File path = FileSystemView.getFileSystemView().getHomeDirectory();
        if (format.charAt(0)=='.')
            format = format.substring(1);
        File f = new File(savePath + File.separator + name + "." + format);
        try {
            ImageIO.write(saveImage, format, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class RectD extends JFrame {
        private static final long serialVersionUID = 1L;

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
                public void mouseDragged(MouseEvent e) {
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
                        saveToFile("jpg");
                        System.exit(0);
                    }
                }
            });
        }
    }
    /**
     * 等比例放大/缩小图片
     *
     * @param fromFilePath   原始图片完整路径
     * @param saveToFilePath 缩略图片保存路径
     * @param scale          缩放比例
     * @throws Exception
     */
    private static void cutImage2(String fromFilePath, String saveToFilePath, double scale) throws Exception {
        // 校验原始图片
        File file = new File(fromFilePath);
        if (!file.isFile()) {
            throw new Exception(file + " is not image file error in cutImage!");
        }

        BufferedImage buffer = ImageIO.read(file);
        /*
         * width和height为压缩后图片的宽和高
         */
        int width = (int) (buffer.getWidth() * scale);
        int height = (int) (buffer.getHeight() * scale);

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(scale, scale), null);
        buffer = op.filter(buffer, null);
        buffer = buffer.getSubimage(0, 0, width, height);
        try {
            ImageIO.write(buffer, "jpg", new File(saveToFilePath));
        } catch (Exception ex) {
            throw new Exception(" ImageIo.write error in CreatThum.: " + ex.getMessage());
        }
    }
    /**
     * 按照指定宽高剪切图片
     *
     * @param fromFilePath   原始图片完整路径
     * @param saveToFilePath 缩略图片保存路径
     * @param width          剪切后图片的宽
     * @param height         剪切后图片的高
     * @throws Exception
     */
    private static void cutImage1(String fromFilePath, String saveToFilePath, int width, int height) throws Exception {
        // 校验原始图片
        File file = new File(fromFilePath);
        if (!file.isFile()) {
            throw new Exception(file + " is not image file error in cutImage!");
        }

        BufferedImage buffer = ImageIO.read(file);

        /*
         * 核心算法，计算图片的压缩比
         *
         * w 和 h 为原始图片的宽和高
         *
         * width 和 height 为压缩/放大后图片的宽和高
         */
        int w = buffer.getWidth();
        int h = buffer.getHeight();

        double ratiox = 1.0;
        double ratioy = 1.0;

        ratiox = w * ratiox / width;
        ratioy = h * ratioy / height;

        // 缩小图片
        if (ratiox >= 1) {
            if (ratioy < 1) {
                ratiox = height * 1.0 / h;
            } else {
                if (ratiox > ratioy) {
                    ratiox = height * 1.0 / h;
                } else {
                    ratiox = width * 1.0 / w;
                }
            }
        } else {
            // 放大图片
            if (ratioy < 1) {
                if (ratiox > ratioy) {
                    ratiox = height * 1.0 / h;
                } else {
                    ratiox = width * 1.0 / w;
                }
            } else {
                ratiox = width * 1.0 / w;
            }
        }
        /*
         * 对于图片的放大或缩小倍数计算完成，ratiox大于1，则表示放大，否则表示缩小
         */
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratiox, ratiox), null);
        buffer = op.filter(buffer, null);
        // 从放大的图像中心截图
        buffer = buffer.getSubimage((buffer.getWidth() - width) / 2, (buffer.getHeight() - height) / 2, width, height);
        try {
            ImageIO.write(buffer, "jpg", new File(saveToFilePath));
        } catch (Exception ex) {
            throw new Exception(" ImageIo.write error in CreatThum.: " + ex.getMessage());
        }
    }

}
