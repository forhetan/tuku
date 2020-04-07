package cn.picturecool.DTO.helper;

import cn.picturecool.DTO.PictureMainDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: tuku
 * @description: 生成器 生成main bean  linkbean
 * @author: 赵元昊
 * @create: 2020-03-02 20:31
 **/
public class PictureLinkBeanGenerator {

    private Map<String, Object> map = null;

    private PictureLinkBeanGenerator(Map tempMap) {
        this.map = new HashMap<>();
    }

    public static PictureLinkBeanGenerator start(Map tempMap) {
        return new PictureLinkBeanGenerator(tempMap);
    }

/*    public PictureMainDTO generatorMainBean() {
        PictureMainDTO pictureMainDTO = PictureMainDTO.builder().uniqueHash()
                .hammingHash()
                .fileHash()
                .filePath()
                .pictureStyle()
                .picturePixel()
                .fileSize()
                .likeTotal()
                .viewedTotal()
                .downloadedTotal()
                .searchedTotal()
                .fileSuffix()
                .uploadTotal()
                .lastUploadUserId()
                .adminUpload()
                .lastUploadDate()
                .weights()
                .status()
                .build();
    }*/

}
