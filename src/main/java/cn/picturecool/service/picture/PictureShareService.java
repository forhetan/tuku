package cn.picturecool.service.picture;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-04-06 10:03
 **/
public interface PictureShareService {
    List<String> getUniqueHashList(String fileName) throws IOException;

    int saveUniqueHashList(String fileName, List<String> list) throws IOException;

    boolean hasUniqueHashList(String fileName);
}
