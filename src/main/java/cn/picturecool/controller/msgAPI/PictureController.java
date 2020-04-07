/*
package cn.picturecool.controller.msgAPI;

import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.picture.PictureMainService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @program: tuku
 * @description: 图片查询
 * @author: 赵元昊
 * @create: 2020-03-23 16:31
 **//*

@RestController
public class PictureController {
    @Autowired
    private PictureMainService pictureMainService;

    @GetMapping("/style/phone")
    public RespBean getPictureByPhone() {
        List<PictureMainDTO> list = pictureMainService.selectAllByLabel("手机");
        return RespBean.ok("", list);
    }

    @GetMapping("/style/scenery")
    public RespBean getPictureByScenery() {
        List<PictureMainDTO> list = pictureMainService.selectAllByLabel("风景");
        return RespBean.ok("", list);
    }

    @GetMapping("/style/anime")
    public RespBean getPictureByAnime() {
        List<PictureMainDTO> list = pictureMainService.selectAllByLabel("动漫");
        return RespBean.ok("", list);
    }

    @GetMapping("/style/design")
    public RespBean getPictureByDesign() {
        List<PictureMainDTO> list = pictureMainService.selectAllByLabel("设计");
        return RespBean.ok("", list);
    }

    @GetMapping("/style/people")
    public RespBean getPictureByPeople() {
        List<PictureMainDTO> list = pictureMainService.selectAllByLabel("人物");
        return RespBean.ok("", list);
    }

    @GetMapping("/style/text")
    public RespBean getPictureByText() {
        List<PictureMainDTO> list = pictureMainService.selectAllByLabel("文字");
        return RespBean.ok("", list);
    }

    @GetMapping("/style/hot")
    public RespBean getPictureByHot() {
        List<PictureMainDTO> list = pictureMainService.selectAllByHot();
        return RespBean.ok("", list);
    }

    @GetMapping("/style/time")
    public RespBean getPictureByTime() {
        List<PictureMainDTO> list = pictureMainService.selectAllByTime();
        return RespBean.ok("", list);
    }

    @GetMapping("/style/phone/{size}/{page}")
    public RespBean getPictureByPhonePage(@PathVariable int size,
                                          @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size,page,"手机");
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("total",(Long)iPage.getTotal());
        map.put("pages",iPage.getPages());
        map.put("position",page);
        map.put("list",iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/style/scenery/{size}/{page}")
    public RespBean getPictureBySceneryPage(@PathVariable int size,
                                            @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size,page,"风景");
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("total",(Long)iPage.getTotal());
        map.put("pages",iPage.getPages());
        map.put("position",page);
        map.put("list",iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/style/anime/{size}/{page}")
    public RespBean getPictureByAnimePage(@PathVariable int size,
                                          @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size,page,"动漫");
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("total",(Long)iPage.getTotal());
        map.put("pages",iPage.getPages());
        map.put("position",page);
        map.put("list",iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/style/design/{size}/{page}")
    public RespBean getPictureByDesignPage(@PathVariable int size,
                                           @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size,page,"设计");
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("total",(Long)iPage.getTotal());
        map.put("pages",iPage.getPages());
        map.put("position",page);
        map.put("list",iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/style/people/{size}/{page}")
    public RespBean getPictureByPeoplePage(@PathVariable int size,
                                           @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size,page,"人物");
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("total",(Long)iPage.getTotal());
        map.put("pages",iPage.getPages());
        map.put("position",page);
        map.put("list",iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/style/text/{size}/{page}")
    public RespBean getPictureByTextPage(@PathVariable int size,
                                         @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size,page,"文字");
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("total",(Long)iPage.getTotal());
        map.put("pages",iPage.getPages());
        map.put("position",page);
        map.put("list",iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/style/hot/{size}/{page}")
    public RespBean getPictureByHotPage(@PathVariable int size,
                                        @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByHotPage(size,page);
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("total",(Long)iPage.getTotal());
        map.put("pages",iPage.getPages());
        map.put("position",page);
        map.put("list",iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/style/time/{size}/{page}")
    public RespBean getPictureByTimePage(@PathVariable int size,
                                         @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByTimePage(size,page);
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("total",(Long)iPage.getTotal());
        map.put("pages",iPage.getPages());
        map.put("position",page);
        map.put("list",iPage.getRecords());
        return RespBean.ok("", map);
    }

    public RespBean getPictureByLabel() {
        return null;
    }
}
*/
