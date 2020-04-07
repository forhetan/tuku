package cn.picturecool.utils.image;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * @program: pictureCool
 * @description: 生成缩略图
 * @author: 赵元昊
 * @create: 2020-02-16 13:26
 **/
public class ImageThumbnail {

    public final static int HAMMING_WIDTH = 8;
    public final static int HAMMING_HEIGHT = 8;
    public final static int Thumbnail_WIDTH = 1280;
    public final static int Thumbnail_HEIGHT = 720;
    public final static int SCALE_16 = 16;
    public final static int SCALE_9 = 9;


    public static BufferedImage getThumbnailMin(BufferedImage bufferedImage) {
        bufferedImage = getCropBufferedImage(bufferedImage);
        if (bufferedImage.getHeight() > bufferedImage.getWidth()) {
            return zoom(bufferedImage, Thumbnail_HEIGHT, Thumbnail_WIDTH, false);
        } else if (bufferedImage.getHeight() < Thumbnail_HEIGHT) {
            return bufferedImage;
        } else {
            return zoom(bufferedImage, Thumbnail_WIDTH, Thumbnail_HEIGHT, false);
        }
    }

    public static BufferedImage getThumbnailMid(BufferedImage bufferedImage) {
        bufferedImage = getCropBufferedImage(bufferedImage);
        if (bufferedImage.getHeight() > bufferedImage.getWidth()) {
            return zoom(bufferedImage, Thumbnail_HEIGHT, Thumbnail_WIDTH, false);
        } else if (bufferedImage.getHeight() < Thumbnail_HEIGHT) {
            return bufferedImage;
        } else {
            return zoom(bufferedImage, Thumbnail_WIDTH, Thumbnail_HEIGHT, false);
        }
    }

    public static BufferedImage getHammingThumbnail(BufferedImage source) {
        return ImageZoom.zoom(source, HAMMING_WIDTH, HAMMING_HEIGHT, false);
    }

    public static BufferedImage zoom(BufferedImage source, int width,
                                     int height, boolean b) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) width / source.getWidth();
        double sy = (double) height / source.getHeight();

        if (b) {
            if (sx > sy) {
                sx = sy;
                width = (int) (sx * source.getWidth());
            } else {
                sy = sx;
                height = (int) (sy * source.getHeight());
            }
        }

        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(width,
                    height);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(width, height, type);
        }
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    public static BufferedImage getCropBufferedImage(BufferedImage source) {
        int height = source.getHeight();
        int width = source.getWidth();
        BufferedImage cropImage = null;
        int x = 0;
        int y = 0;
        int height_scale = height * SCALE_16;
        int width_scale = width * SCALE_9;
        if ((height_scale == width_scale) || ((height * SCALE_9) == (width * SCALE_16))) {
            cropImage = source.getSubimage(x, y, width, height);
        } else if (height_scale > width_scale) {
            y = ((height_scale - width_scale) / SCALE_16) / 2;
            cropImage = source.getSubimage(x, y, width, width_scale / SCALE_16);
        } else if (height_scale < width_scale) {
            x = ((width_scale - height_scale) / SCALE_9) / 2;
            cropImage = source.getSubimage(x, y, height / SCALE_9, height);
        }
        return cropImage;
    }


}
