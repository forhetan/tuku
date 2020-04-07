package cn.picturecool.utils.md5;

import java.security.MessageDigest;

public class HideMD5 {
    public static final String KEY_MD5="MD5";
    //盐
    private static final String NaCl="*#06#";

    public static String getMD5(String text) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            // 计算md5函数
            String NaClText = text + NaCl;
            md.update(NaClText.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return hex(md.digest());
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public static String getMD5Text(String text) throws Exception {
        //MD5二次加密
        return HideMD5.getMD5(HideMD5.getMD5(text));
    }

    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

}
