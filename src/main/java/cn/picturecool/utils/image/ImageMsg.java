package cn.picturecool.utils.image;

import cn.picturecool.utils.image.helper.ImageBinary;
import cn.picturecool.utils.image.helper.ImageRead;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageMsg {

    public final static int WIDTH = 8;
    public final static int HEIGHT = 8;

    /*
     * 对传入的图片文件名列表进行图片读取，并返回hash码的List
     * */
    public static List<String> getImageHash(List<String> files) {
        List<String> imageMsgs = new ArrayList<String>();
        for (String filename : files) {
            BufferedImage source = ImageRead.readImage(filename);// 读取文件
            imageMsgs.add(ImageMsg.getImageHash(source));
        }
        return imageMsgs;
    }

    public static String getImageHash(String filename) {
        BufferedImage source = ImageRead.readImage(filename);// 读取文件
        return ImageMsg.getImageHash(source);
    }

    public static String getImageHash(File inputFile) {
        BufferedImage source = ImageRead.readImage(inputFile);// 读取文件
        return ImageMsg.getImageHash(source);
    }

    public static String getImageHash(BufferedImage source) {
        // 第一步，缩小尺寸。
        BufferedImage bufferedImage = ImageZoom.zoom(source, WIDTH, HEIGHT, false);
        // 第二步，简化色彩。
        List<Integer> pixelArrray = ImageBinary.getGrayscale(bufferedImage);
        int avgPixel = ImageBinary.average(pixelArrray);
        // 比较像素的灰度
        List<Integer> comps = new ArrayList<Integer>();
        for (Integer i : pixelArrray) {
            if (i >= avgPixel) {
                comps.add(1);
            } else {
                comps.add(0);
            }
        }
        // 第三步，计算平均值。
        // 第四步，比较像素的灰度。
        // 第五步，计算哈希值。
        StringBuffer hashCode = new StringBuffer();
        for (int i = 0; i < comps.size(); i += 4) {
            int result = comps.get(i) * (int) Math.pow(2, 3) + comps.get(i + 1) * (int) Math.pow(2, 2) + comps.get(i + 2) * (int) Math.pow(2, 1) + comps.get(i + 3);
            hashCode.append(ImageBinary.binaryToHex(result));
        }
        // 得到指纹以后，就可以对比不同的图片，看看64位中有多少位是不一样的。
        return hashCode.toString();
    }
}
