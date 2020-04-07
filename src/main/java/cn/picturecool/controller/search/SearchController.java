package cn.picturecool.controller.search;

import cn.picturecool.DTO.PictureLinkDTO;
import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.picture.PictureMainService;
import cn.picturecool.service.picture.imp.PictureLinkServiceImp;
import cn.picturecool.utils.file.FilePath;
import cn.picturecool.utils.file.PictureBeanHelper;
import cn.picturecool.utils.image.sort.HashSimilarity;
import cn.picturecool.utils.image.sort.HashSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tuku
 * @description: 汉明相似度搜索、关键词搜索
 * @author: 赵元昊
 * @create: 2020-03-09 20:23
 **/
@RestController
public class SearchController {
    @Autowired
    private PictureMainService pictureMainService;
    @Autowired
    private PictureLinkServiceImp pictureLinkServiceImp;

    @PostMapping("/search")
    public RespBean selectOriginal(@RequestParam("file") MultipartFile file) throws IOException {
        if (FilePath.isImage(file.getContentType())) {
            PictureBeanHelper pictureBeanHelper = PictureBeanHelper.init(file.getInputStream());
            List<String> hammingHashList = new ArrayList<>();
            List<PictureMainDTO> pictureMainDTOList = pictureMainService.selectAll();
            for (PictureMainDTO pictureMainDTO : pictureMainDTOList) {
                hammingHashList.add(pictureMainDTO.getHammingHash());
            }
            Map<String, Integer> sortMap =
                    HashSort.getSortMap(HashSimilarity.getSimilarity(hammingHashList, pictureBeanHelper.getMsgHash()), 60);
            List<PictureMainDTO> destList = new ArrayList<>();
            List<URL> urlList = new ArrayList<>();
            PictureMainDTO pictureMain = null;

            /*for (Map.Entry<String, Integer> entry : sortMap.entrySet()) {
                for (PictureMainDTO pictureMainDTO : pictureMainDTOList) {
                    if (entry.getKey() == pictureMainDTO.getHammingHash()) {
                        if (pictureMain != null) {
                            if ((pictureMain.getHammingHash() == pictureMainDTO.getHammingHash())
                                    && (pictureMain.getFileSize() < pictureMainDTO.getFileSize())) {
                                pictureMain = pictureMainDTO;
                            } else if ((pictureMain.getHammingHash() != pictureMainDTO.getHammingHash())) {
                                destList.add(pictureMain);
                                pictureMain = pictureMainDTO;
                            }
                        } else {
                            pictureMain = pictureMainDTO;
                        }
                    }
                }
            }*/
            for (Map.Entry<String, Integer> entry : sortMap.entrySet()) {
                for (PictureMainDTO pictureMainDTO : pictureMainDTOList) {
                    if (entry.getKey() == pictureMainDTO.getHammingHash()) {
                        destList.add(pictureMainDTO);
                        /*urlList.add(new URL("http://localhost:9999/picture/max/" + pictureMainDTO.getUniqueHash()));*/
                    }
                }
            }
            /*return RespBean.ok("查找成功", urlList);*/
            return RespBean.ok("查找成功", destList);
        }
        return RespBean.error("出错了");
    }

    @GetMapping(value = "/search/like/{word}")
    public RespBean selectByWord(@PathVariable String word) {
        System.out.println(word);
        if (!word.equals("")) {
            String[] keyWord = word.split(" ");
            List<List<PictureLinkDTO>> lists = new ArrayList<>();
            List<PictureLinkDTO> linkDTOS = new ArrayList<>();
            for (int i = 0; i < keyWord.length; i++) {
                if (!keyWord[i].equals("")) {
                    System.out.println(keyWord[i]);
                    lists.add(pictureLinkServiceImp.findLinkByWord(keyWord[i]));
                }
            }
            for (List<PictureLinkDTO> list : lists) {
                for (PictureLinkDTO pictureLinkDTO : list) {
                    if (!linkDTOS.contains(pictureLinkDTO)) {
                        linkDTOS.add(pictureLinkDTO);
                    }
                }
            }
            if (linkDTOS.size() > 0) {
                Map<Object, Object> map = new LinkedHashMap<>();
                map.put("total", linkDTOS.size());
                map.put("picture", linkDTOS);
                return RespBean.ok("相关图片信息", map);
            } else {
                return RespBean.ok("未查找到相关图片！");
            }
        }
        return RespBean.error("关键词错误");
    }

    @GetMapping(value = {"/search/match/{word}","/search/{word}"})
    public RespBean selectMatchByWord(@PathVariable String word) {
        if (!word.equals("")) {
            String[] keyWord = word.split(" ");
            List<List<PictureLinkDTO>> lists = new ArrayList<>();
            List<PictureLinkDTO> linkDTOS = new ArrayList<>();
            for (int i = 0; i < keyWord.length; i++) {
                if (!keyWord[i].equals("")) {
                    lists.add(pictureLinkServiceImp.findLinkByWord(keyWord[i]));
                }
            }
            for (List<PictureLinkDTO> list : lists) {
                if (linkDTOS.size()==0) {
                    linkDTOS = list;
                } else if (list.size() < linkDTOS.size()) {
                    linkDTOS = list;
                }
            }
            for (List<PictureLinkDTO> list : lists) {
                List<PictureLinkDTO> tempLinkDTOS = new ArrayList<>();
                for (PictureLinkDTO pictureLinkDTO : list) {
                    if(linkDTOS.contains(pictureLinkDTO)){
                        tempLinkDTOS.add(pictureLinkDTO);
                    }
                }
                linkDTOS=tempLinkDTOS;
            }
            Map<Object, Object> map = new LinkedHashMap<>();
            map.put("total", linkDTOS.size());
            map.put("picture", linkDTOS);
            return RespBean.ok("相关图片信息", map);
        }
        return RespBean.error("关键词错误");
    }
}
