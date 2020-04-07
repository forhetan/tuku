package cn.picturecool.DTO.helper;

import cn.picturecool.DTO.PictureLinkDTO;
import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.utils.file.HelperMapKeys;
import lombok.val;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: tuku
 * @description: PictureLinkDTO初始化器 所有属性的初始化器 整理所有相关bean的属性 生成map 并将map交给bean生成器 按需生成bean
 * @author: 赵元昊
 * @create: 2020-03-01 12:55
 **/
public class PictureLinkBeanInitializer {

    private Map<String, Object> map = null;

    private PictureLinkBeanInitializer() {
        this.map = new HashMap<>();
    }

/*    public static PictureLinkDTO initLinkBeanByMainBean(PictureLinkDTO updateBean, PictureMainDTO sourceBean) {
        updateBean.setUniqueHash(sourceBean.getUniqueHash());
        updateBean.setFilePath(sourceBean.getFilePath());
        updateBean.setCreateDate(sourceBean.getLastUploadDate());
        return updateBean;
    }*/

    public PictureLinkBeanInitializer initMainBeanByHelperMap(Map<String, Object> souMap, boolean cover) {

        for (Map.Entry<String, Object> entry : souMap.entrySet()) {
            put(entry.getKey(), entry.getValue(), cover);
        }
        /*PictureMainDTO pictureMainDTO = PictureMainDTO.builder().uniqueHash((String) map.get())
                .hammingHash(pictureLinkDTO.getUniqueHash().split("_")[0])
                .fileHash(pictureLinkDTO.getUniqueHash().split("_")[1])
                .filePath(pictureLinkDTO.getFilePath())
                .pictureStyle(pictureStyle)
                .picturePixel()
                .fileSize()
                .likeTotal()
                .viewedTotal()
                .downloadedTotal()
                .searchedTotal()
                .fileSuffix()
                .uploadTotal(1)
                .lastUploadUserId()
                .adminUpload(0)
                .lastUploadDate()
                .weights()
                .status(1)
                .build();*/
        return this;
    }

    public static PictureLinkBeanInitializer init() {
        return new PictureLinkBeanInitializer();
    }

    public PictureLinkBeanInitializer initByPictureMainBean(PictureMainDTO pictureMainDTO) {
        return initByPictureMainBean(pictureMainDTO, true);
    }

    public PictureLinkBeanInitializer initByPictureLinkBean(PictureLinkDTO pictureLinkDTO) {
        return initByPictureLinkBean(pictureLinkDTO, true);
    }

    public PictureLinkBeanInitializer initByPictureUserBean(PictureUserDTO pictureUserDTO) {
        return initByPictureUserBean(pictureUserDTO, true);
    }

    public PictureLinkBeanInitializer initByPictureMainBean(PictureMainDTO pictureMainDTO, boolean cover) {

        put(HelperMapKeys.UniqueHash_KEY, pictureMainDTO.getUniqueHash(), cover);
        put(HelperMapKeys.HashMsg_KEY, pictureMainDTO.getHammingHash(), cover);
        put(HelperMapKeys.FileHash_KEY, pictureMainDTO.getFileHash(), cover);
        put(HelperMapKeys.FilePath_KEY, pictureMainDTO.getFilePath(), cover);
        put(HelperMapKeys.MainPictureStyle_KEY, pictureMainDTO.getPictureStyle(), cover);
        put(HelperMapKeys.PicturePixel_KEY, pictureMainDTO.getPicturePixel(), cover);
        put(HelperMapKeys.FileSize_KEY, pictureMainDTO.getFileSize(), cover);
        put(HelperMapKeys.LikeTotal_KEY, pictureMainDTO.getLikeTotal(), cover);
        put(HelperMapKeys.ViewedTotal_KEY, pictureMainDTO.getViewedTotal(), cover);
        put(HelperMapKeys.DownloadedTotal_KEY, pictureMainDTO.getDownloadedTotal(), cover);
        put(HelperMapKeys.SearchedTotal_KEY, pictureMainDTO.getSearchedTotal(), cover);
        //put(HelperMapKeys.FileSuffix_KEY, pictureMainDTO.getFileSuffix(), cover);
        put(HelperMapKeys.AdminUpload_KEY, pictureMainDTO.getAdminUpload(), cover);
        put(HelperMapKeys.Weights_KEY, pictureMainDTO.getWeights(), cover);
        put(HelperMapKeys.MainStatus_KEY, pictureMainDTO.getStatus(), cover);

        put(HelperMapKeys.UploadTotal_KEY, pictureMainDTO.getUploadTotal(), cover);
        put(HelperMapKeys.LastUploadDate_KEY, pictureMainDTO.getLastUploadDate(), cover);
        put(HelperMapKeys.LastUploadUserId_KEY, pictureMainDTO.getLastUploadUserId(), cover);

        return this;
    }

    public PictureLinkBeanInitializer initByPictureLinkBean(PictureLinkDTO pictureLinkDTO, boolean cover) {

        put(HelperMapKeys.UserId_KEY, pictureLinkDTO.getUserId(), cover);
        put(HelperMapKeys.GalleryId_KEY, pictureLinkDTO.getGalleryId(), cover);
        put(HelperMapKeys.UniqueHash_KEY, pictureLinkDTO.getUniqueHash(), cover);
        put(HelperMapKeys.LinkCreateDate_KEY, pictureLinkDTO.getCreateDate(), cover);
        put(HelperMapKeys.OriginName_KEY, pictureLinkDTO.getOriginName(), cover);
        put(HelperMapKeys.Description_KEY, pictureLinkDTO.getDescription(), cover);
        put(HelperMapKeys.LinkPictureStyle_KEY, pictureLinkDTO.getPictureStyle(), cover);
        //put(HelperMapKeys.Url_KEY, pictureLinkDTO.getUrl(), cover);

        return this;
    }

    public PictureLinkBeanInitializer initByPictureUserBean(PictureUserDTO pictureUserDTO, boolean cover) {

        put(HelperMapKeys.UserId_KEY, pictureUserDTO.getUserId(), cover);
        put(HelperMapKeys.UserName_KEY, pictureUserDTO.getUserName(), cover);
        put(HelperMapKeys.Password_KEY, pictureUserDTO.getPassword(), cover);
        put(HelperMapKeys.Phone_KEY, pictureUserDTO.getPhone(), cover);
        put(HelperMapKeys.Email_KEY, pictureUserDTO.getEmail(), cover);
        put(HelperMapKeys.UserCreateDate_KEY, pictureUserDTO.getCreateDate(), cover);
        put(HelperMapKeys.GalleryTotal_KEY, pictureUserDTO.getGalleryTotal(), cover);
        put(HelperMapKeys.UserPictureTotal_KEY, pictureUserDTO.getPictureTotal(), cover);
        //put(HelperMapKeys.UserLikeTotal_KEY, pictureUserDTO.getLikeTotal(), cover);

        return this;
    }


    public Map<String, Object> build() {
        return map;
    }

    //覆写器
    private void put(String key, Object val, boolean cover) {
        if (cover) {
            this.map.put(key, val);
        } else {
            if (this.map.containsKey(key)) {
            } else {
                this.map.put(key, val);
            }
        }
    }

    public PictureLinkBeanInitializer put(String key, Object val) {
        this.map.put(key, val);
        return this;
    }

    public PictureLinkBeanInitializer updateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.put(HelperMapKeys.LastUploadDate_KEY, localDateTime);
        this.put(HelperMapKeys.LinkCreateDate_KEY, localDateTime);
        return this;
    }

    public PictureLinkBeanInitializer updateUser() {
        this.put(HelperMapKeys.LastUploadUserId_KEY,map.get(HelperMapKeys.UserId_KEY));
        return this;
    }

    public PictureLinkBeanInitializer updateUpTotal(){
        map.put(HelperMapKeys.UploadTotal_KEY,(Integer)map.get(HelperMapKeys.UploadTotal_KEY)+1);
        return this;
    }
}
