package cn.picturecool.controller.gallery;

import cn.picturecool.DTO.*;
import cn.picturecool.authorization.annotation.Authorization;
import cn.picturecool.authorization.annotation.CurrentUser;
import cn.picturecool.model.RequestBean;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.picture.PictureGalleryService;
import cn.picturecool.service.picture.PictureLikeService;
import cn.picturecool.service.picture.PictureMainService;
import cn.picturecool.service.picture.imp.PictureLinkServiceImp;
import cn.picturecool.service.user.PictureUserService;
import cn.picturecool.utils.date.DateUtils;
import cn.picturecool.utils.file.FilePath;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-27 16:08
 **/
@RestController
public class GalleryByIdAndTimeAndUniqueHashController {

    @Autowired
    private PictureGalleryService pictureGalleryService;
    @Autowired
    private PictureUserService pictureUserService;
    @Autowired
    private PictureLinkServiceImp pictureLinkServiceImp;
    @Autowired
    private PictureMainService pictureMainService;
    @Autowired
    private PictureLikeService pictureLikeService;

    @Authorization
    @CrossOrigin
    @DeleteMapping("/gallery/{galleryId}/{uniqueHash}/{createDate}")
    public RespBean deleteUniquePicture(@CurrentUser PictureUserDTO pictureUserDTO,
                                        @PathVariable long galleryId,
                                        @PathVariable String uniqueHash,
                                        @PathVariable String createDate) {
        /*LocalDateTime localDateTime =LocalDateTime.parse(createDate);*/
        PictureGalleryDTO galleryDTO = pictureGalleryService.findGalleryById(galleryId);
        if (galleryDTO != null && galleryDTO.getUserId().equals(pictureUserDTO.getUserId())) {
            PictureLinkDTO linkDTO = pictureLinkServiceImp.findLinkByGalleryIdAndUniqueHashAndTime(galleryId, uniqueHash, DateUtils.String2LocalDateTime(createDate));
            if (linkDTO != null) {
                PictureMainDTO pictureByUniqueHash = pictureMainService.findPictureByUniqueHash(linkDTO.getUniqueHash());
                if (pictureByUniqueHash.getUploadTotal() > 1) {//判断是否存在其他的上传用户 存在的情况下 不删除 like数据 和本地图源
                    pictureLinkServiceImp.deleteLinkByGalleryIdAndUniqueHashAndDate(
                            linkDTO.getGalleryId(), linkDTO.getUniqueHash(), linkDTO.getCreateDate());
                    if (pictureByUniqueHash.getLastUploadUserId().equals(linkDTO.getUserId())) {//如果是最后一位的上传用户，删除上传记录 回退到第二上传的用户
                        PictureLinkDTO newLastLink =
                                pictureLinkServiceImp.findLinkByUniqueHashOrderByTime(pictureByUniqueHash.getUniqueHash()).get(0);
                        pictureMainService.updateUploadTotalAndUploadUser(
                                newLastLink.getUniqueHash(),
                                pictureByUniqueHash.getUploadTotal() - 1,
                                newLastLink.getUserId(),
                                newLastLink.getCreateDate());
                    } else {//如果不是 直接更新数据
                        pictureMainService.updateUploadTotal(pictureByUniqueHash.getUniqueHash(), pictureByUniqueHash.getUploadTotal() - 1);
                    }
                } else {//如果仅自己上传 删除关联的like数据 删除main表中的记录 删除本地图源
                    List<PictureLikeDTO> allUserLikeByUniqueHash = pictureLikeService.findLikeByUniqueHash(linkDTO.getUniqueHash());
                    for (PictureLikeDTO userLikeByUniqueHash : allUserLikeByUniqueHash) {//删除其他用户对该图片的like
                        pictureLikeService.deleteLikeByUserIdAndUniqueHash(userLikeByUniqueHash.getUniqueHash(), userLikeByUniqueHash.getUserId());
                        PictureUserDTO user = pictureUserService.findUserById(userLikeByUniqueHash.getUserId());
                        pictureUserService.updateLikeTotal(userLikeByUniqueHash.getUserId(), user.getLikeTotal() - 1);
                    }//接下来删除main表数据 删除本地图源
                    pictureLinkServiceImp.deleteLinkByGalleryIdAndUniqueHashAndDate(
                            linkDTO.getGalleryId(), linkDTO.getUniqueHash(), linkDTO.getCreateDate());
                    pictureMainService.deleteByUniqueHash(pictureByUniqueHash.getUniqueHash());

                    String[] filePath;
                    if (FilePath.isWindows()) {
                        filePath = pictureByUniqueHash.getFilePath().split("\\\\");
                    } else {
                        filePath = pictureByUniqueHash.getFilePath().split(File.separator);
                    }
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 0; i < (filePath.length - 1); i++) {
                        stringBuilder.append(filePath[i] + File.separator);
                    }
                    String fileDir = stringBuilder.toString();
                    File dir = new File(fileDir);
                    File maxFile = new File(fileDir + pictureByUniqueHash.getUniqueHash() + "." + pictureByUniqueHash.getFileSuffix());
                    File midFile = new File(fileDir + pictureByUniqueHash.getUniqueHash() + "_mid.jpg");
                    File minFile = new File(fileDir + pictureByUniqueHash.getUniqueHash() + "_min.jpg");
                    minFile.delete();
                    midFile.delete();
                    maxFile.delete();
                    if (dir.listFiles().length > 0) {

                    } else {
                        dir.delete();
                    }
                }
                //按图片数量更新 数据库中user 上传图片张数
                pictureUserService.updatePictureTotal(pictureUserDTO.getUserId(), pictureUserService.findUserById(pictureUserDTO.getUserId()).getPictureTotal() - 1);
                pictureGalleryService.updatePictureTotal(galleryDTO.getGalleryId(), pictureGalleryService.findGalleryById(galleryDTO.getGalleryId()).getPictureTotal() - 1);
                return RespBean.ok("删除成功");
            }
        }
        return RespBean.error("信息错误");
    }

/*    @Authorization
    @PutMapping("/gallery/{galleryId}/{uniqueHash}/{createDate}")
    public RespBean updatePictureMsg(@CurrentUser PictureUserDTO pictureUserDTO,
                                     @PathVariable long galleryId,
                                     @PathVariable String uniqueHash,
                                     @PathVariable String createDate,
                                     @RequestBody JSONObject jsonParam) {
        RequestBean requestBean = jsonParam.toJavaObject(RequestBean.class);
        PictureGalleryDTO galleryDTO = pictureGalleryService.findGalleryById(galleryId);
        if (galleryDTO != null && galleryDTO.getUserId().equals(pictureUserDTO.getUserId())) {
            if (requestBean.getRequestMsg().equals("picture")) {
                PictureLinkDTO linkDTO =
                        pictureLinkServiceImp.findLinkByGalleryIdAndUniqueHashAndTime(galleryId, uniqueHash, DateUtils.String2LocalDateTime(createDate));
                if (linkDTO != null) {
                    String label = (String) requestBean.getRequestMap().get("label");
                    String description = (String) requestBean.getRequestMap().get("description");
                    if (label != null && description != null) {
                        PictureMainDTO pictureMainDTO = pictureMainService.findPictureByUniqueHash(linkDTO.getUniqueHash());
                        pictureMainDTO.getPictureStyle();
                    }
                }

                *//*if (galleryName != null && description != null) {
                    if (pictureGalleryService.updateGalleryNameAadDescription(galleryDTO.getGalleryId(), galleryName, description) > 0) {
                        PictureGalleryDTO galleryById = pictureGalleryService.findGalleryById(galleryId);
                        Map<Object, Object> map = new LinkedHashMap<>();
                        map.put("gallery", galleryById);
                        return RespBean.ok("用户相册图片信息", map);
                    }
                }*//*
            }
        }
        return RespBean.error("用户相册图片信息");
    }*/

    @Authorization
    @GetMapping("/gallery/{galleryId}/{uniqueHash}/{createDate}")
    RespBean getPictureMsg(@CurrentUser PictureUserDTO pictureUserDTO,
                           @PathVariable long galleryId,
                           @PathVariable String uniqueHash,
                           @PathVariable String createDate) {
        PictureGalleryDTO galleryDTO = pictureGalleryService.findGalleryById(galleryId);
        if (galleryDTO != null && galleryDTO.getUserId().equals(pictureUserDTO.getUserId())) {
            PictureLinkDTO linkDTO =
                    pictureLinkServiceImp.findLinkByGalleryIdAndUniqueHashAndTime(galleryId, uniqueHash, DateUtils.String2LocalDateTime(createDate));
            if (linkDTO != null) {
                Map<Object, Object> map = new LinkedHashMap<>();
                map.put("picture", linkDTO);
                return RespBean.ok("用户相册图片信息", map);
            }
        }
        return RespBean.error("信息错误");
    }
}
