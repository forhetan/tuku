package cn.picturecool.service.picture.imp;

import cn.picturecool.service.picture.PictureShareService;
import cn.picturecool.utils.file.FilePath;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-04-06 10:12
 **/
@Service
public class PictureShareServiceImp implements PictureShareService {
    @Override
    public List<String> getUniqueHashList(String fileName) throws IOException {
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        File newFile = new File(FilePath.getLogsPath()
                + File.separator
                + fileName
                + ".txt");
        if (newFile.exists()) {
            List<String> list = new ArrayList<>();
            fileReader = new FileReader(newFile);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {  //按行读取文件流的内容
                list.add(line);
            }
            bufferedReader.close();
            fileReader.close();
            return list;
        } else {
            return null;
        }
    }

    @Override
    public int saveUniqueHashList(String fileName, List<String> list) throws IOException {
        File newFile = new File(FilePath.getLogsPath()
                + File.separator
                + fileName
                + ".txt");
        File path = new File(FilePath.getLogsPath());
        if (!path.exists()) {
            path.mkdirs();
        }
        if (!newFile.isFile()) {
            newFile.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile, true));
        for (String uniqueHash : list) {
            String destStr = uniqueHash + "\r\n";
            char[] chars = destStr.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                bufferedWriter.append(chars[i]);
            }
        }
        bufferedWriter.close();
        return list.size();
    }

    @Override
    public boolean hasUniqueHashList(String fileName) {
        File newFile = new File(FilePath.getLogsPath()
                + File.separator
                + fileName
                + ".txt");
        if (newFile.exists()) {
            return true;
        } else {
            return false;
        }
    }
}
