/*
package cn.picturecool.controller.msgAPI;

import cn.picturecool.DTO.PictureGalleryDTO;
import cn.picturecool.DTO.PictureLikeDTO;
import cn.picturecool.DTO.PictureLinkDTO;
import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.authorization.annotation.Authorization;
import cn.picturecool.authorization.annotation.CurrentUser;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.picture.PictureGalleryService;
import cn.picturecool.service.picture.PictureLikeService;
import cn.picturecool.service.picture.imp.PictureLinkServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @program: tuku
 * @description: 用户信息获取与更改
 * @author: 赵元昊
 * @create: 2020-03-24 19:49
 **//*

@RestController
public class UserMsgController {
    @Autowired
    private PictureLikeService pictureLikeService;
    @Autowired
    private PictureGalleryService pictureGalleryService;
    @Autowired
    private PictureLinkServiceImp pictureLinkServiceImp;


    @Authorization
    @PostMapping("/msg/like")
    public RespBean selectLike(@CurrentUser PictureUserDTO pictureUserDTO) {
        List<PictureLikeDTO> likeDTOList = pictureLikeService.findLikeByUserId(pictureUserDTO.getUserId());
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("like", likeDTOList);
        return RespBean.ok("用户Like信息", map);
    }

    @Authorization
    @PostMapping("/msg/picture")
    public RespBean selectPicture(@CurrentUser PictureUserDTO pictureUserDTO) {
        List<PictureLinkDTO> linkDTOList = pictureLinkServiceImp.findLinkByUserId(pictureUserDTO.getUserId());
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("link", linkDTOList);
        return RespBean.ok("用户Link信息", map);
    }

    @Authorization
    @PostMapping("/msg/gallery")
    public RespBean selectGallery(@CurrentUser PictureUserDTO pictureUserDTO) {
        List<PictureGalleryDTO> galleryDTOList = pictureGalleryService.findGalleryListByUserId(pictureUserDTO.getUserId());
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("gallery", galleryDTOList);
        return RespBean.ok("用户gallery信息", map);
    }

    @Authorization
    @PostMapping("/msg/gallery/{galleryId}")
    public RespBean selectGalleryById(@CurrentUser PictureUserDTO pictureUserDTO,
                                      @PathVariable long galleryId) {
        PictureGalleryDTO galleryDTO = pictureGalleryService.findGalleryById(galleryId);
        if (galleryDTO.getUserId().equals(pictureUserDTO.getUserId())) {
            List<PictureLinkDTO> linkDTOList = pictureLinkServiceImp.findLinkByGalleryId(galleryId);
            Map<Object, Object> map = new LinkedHashMap<>();
            map.put("picture", linkDTOList);
            return RespBean.ok("用户相册图片信息", map);
        } else {
            return RespBean.error("用户信息错误");
        }
    }

    @Authorization
    @GetMapping("/msg/user")
    public RespBean selectUser(@CurrentUser PictureUserDTO pictureUserDTO) {
        System.out.println(pictureUserDTO);
        if (pictureUserDTO != null) {
            pictureUserDTO.setPassword("");
            Map<Object, Object> map = new LinkedHashMap<>();
            map.put("user", pictureUserDTO);
            return RespBean.ok("用户信息", map);
        } else {
            return RespBean.error("用户信息错误");
        }
    }


}
*/
