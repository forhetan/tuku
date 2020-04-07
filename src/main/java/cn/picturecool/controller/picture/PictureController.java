package cn.picturecool.controller.picture;

import cn.picturecool.DTO.AdminPictureDTO;
import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.admin.AdminPictureService;
import cn.picturecool.service.picture.PictureMainService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tuku
 * @description: 图片查询
 * @author: 赵元昊
 * @create: 2020-03-23 16:31
 **/
@RestController
public class PictureController {
    @Autowired
    private PictureMainService pictureMainService;
    @Autowired
    private AdminPictureService adminPictureService;



    /*@GetMapping("/picture/phone/{size}/{page}")
    public RespBean getPictureByPhonePage(@PathVariable int size,
                                          @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size, page, "手机");
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("total", (Long) iPage.getTotal());
        map.put("pages", iPage.getPages());
        map.put("position", page);
        map.put("list", iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/picture/scenery/{size}/{page}")
    public RespBean getPictureBySceneryPage(@PathVariable int size,
                                            @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size, page, "风景");
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("total", (Long) iPage.getTotal());
        map.put("pages", iPage.getPages());
        map.put("position", page);
        map.put("list", iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/picture/anime/{size}/{page}")
    public RespBean getPictureByAnimePage(@PathVariable int size,
                                          @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size, page, "动漫");
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("total", (Long) iPage.getTotal());
        map.put("pages", iPage.getPages());
        map.put("position", page);
        map.put("list", iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/picture/design/{size}/{page}")
    public RespBean getPictureByDesignPage(@PathVariable int size,
                                           @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size, page, "设计");
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("total", (Long) iPage.getTotal());
        map.put("pages", iPage.getPages());
        map.put("position", page);
        map.put("list", iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/picture/people/{size}/{page}")
    public RespBean getPictureByPeoplePage(@PathVariable int size,
                                           @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size, page, "人物");
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("total", (Long) iPage.getTotal());
        map.put("pages", iPage.getPages());
        map.put("position", page);
        map.put("list", iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/picture/text/{size}/{page}")
    public RespBean getPictureByTextPage(@PathVariable int size,
                                         @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size, page, "文字");
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("total", (Long) iPage.getTotal());
        map.put("pages", iPage.getPages());
        map.put("position", page);
        map.put("list", iPage.getRecords());
        return RespBean.ok("", map);
    }*/

    @GetMapping("/picture/hot/{size}/{page}")
    public RespBean getPictureByHotPage(@PathVariable int size,
                                        @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByHotPage(size, page);
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("total", (int) iPage.getTotal());
        map.put("pages", (int) iPage.getPages());
        map.put("position", page);
        map.put("list", iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/picture/time/{size}/{page}")
    public RespBean getPictureByTimePage(@PathVariable int size,
                                         @PathVariable int page) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByTimePage(size, page);
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("total", (int) iPage.getTotal());
        map.put("pages", (int) iPage.getPages());
        map.put("position", page);
        map.put("list", iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/picture/{label}/{size}/{page}")
    public RespBean getPictureByLabel(@PathVariable int size,
                                      @PathVariable int page,
                                      @PathVariable String label) {
        IPage<PictureMainDTO> iPage = pictureMainService.selectAllByLabelPage(size, page, label);
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("total", (int) iPage.getTotal());
        map.put("pages", (int) iPage.getPages());
        map.put("position", page);
        map.put("list", iPage.getRecords());
        return RespBean.ok("", map);
    }

    @GetMapping("/picture/home")
    public RespBean getHomePicture() {
        List<AdminPictureDTO> adminPictureDTOS = adminPictureService.selectAll();
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("list", adminPictureDTOS);
        return RespBean.ok("", map);
    }

}
