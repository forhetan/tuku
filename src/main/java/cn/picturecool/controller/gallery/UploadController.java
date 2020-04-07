package cn.picturecool.controller.gallery;

import cn.picturecool.DTO.PictureGalleryDTO;
import cn.picturecool.DTO.PictureLinkDTO;
import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.authorization.annotation.Authorization;
import cn.picturecool.authorization.annotation.CurrentUser;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.picture.PictureGalleryService;
import cn.picturecool.service.picture.PictureMainService;
import cn.picturecool.service.picture.imp.PictureLinkServiceImp;
import cn.picturecool.service.user.PictureUserService;
import cn.picturecool.utils.file.FilePath;
import cn.picturecool.utils.file.PictureBeanHelper;
import cn.picturecool.utils.lable.LabelVerifier;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

/**
 * @program: tuku
 * @description: 图片上传
 * @author: 赵元昊
 * @create: 2020-02-25 18:55
 **/
@RestController
public class UploadController {

    static final String MIN_SUFFIX = "_min.jpg";
    static final String MID_SUFFIX = "_mid.jpg";

    @Autowired
    private PictureMainService pictureMainServiceImp;
    @Autowired
    private PictureGalleryService pictureGalleryServiceImp;
    @Autowired
    private PictureLinkServiceImp pictureLinkServiceImp;
    @Autowired
    private PictureUserService pictureUserServiceImp;

    @Authorization
    @PostMapping("/gallery/{galleryId}")
    public RespBean upload(@RequestParam("file") MultipartFile file,
                           @RequestParam("description") String description,
                           @RequestParam("pictureStyle") String pictureStyle,
                           @PathVariable("galleryId") Long galleryId,
                           @CurrentUser PictureUserDTO pictureUserDTO) throws IOException {

        /*System.out.println(pictureUserDTO);*/

        PictureBeanHelper pictureBeanHelper;
        if (FilePath.isImage(file.getContentType())) {

            /*//PictureLinkDTO pictureLinkDTO = jsonParam.toJavaObject(PictureLinkDTO.class);
            PictureLinkDTO pictureLinkDTO = jsonParam;*/
            PictureLinkDTO pictureLinkDTO = PictureLinkDTO.builder()
                    .galleryId(galleryId)
                    .userId(pictureUserDTO.getUserId())
                    .pictureStyle(LabelVerifier.onlySpliceAndFilter(pictureStyle))
                    .description(description)
                    .build();
            PictureGalleryDTO pictureGalleryDTO = pictureGalleryServiceImp.findGalleryById(pictureLinkDTO.getGalleryId());
            System.out.println(pictureGalleryDTO);
            if (pictureGalleryDTO != null) {
                //初始化linkbean助手信息
                pictureBeanHelper = PictureBeanHelper.init(file.getInputStream());
                //验证数据库是否有这张图片的原图，有的话不进行图片的存储，只进行对图片信息的关联
                PictureMainDTO pictureMainDTO =
                        pictureMainServiceImp.findPictureByUniqueHash(pictureBeanHelper.getUniqueHash());
                if (pictureMainDTO != null) {//如果存在，更新最后上传日期，更新最后上传者ID，更新上传数

                    pictureLinkDTO.setUniqueHash(pictureBeanHelper.getUniqueHash());
                    pictureLinkDTO.setCreateDate(LocalDateTime.now());
                    pictureLinkDTO.setOriginName(file.getOriginalFilename());

                    String newStyle = LabelVerifier.labelOfSqlString(pictureMainDTO.getPictureStyle(),
                            pictureLinkDTO.getPictureStyle());


                    //pictureGalleryDTO.setPictureTotal(pictureGalleryDTO.getPictureTotal() + 1);
                    //pictureUserDTO.setPictureTotal(pictureUserDTO.getPictureTotal() + 1);
                    //返回图片地址
                    pictureGalleryServiceImp.updatePictureTotal(pictureGalleryDTO.getGalleryId(),pictureGalleryDTO.getPictureTotal()+1);
                    pictureMainServiceImp.updateByNewUpload(
                            pictureMainDTO.getUniqueHash(),
                            pictureMainDTO.getUploadTotal() + 1,
                            pictureLinkDTO.getUserId(),
                            pictureLinkDTO.getCreateDate(),
                            newStyle);
                    pictureMainServiceImp.uploadWeightsByUniqueHash(pictureMainDTO.getUniqueHash());
                    pictureLinkServiceImp.insertLink(pictureLinkDTO);
                    pictureUserServiceImp.updatePictureTotal(pictureUserDTO.getUserId(),pictureUserDTO.getPictureTotal()+1);
                    return RespBean.ok("存入成功", new URL("http://localhost:8080/picture/max/"+pictureLinkDTO.getUniqueHash()));
                } else {//如果本地没有存储过这张图片，生成图片缩略图 创建各项信息 生成pictureMainBean
                    pictureMainDTO = PictureMainDTO.builder().uniqueHash(pictureBeanHelper.getUniqueHash())
                            .hammingHash(pictureBeanHelper.getMsgHash())
                            .fileHash(pictureBeanHelper.getFileHash())
                            .filePath(pictureBeanHelper.getBasePath() + File.separator
                                    + pictureBeanHelper.getUniqueHash())
                            .pictureStyle(pictureLinkDTO.getPictureStyle())
                            .picturePixel(pictureBeanHelper.getPicturePixel())
                            .fileSize(file.getSize())
                            .likeTotal(0)
                            .viewedTotal(0)
                            .downloadedTotal(0)
                            .searchedTotal(0)
                            .fileSuffix(FilePath.getSuffix(file.getContentType()))
                            .uploadTotal(1)
                            .lastUploadUserId(pictureLinkDTO.getUserId())
                            .adminUpload(0)
                            .lastUploadDate(LocalDateTime.now())
                            .weights(0)
                            .status(1)
                            .build();

                    pictureLinkDTO.setUniqueHash(pictureBeanHelper.getUniqueHash());
                    pictureLinkDTO.setOriginName(file.getOriginalFilename());
                    pictureLinkDTO.setCreateDate(pictureMainDTO.getLastUploadDate());

                    //pictureGalleryDTO.setPictureTotal(pictureGalleryDTO.getPictureTotal() + 1);
                    //pictureUserDTO.setPictureTotal(pictureUserDTO.getPictureTotal() + 1);

                    PictureBeanHelper.createFolders(pictureBeanHelper.getBasePath());
                    file.transferTo(new File(pictureMainDTO.getFilePath()
                            + "." + FilePath.getSuffix(file.getContentType())));
                    ImageIO.write(pictureBeanHelper.getBufferedImageMIN(), "JPG",
                            new File(pictureMainDTO.getFilePath() + MIN_SUFFIX));
                    ImageIO.write(pictureBeanHelper.getBufferedImageMID(), "JPG",
                            new File(pictureMainDTO.getFilePath() + MID_SUFFIX));
                    pictureGalleryServiceImp.updatePictureTotal(pictureGalleryDTO.getGalleryId(),pictureGalleryDTO.getPictureTotal()+1);
                    pictureMainServiceImp.insertMain(pictureMainDTO);
                    pictureMainServiceImp.uploadWeightsByUniqueHash(pictureMainDTO.getUniqueHash());
                    pictureLinkServiceImp.insertLink(pictureLinkDTO);
                    pictureUserServiceImp.updatePictureTotal(pictureUserDTO.getUserId(),pictureUserDTO.getPictureTotal()+1);
                }
            } else {//不存在相册 非法相册
                return RespBean.error("相册信息错误！");
            }
            //向数据库插入main bean 再更新用户表、相册表 再插入图片link表 bean
            return RespBean.ok("上传成功:", new URL("http://localhost:8080/picture/max/"+pictureLinkDTO.getUniqueHash()));
        } else {//不是图片类型 上传出错
            return RespBean.error("上传失败:", null);
        }
    }

}
