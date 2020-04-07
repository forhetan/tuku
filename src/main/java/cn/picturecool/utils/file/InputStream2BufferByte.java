package cn.picturecool.utils.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;

/**
 * @program: tuku
 * @description: 读取inputstream并返回一个byteArreyOutputStream
 * @author: 赵元昊
 * @create: 2020-02-29 11:50
 **/
public class InputStream2BufferByte {

    private ByteArrayOutputStream baos;

    public static InputStream2BufferByte copyInputStream(InputStream original) throws IOException {
        return new InputStream2BufferByte(original);
    }

    private InputStream2BufferByte(InputStream original) throws IOException {
        this.baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = original.read(buffer)) > -1 ) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
    }

    public InputStream getInputStream(){
        InputStream stream= new ByteArrayInputStream(baos.toByteArray());
        return stream;
    }

}
