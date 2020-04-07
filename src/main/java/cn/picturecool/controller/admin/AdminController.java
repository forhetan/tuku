package cn.picturecool.controller.admin;

import cn.picturecool.DTO.*;
import cn.picturecool.authorization.admin.annotation.Admin;
import cn.picturecool.authorization.admin.annotation.CurrentAdmin;
import cn.picturecool.model.RespBean;
import cn.picturecool.model.UserEntity;
import cn.picturecool.service.admin.PictureAdminService;
import cn.picturecool.service.picture.PictureGalleryService;
import cn.picturecool.service.picture.PictureLikeService;
import cn.picturecool.service.picture.PictureMainService;
import cn.picturecool.service.picture.imp.PictureLinkServiceImp;
import cn.picturecool.service.user.PictureUserService;
import cn.picturecool.utils.file.FilePath;
import cn.picturecool.utils.id.SnowFlake;
import cn.picturecool.utils.md5.HideMD5;
import cn.picturecool.utils.token.AdminToken;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * @create: 2020-03-27 18:16
 **/
@RestController
public class AdminController {

    @Autowired
    private PictureUserService pictureUserService;
    @Autowired
    private PictureGalleryService pictureGalleryService;
    @Autowired
    private PictureLinkServiceImp pictureLinkServiceImp;
    @Autowired
    private PictureMainService pictureMainService;
    @Autowired
    private PictureLikeService pictureLikeService;
    @Autowired
    private PictureAdminService pictureAdminService;

    @Admin
    @PostMapping("/admin")
    public RespBean loginAdmin(@RequestParam("adminName") String adminName,
                               @RequestParam("password") String password) throws Exception {

        String passMD5= HideMD5.getMD5Text(password);
        AdminDTO admin = pictureAdminService.findAdmin(adminName, passMD5);
        if(admin!=null){
            return RespBean.ok("", AdminToken.getToken(admin));
        }
        return RespBean.error("");

    }

    @CrossOrigin
    @Admin
    @DeleteMapping("/admin/user/{userId}")
    public RespBean deleteUser(@CurrentAdmin AdminDTO adminDTO,
                               @PathVariable("userId") long userId) {

        PictureUserDTO pictureUserDTO = pictureUserService.findUserById(userId);
        if (pictureUserDTO != null) {
            List<PictureGalleryDTO> galleryListByUserId = pictureGalleryService.findGalleryListByUserId(pictureUserDTO.getUserId());
            for (PictureGalleryDTO galleryDTO : galleryListByUserId) {
                if (galleryDTO != null && galleryDTO.getUserId().equals(pictureUserDTO.getUserId())) {
                    if (galleryDTO.getPictureTotal() > 0) {
                        List<PictureLinkDTO> linkDTOS = pictureLinkServiceImp.findLinkByGalleryId(galleryDTO.getGalleryId());
                        for (PictureLinkDTO linkDTO : linkDTOS) {
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
                        }
                    }
                    pictureGalleryService.deleteGalleryById(galleryDTO.getGalleryId());
                    pictureUserService.updateGalleryTotal(pictureUserDTO.getUserId(), pictureUserDTO.getGalleryTotal() - 1);
                }
            }
            pictureUserService.deleteByUserId(userId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("信息错误");
    }

    @Admin
    @PostMapping("/admin/user")
    public RespBean insertUser(@CurrentAdmin AdminDTO adminDTO,
                               @RequestParam("userName") String username,
                               @RequestParam("userPassword") String password,
                               @RequestParam("email") String email,
                               @RequestParam("phone") String phone) {
        long id = new SnowFlake(1, 1).nextId();
        PictureUserDTO pictureUserDTO = new PictureUserDTO();
        pictureUserDTO.setUserId(id);
        pictureUserDTO.setUserName(username);
        pictureUserDTO.setPassword(password);
        pictureUserDTO.setEmail(email);
        pictureUserDTO.setPhone(phone);
        pictureUserDTO.setCreateDate(LocalDateTime.now());
        pictureUserDTO.setPictureTotal(0);
        pictureUserDTO.setGalleryTotal(0);
        pictureUserDTO.setLikeTotal(0);
        if (pictureUserService.insertUser(pictureUserDTO) != 1) {
            return RespBean.error("新增失败");
        }
        UserEntity userEntity = UserEntity.toUserEntity(pictureUserService.findUser(username, password));
        return RespBean.ok("新增成功", userEntity);
    }

    @Admin
    @GetMapping("/admin/user/{size}/{page}")
    public RespBean getUserList(@CurrentAdmin AdminDTO adminDTO,
                                @PathVariable int size,
                                @PathVariable int page){
        IPage<PictureUserDTO> iPage = pictureUserService.selectAllPage(size, page);
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("total", (int) iPage.getTotal());
        map.put("pages", (int) iPage.getPages());
        map.put("position", page);
        map.put("list", iPage.getRecords());
        return RespBean.ok("", map);
    }
}
