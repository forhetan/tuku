package cn.picturecool.controller.like;

import cn.picturecool.DTO.PictureLikeDTO;
import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.authorization.annotation.Authorization;
import cn.picturecool.authorization.annotation.CurrentUser;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.picture.PictureLikeService;
import cn.picturecool.service.picture.PictureMainService;
import cn.picturecool.service.user.PictureUserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-04 12:21
 **/
@RestController
public class LikeController {
    @Autowired
    private PictureLikeService pictureLikeService;

    @Autowired
    private PictureUserService pictureUserService;

    @Autowired
    private PictureMainService pictureMainService;

    @Authorization
    @PostMapping("/like/{uniqueHash}")
    public RespBean setLike(@CurrentUser PictureUserDTO pictureUserDTO,
                            @PathVariable String uniqueHash) {
        PictureLikeDTO pictureLikeDTO = pictureLikeService.findLikeByUniqueHashAndUserId(uniqueHash, pictureUserDTO.getUserId());
        if (pictureLikeDTO == null) {
            PictureMainDTO mainDTO = pictureMainService.findPictureByUniqueHash(uniqueHash);
            if (mainDTO != null) {
                pictureLikeDTO = new PictureLikeDTO();
                pictureLikeDTO.setCreateDate(LocalDateTime.now());
                pictureLikeDTO.setUniqueHash(uniqueHash);
                pictureLikeDTO.setUserId(pictureUserDTO.getUserId());
                pictureLikeService.insertLike(pictureLikeDTO);
                pictureMainService.updateMainLikeTotal(mainDTO.getUniqueHash(), mainDTO.getLikeTotal() + 1);
                pictureMainService.uploadWeightsByUniqueHash(mainDTO.getUniqueHash());
                pictureUserService.updateLikeTotal(pictureUserDTO.getUserId(), pictureUserDTO.getLikeTotal() + 1);
                return RespBean.ok("已点赞");
            }
        }
        return RespBean.error("数据错误");
    }

    @CrossOrigin
    @Authorization
    @DeleteMapping("/like/{uniqueHash}")
    public RespBean deleteLike(@CurrentUser PictureUserDTO pictureUserDTO,
                               @PathVariable String uniqueHash) {
        PictureLikeDTO pictureLikeDTO = pictureLikeService.findLikeByUniqueHashAndUserId(uniqueHash, pictureUserDTO.getUserId());
        if (pictureLikeDTO != null) {
            PictureMainDTO mainDTO = pictureMainService.findPictureByUniqueHash(uniqueHash);
            if (mainDTO != null) {
                pictureLikeService.deleteLikeByUserIdAndUniqueHash(pictureLikeDTO.getUniqueHash(), pictureLikeDTO.getUserId());
                pictureMainService.updateMainLikeTotal(mainDTO.getUniqueHash(), mainDTO.getLikeTotal() - 1);
                pictureMainService.uploadWeightsByUniqueHash(mainDTO.getUniqueHash());
                pictureUserService.updateLikeTotal(pictureLikeDTO.getUserId(), pictureUserDTO.getLikeTotal() - 1);
                return RespBean.ok("取消点赞！");
            }
        }
        return RespBean.error("数据错误");
    }

    @Authorization
    @GetMapping("/like/{uniqueHash}")
    public RespBean getLike(@CurrentUser PictureUserDTO pictureUserDTO,
                            @PathVariable String uniqueHash) {
        PictureLikeDTO pictureLikeDTO = pictureLikeService.findLikeByUniqueHashAndUserId(uniqueHash, pictureUserDTO.getUserId());
        if (pictureLikeDTO != null) {
            return RespBean.ok("已点赞", true);
        }
        return RespBean.ok("未点赞", false);
    }


    @Authorization
    @GetMapping("/like")
    public RespBean selectLike(@CurrentUser PictureUserDTO pictureUserDTO) {
        List<PictureLikeDTO> likeDTOList = pictureLikeService.findLikeByUserId(pictureUserDTO.getUserId());
        Map<Object, Object> map = new LinkedHashMap<>();
        List<PictureMainDTO> list=new ArrayList<>();
        for (PictureLikeDTO pictureLikeDTO : likeDTOList) {
            list.add(pictureMainService.findPictureByUniqueHash(pictureLikeDTO.getUniqueHash()));
        }
        map.put("like", list);
        return RespBean.ok("用户Like信息", map);
    }
}
