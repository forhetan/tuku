package cn.picturecool.utils.file;


import cn.picturecool.utils.image.BufferedImageHelper;
import cn.picturecool.utils.image.ImageMsg;
import cn.picturecool.utils.image.ImageThumbnail;
import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: pictureCool
 * @description: 根据传进来的路径创建文件夹
 * @author: 赵元昊
 * @create: 2020-02-16 11:27
 **/
public class PictureBeanHelper {

    private InputStream2BufferByte inputStream2BufferByte;
    private BufferedImage bufferedImage;
    private BufferedImage bufferedImageMID;
    private BufferedImage bufferedImageMIN;
    private String picturePixel;
    private String msgHash;
    private String fileHash;
    private String uniqueHash;
    private String basePath;

    public static boolean createFolders(String targetFolders) {
        try {
            File ds = new File(targetFolders);
            ds.mkdirs();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /*
     * 创建目标文件夹保存缩略图，并返回文件夹地址
     * */
    public static PictureBeanHelper init(InputStream inputStream) throws IOException {
        return new PictureBeanHelper(inputStream);
    }

    private PictureBeanHelper(InputStream inputStream) throws IOException {
        this.inputStream2BufferByte = InputStream2BufferByte.copyInputStream(inputStream);
        this.bufferedImage = ImageIO.read(inputStream2BufferByte.getInputStream());
        this.fileHash= DigestUtils.md5Hex(inputStream2BufferByte.getInputStream());
        this.msgHash = ImageMsg.getImageHash(BufferedImageHelper.copyBufferedImage(bufferedImage));
        this.uniqueHash = this.msgHash + "_" + this.fileHash;
        this.picturePixel=bufferedImage.getWidth()+"x"+bufferedImage.getHeight();
        this.bufferedImageMIN = ImageThumbnail.getThumbnailMin(bufferedImage);
        this.bufferedImageMID = ImageThumbnail.getThumbnailMid(bufferedImage);
        this.basePath = FilePath.getPicturePath(this.msgHash);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public BufferedImage getBufferedImageMID() {
        return bufferedImageMID;
    }

    public BufferedImage getBufferedImageMIN() {
        return bufferedImageMIN;
    }

    public String getPicturePixel() {
        return picturePixel;
    }

    public String getMsgHash() {
        return msgHash;
    }

    public String getFileHash() {
        return fileHash;
    }

    public String getUniqueHash() {
        return uniqueHash;
    }

    public String getBasePath() {
        return basePath;
    }
}
