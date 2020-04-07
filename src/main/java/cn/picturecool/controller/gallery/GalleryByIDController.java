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
import cn.picturecool.service.picture.imp.PictureMainServiceImp;
import cn.picturecool.service.user.PictureUserService;
import cn.picturecool.utils.file.FilePath;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-26 19:41
 **/
@RestController
public class GalleryByIDController {
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
    @GetMapping("/gallery/{galleryId}")
    public RespBean getGallery(@CurrentUser PictureUserDTO pictureUserDTO,
                               @PathVariable("galleryId") Long galleryId) {
        System.out.println(galleryId);
        PictureGalleryDTO galleryDTO = pictureGalleryService.findGalleryById(galleryId);
        System.out.println(galleryDTO);
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
    @PutMapping("/gallery/{galleryId}")
    public RespBean updateGallery(@CurrentUser PictureUserDTO pictureUserDTO,
                                  @PathVariable("galleryId") Long galleryId,
                                  @RequestBody JSONObject jsonParam) {
        RequestBean requestBean = jsonParam.toJavaObject(RequestBean.class);
        PictureGalleryDTO galleryDTO = pictureGalleryService.findGalleryById(galleryId);
        if (galleryDTO != null) {
            if (galleryDTO.getUserId().equals(pictureUserDTO.getUserId())) {
                if (requestBean.getRequestMsg().equals("gallery")) {
                    String galleryName = (String) requestBean.getRequestMap().get("galleryName");
                    String description = (String) requestBean.getRequestMap().get("description");
                    if (galleryName != null && description != null) {
                        if (pictureGalleryService.updateGalleryNameAadDescription(galleryDTO.getGalleryId(), galleryName, description) > 0) {
                            PictureGalleryDTO galleryById = pictureGalleryService.findGalleryById(galleryId);
                            Map<Object, Object> map = new LinkedHashMap<>();
                            map.put("gallery", galleryById);
                            return RespBean.ok("用户相册图片信息", map);
                        }
                    }
                }
            }
        }
        return RespBean.error("用户相册图片信息");
    }

    @CrossOrigin
    @Authorization
    @DeleteMapping("/gallery/{galleryId}")
    public RespBean deleteGallery(@CurrentUser PictureUserDTO pictureUserDTO,
                                  @PathVariable("galleryId") Long galleryId) {//忘了重要的一点 判定这个相册是不是该用户的 好了 加上了
        PictureGalleryDTO galleryDTO = pictureGalleryService.findGalleryById(galleryId);
        if (galleryDTO != null && galleryDTO.getUserId().equals(pictureUserDTO.getUserId())) {
            if (galleryDTO.getPictureTotal() > 0) {
                List<PictureLinkDTO> linkDTOS = pictureLinkServiceImp.findLinkByGalleryId(galleryId);
                for (PictureLinkDTO linkDTO : linkDTOS) {
                    PictureMainDTO pictureByUniqueHash = pictureMainService.findPictureByUniqueHash(linkDTO.getUniqueHash());
                    if (pictureByUniqueHash.getUploadTotal() > 1) {//判断是否存在其他的上传用户 存在的情况下 不删除 like数据 和本地图源
                        pictureLinkServiceImp.deleteLinkByGalleryIdAndUniqueHashAndDate(
                                linkDTO.getGalleryId(), linkDTO.getUniqueHash(), linkDTO.getCreateDate());
                        if (pictureByUniqueHash.getLastUploadUserId().equals(linkDTO.getUserId())) {//如果是最后一位的上传用户，删除上传记录 回退到第二上传的用户
                            //System.out.println(pictureLinkServiceImp.findLinkByUniqueHashOrderByTime(pictureByUniqueHash.getUniqueHash()));
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

                        /*String path=pictureByUniqueHash.getFilePath();
                        String[] hshs= path.split(File.pathSeparator);
                        for (int i=0;i<hshs.length;i++){
                            System.out.println(hshs[i]);
                        }*/
                        String[] filePath;
                        if(FilePath.isWindows()){
                            filePath= pictureByUniqueHash.getFilePath().split("\\\\");
                        }else {
                            filePath= pictureByUniqueHash.getFilePath().split(File.separator);
                        }
                        StringBuilder stringBuilder=new StringBuilder();

                        for (int i = 0; i < (filePath.length - 1); i++) {
                            stringBuilder.append(filePath[i] + File.separator);
                            /*fileDir = fileDir + filePath[i] + File.separator;
                            System.out.println(fileDir);*/
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
                }
            }
            pictureGalleryService.deleteGalleryById(galleryDTO.getGalleryId());
            pictureUserService.updateGalleryTotal(pictureUserDTO.getUserId(), pictureUserDTO.getGalleryTotal() - 1);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("信息错误");
    }

}
