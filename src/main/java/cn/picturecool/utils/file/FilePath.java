package cn.picturecool.utils.file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: pictureCool
 * @description: 获取图库初始路径，及想相关的系统信息
 * @author: 赵元昊
 * @create: 2020-02-15 18:33
 **/
public class FilePath {
    public static final String PictureCool = "picture cool";
    public static final String TempPath = "temp";
    public static final String IMAGE = "image";
    public static final String Picture = "picture";
    public static final String Logs = "logs";
    public static final String Avatar = "avatar";
    public static final String Thumbnail_SUFFIX = "temp";
    public static final String PicturePath_KEY = "picturePath";
    public static final String ThumbnailPath_KEY = "thumbnailPath";


    /*public static Map<String, String> getTargetPath(String hashMsg) {
        Map<String, String> map = new HashMap<>();
        map.put(PicturePath_KEY, getPicturePath(hashMsg));
        map.put(ThumbnailPath_KEY, getThumbnailPath(hashMsg));
        return map;
    }*/
    public static Map<String, String> getTargetPath(String hashMsg) {
        Map<String, String> map = new HashMap<>();
        map.put(PicturePath_KEY, getPicturePath(hashMsg));
        map.put(ThumbnailPath_KEY, getThumbnailPath(hashMsg));
        return map;
    }

    public static boolean isImage(String contentType) {
        if (getFileContent(contentType).equals(IMAGE)) {
            return true;
        }
        return false;
    }

    public static String getFileContent(String contentType) {
        System.out.println(contentType.split("/")[0]);
        return contentType.split("/")[0];
    }

    public static String getSuffix(String contentType) {
        System.out.println(contentType.split("/")[1]);
        return contentType.split("/")[1];
    }

    public static String getTempPath() {
        String tempPath = getDefaultPath()
                + File.separator
                + TempPath;
        return tempPath;
    }

    public static String getLogsPath() {
        String logsPath = getDefaultPath()
                + File.separator
                + Logs;
        return logsPath;
    }

    public static String getAvatarPath() {
        String avatarPath = getDefaultPath()
                + File.separator
                + Avatar;
        return avatarPath;
    }

    public static String getDefaultPath() {
        String defaultPath = null;
        if (isWindows()) {
            defaultPath =
                    System.getProperty("user.home")
                            + File.separator
                            + PictureCool;
        } else {
            defaultPath =
                    System.getProperty("user.home")
                            + File.separator
                            + PictureCool;
        }

        return defaultPath;
    }

    public static String getThumbnailPath(String hashPath) {

        String msgFirstChar = hashPath.substring(0, 1);
        String filePath = getDefaultPath()
                + File.separator
                + Picture
                + File.separator
                + msgFirstChar
                + File.separator
                + hashPath
                + File.separator
                + Thumbnail_SUFFIX;

        return filePath;
    }

    public static String getPicturePath(String hashPath) {

        String msgFirstChar = hashPath.substring(0, 1);
        String filePath = getDefaultPath()
                + File.separator
                + Picture
                + File.separator
                + msgFirstChar
                + File.separator
                + hashPath;

        return filePath;
    }

    public static String getOsName() {
        String osName = System.getProperty("os.name").split(" ")[0];
        if (osName.equals("Windows")) {
            return "Windows";
        } else if (osName.equals("Linux")) {
            return "Linux";
        }
        return null;
    }

    public static boolean isWindows() {
        if (getOsName().equals("Windows")) {
            return true;
        }
        return false;
    }

}
