package cn.picturecool.controller.gallery;

import cn.picturecool.DTO.PictureGalleryDTO;
import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.authorization.annotation.Authorization;
import cn.picturecool.authorization.annotation.CurrentUser;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.picture.PictureGalleryService;
import cn.picturecool.service.user.PictureUserService;
import cn.picturecool.utils.id.SnowFlake;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tuku
 * @description: 图库控制器
 * @author: 赵元昊
 * @create: 2020-02-25 18:56
 **/
@RestController
public class GalleryController {

    @Autowired
    private PictureGalleryService pictureGalleryService;
    @Autowired
    private PictureUserService pictureUserService;

    @Authorization
    @PostMapping("/gallery")
    public RespBean createGallery(@CurrentUser PictureUserDTO pictureUserDTO,
                                  @RequestParam("galleryName") String galleryName,
                                  @RequestParam("description") String description) {

        if (galleryName != null) {
            PictureGalleryDTO pictureGalleryDTO = PictureGalleryDTO.builder()
                    .galleryId(new SnowFlake(1, 1).nextId())
                    .galleryName(galleryName)
                    .pictureTotal(0)
                    .createDate(LocalDateTime.now())
                    .description(description)
                    .userId(pictureUserDTO.getUserId())
                    .build();
            int galleryTotal = pictureUserDTO.getGalleryTotal() + 1;
            pictureUserService.updateGalleryTotal(pictureUserDTO.getUserId(), galleryTotal);
            pictureGalleryService.insertGallery(pictureGalleryDTO);
            return RespBean.ok("创建成功", pictureGalleryDTO);
        } else {
            return RespBean.error("相册名非法！");
        }
    }

    @Authorization
    @GetMapping("/gallery")
    public RespBean getUserAllGallery(@CurrentUser PictureUserDTO pictureUserDTO) {
        List<PictureGalleryDTO> galleryDTOList = pictureGalleryService.findGalleryListByUserId(pictureUserDTO.getUserId());
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("gallery", galleryDTOList);
        return RespBean.ok("用户gallery信息", map);
    }

/*    @GetMapping("/gallery/{id}")
    public RespBean getGallery(@PathVariable("id") String id) {

        return RespBean.ok("创建成功！");
    }

    @PutMapping("/gallery")
    public RespBean insertGallery() {

        return RespBean.ok("创建成功！");
    }

    @DeleteMapping("/gallery")
    public RespBean deleteGallery() {

        return RespBean.ok("创建成功！");
    }*/
}
