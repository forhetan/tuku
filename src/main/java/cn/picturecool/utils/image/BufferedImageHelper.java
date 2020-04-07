package cn.picturecool.utils.image;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @program: pictureCool
 * @description: 根据传入的BufferedImage创建一个新副本
 * @author: 赵元昊
 * @create: 2020-02-16 13:20
 **/
public class BufferedImageHelper {

    public static BufferedImage copyBufferedImage(BufferedImage img)
    {
        return getBufferedImage(img,img.getType());
    }

    public static BufferedImage getBufferedImage(BufferedImage img, int imageType) {
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, imageType);
        Graphics g = newImage.createGraphics();

        g.drawImage(img, 0, 0, null);

        g.dispose();

        return newImage;
    }
}
