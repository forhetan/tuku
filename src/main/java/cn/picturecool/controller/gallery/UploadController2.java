package cn.picturecool.controller.gallery;

import cn.picturecool.DTO.PictureGalleryDTO;
import cn.picturecool.DTO.PictureLinkDTO;
import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.DTO.helper.PictureLinkBeanInitializer;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.picture.PictureGalleryService;
import cn.picturecool.service.picture.PictureMainService;
import cn.picturecool.utils.file.HelperMapKeys;
import cn.picturecool.utils.file.PictureBeanHelper;
import cn.picturecool.utils.file.FilePath;
import cn.picturecool.utils.id.SnowFlake;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: tuku
 * @description: 图片上传
 * @author: 赵元昊
 * @create: 2020-02-25 18:55
 **/
@RestController
public class UploadController2 {

    static final String MIN_SUFFIX = "_min.jpg";
    static final String MID_SUFFIX = "_mid.jpg";

    @Autowired
    private PictureMainService pictureMainServiceImp;
    @Autowired
    private PictureGalleryService pictureGalleryServiceImp;

    /*    @PostMapping("/upload/{galleryId}/{position}")
        public RespBean upload(HttpServletRequest req, @RequestParam("file") MultipartFile file, Model m) throws IOException {
            String fileName = file.getOriginalFilename();
            //验证是否是图片文件
            if(FilePath.isImage(file.getContentType())){
                //获得图片尺寸格式

                CreateFile.createThumbnail(file.getInputStream());
                //获取图片汉明hash
                String imageMsg = ImageMsg.getImageHash(ImageIO.read(file.getInputStream()));
                //根据汉明hash合成图片目标文件夹路径
                String target= FilePath.getPicturePath(imageMsg);
                //创建图片目标文件夹
                CreateFile.createFolders(FilePath.getPicturePath(imageMsg));
                //生成目标目录
                String fileDestPath = target+File.separator+ imageMsg+"."+FilePath.getSuffix(file.getContentType());
                //生成缩略图

                CreateFile.createThumbnail(file.getInputStream());
                //保存缩略图

                //保存原图片
                file.transferTo(new File(fileDestPath));
            }else {
                System.out.println("shazi****************");
            }
            return RespBean.ok("上传成功:"+fileName);
        }*/
    /*@PostMapping("/upload")
    public RespBean upload(@RequestParam("file") MultipartFile file,
                           @RequestBody JSONObject jsonParam,
                           @RequestParam("pictureUserDTO") PictureUserDTO pictureUserDTO) throws IOException {

        PictureBeanHelper pictureBeanHelper = null;
        if (FilePath.isImage(file.getContentType())) {

            PictureLinkDTO pictureLinkDTO = jsonParam.toJavaObject(PictureLinkDTO.class);
            PictureGalleryDTO pictureGalleryDTO = pictureGalleryServiceImp.findGalleryById(pictureLinkDTO.getGalleryId());
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

                    pictureMainDTO.setUploadTotal(pictureMainDTO.getUploadTotal() + 1);
                    pictureMainDTO.setLastUploadUserId(pictureLinkDTO.getUserId());
                    pictureMainDTO.setLastUploadDate(pictureLinkDTO.getCreateDate());

                    pictureGalleryDTO.setPictureTotal(pictureGalleryDTO.getPictureTotal() + 1);
                    pictureUserDTO.setPictureTotal(pictureUserDTO.getPictureTotal() + 1);
                    //返回图片地址
                    return RespBean.ok("存入成功", pictureLinkDTO);
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

                    pictureGalleryDTO.setPictureTotal(pictureGalleryDTO.getPictureTotal() + 1);
                    pictureUserDTO.setPictureTotal(pictureUserDTO.getPictureTotal() + 1);

                    PictureBeanHelper.createFolders(pictureBeanHelper.getBasePath());
                    file.transferTo(new File(pictureMainDTO.getFilePath()
                            + "." + FilePath.getSuffix(file.getContentType())));
                    ImageIO.write(pictureBeanHelper.getBufferedImageMIN(), "JPG",
                            new File(pictureMainDTO.getFilePath() + MIN_SUFFIX));
                    ImageIO.write(pictureBeanHelper.getBufferedImageMID(), "JPG",
                            new File(pictureMainDTO.getFilePath() + MID_SUFFIX));

                }
            } else {//不存在相册 非法相册
                return RespBean.error("相册信息错误！");
            }
            //向数据库插入main bean 再更新用户表、相册表 再插入图片link表 bean

        } else {//不是图片类型 上传出错
            return RespBean.error("上传失败:", null);
        }
        return RespBean.ok("上传成功:", pictureLinkDTO);
    }*/

    /*@PostMapping("/upload")
    public RespBean upload(@RequestParam("file") MultipartFile file,
                           @RequestBody JSONObject jsonParam,
                           @RequestParam("pictureUserDTO") PictureUserDTO pictureUserDTO) throws IOException {

        PictureBeanHelper pictureBeanHelper = null;
        Map<String, Object> map = null;
        if (FilePath.isImage(file.getContentType())) {

            PictureLinkDTO pictureLinkDTO = jsonParam.toJavaObject(PictureLinkDTO.class);
            PictureGalleryDTO pictureGalleryDTO = pictureGalleryServiceImp.findGalleryById(pictureLinkDTO.getGalleryId());
            if (pictureGalleryDTO != null) {
                //初始化linkbean助手信息
                pictureBeanHelper = PictureBeanHelper.init(file.getInputStream());
                //验证数据库是否有这张图片的原图，有的话不进行图片的存储，只进行对图片信息的关联
                PictureMainDTO pictureMainDTO =
                        pictureMainServiceImp.findPictureByUniqueHash(pictureBeanHelper.getMsg());
                if (pictureMainDTO != null) {//如果存在，更新最后上传日期，更新最后上传者ID，更新上传数
                    *//*map = new HashMap<>();
                    map.put(HelperMapKeys.LastUploadDate_KEY,LocalDateTime.now());
                    map.put(HelperMapKeys.LastUploadUserId_KEY,pictureLinkDTO.getUserId());
                    map.put(HelperMapKeys.UploadTotal_KEY,pictureMainDTO.getUploadTotal() + 1);
                    map.put(HelperMapKeys.HashMsg_KEY);
                    map.put(HelperMapKeys.FileHash_KEY);
                    map.put(HelperMapKeys.UniqueHash_KEY);
                    map.put(HelperMapKeys.BasePath_KEY);
                    map.put(HelperMapKeys.MinThumbnail_KEY);
                    map.put(HelperMapKeys.MidThumbnail_KEY);
                    map.put(HelperMapKeys.PicturePixel_KEY);
                    map.put(HelperMapKeys.FilePath_KEY);
                    map.put(HelperMapKeys.PictureStyle_KEY);
                    map.put(HelperMapKeys.FileSize_KEY);
                    map.put(HelperMapKeys.LikeTotal_KEY);
                    map.put(HelperMapKeys.ViewedTotal_KEY);
                    map.put(HelperMapKeys.DownloadedTotal_KEY);
                    map.put(HelperMapKeys.SearchedTotal_KEY);
                    map.put(HelperMapKeys.FileSuffix_KEY);
                    map.put(HelperMapKeys.AdminUpload_KEY);
                    map.put(HelperMapKeys.Weights_KEY);
                    map.put(HelperMapKeys.Status_KEY);*//*

                    Map<String, Object> initMap = PictureLinkBeanInitializer.init()
                            .initByPictureMainBean(pictureMainDTO)
                            .initByPictureLinkBean(pictureLinkDTO,false)
                            .updateTime()
                            .updateUser()
                            .updateUpTotal()
                            .build();

                    pictureMainDTO.setLastUploadDate(localDateTime);
                    pictureMainDTO.setLastUploadUserId(pictureLinkDTO.getUserId());
                    pictureMainDTO.setUploadTotal(pictureMainDTO.getUploadTotal() + 1);
                    //                    //再更新pictureLinkDTO中的各项信息
                    //PictureLinkBeanInitializer.initLinkBeanByMainBean(pictureLinkDTO,pictureMainDTO);
                    pictureLinkDTO.setUniqueHash(pictureMainDTO.getUniqueHash());
                    pictureLinkDTO.setFilePath(pictureMainDTO.getFilePath());
                    pictureLinkDTO.setCreateDate(localDateTime);
                    pictureLinkDTO.setStatus(1);
                    pictureLinkDTO.setLinkId(new SnowFlake(1, 1).nextId());
                    pictureGalleryDTO.setPictureTotal(pictureGalleryDTO.getPictureTotal() + 1);
                    pictureUserDTO.setPictureTotal(pictureUserDTO.getPictureTotal() + 1);
                    //代办 将各项信息存入数据库

                    //返回图片地址
                    return RespBean.ok("存入成功", pictureLinkDTO);
                } else {//如果本地没有存储过这张图片，生成图片缩略图 创建各项信息 生成pictureMainBean
                    map = pictureBeanHelper.createThumbnail();

                }
            } else {//不存在相册 非法相册
                return RespBean.error("相册信息错误！");
            }
            //向数据库插入main bean 再更新用户表、相册表 再插入图片link表 bean

        } else {//不是图片类型 上传出错
            return RespBean.error("上传失败:", null);
        }
        return RespBean.ok("上传成功:", pictureLinkDTO);
    }*/

    /*@PostMapping("/upload/{gallery}/{position}")
    public RespBean upload(@RequestParam("file") MultipartFile file,
                           @PathVariable("gallery") Long galleryId,
                           @PathVariable("position") Integer position,
                           @RequestParam("userId") Long userId,
                           @RequestParam("pictureStyle") String pictureStyle) throws IOException {

        PictureLinkDTO pictureLinkDTO = null;
        if (FilePath.isImage(file.getContentType())) {
            PictureBeanHelper pictureBeanHelper = PictureBeanHelper.init(file.getInputStream());
            //验证数据库是否有这张图片的原图，有的话不进行图片的存储，只进行对图片信息的关联
            PictureMainDTO pictureMainDTO =
                    pictureMainServiceImp.findPictureByUniqueHash(pictureBeanHelper.getMsg());
            *//*String
            pictureLinkDTO = (PictureLinkDTO) getPictureBean(file, galleryId, position).get("pictureLinkDTO");*//*


            if (pictureMainDTO != null) {
                pictureMainDTO.setLastUploadDate(LocalDateTime.now());
                pictureMainDTO.setLastUploadUserId(userId);
                pictureMainDTO.setUploadTotal(pictureMainDTO.getUploadTotal() + 1);
            } else {
                Map<String, Object> map = pictureBeanHelper.createThumbnail();

                pictureMainDTO = PictureMainDTO.builder().uniqueHash(pictureLinkDTO.getUniqueHash())
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
                        .build();
            }
            //向数据库插入main bean 再更新用户表、相册表 再插入图片link表 bean

        } else {
            return RespBean.error("上传失败:", null);
        }
        return RespBean.ok("上传成功:", pictureLinkDTO);
    }*/


/*    @PostMapping("/upload/{gallery}")
    public RespBean upload(@RequestParam("file") MultipartFile[] files,
                           @PathVariable("gallery") Long galleryId,
                           @RequestParam("user") String userId) throws IOException {

        PictureLinkDTO[] pictureLinkDTO = new PictureLinkDTO[files.length];
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (FilePath.isImage(file.getContentType())) {
                pictureLinkDTO[i] = (PictureLinkDTO) getPictureBean(file, galleryId, i).get("pictureLinkDTO");
            } else {
                return RespBean.error("上传失败:", null);
            }
        }
        return RespBean.ok("上传成功:", pictureLinkDTO);
    }*/

  /*  private Map<String, Object> getPictureBean(PictureBeanHelper pictureBeanHelper, MultipartFile file,
                                               Long galleryId, Integer position) throws IOException {
        String fileName = file.getOriginalFilename();
        Map<String, Object> pictureMsg = pictureBeanHelper.createThumbnail();
        String basePath = (String) pictureMsg.get(HelperMapKeys.BasePath_KEY);
        String imageMsg = (String) pictureMsg.get(HelperMapKeys.HashMsg_KEY);
        String fileHash = (String) pictureMsg.get(HelperMapKeys.FileHash_KEY);
        String uniqueHash = (String) pictureMsg.get(HelperMapKeys.UniqueHash_KEY);
        String destPath = basePath + File.separator + uniqueHash + "." + FilePath.getSuffix(file.getContentType());
        String filePath = destPath + "." + FilePath.getSuffix(file.getContentType());
        BufferedImage minImage = (BufferedImage) pictureMsg.get(HelperMapKeys.MinThumbnail_KEY);
        BufferedImage midImage = (BufferedImage) pictureMsg.get(HelperMapKeys.MidThumbnail_KEY);
        PictureBeanHelper.createFolders(basePath);
        file.transferTo(new File(filePath));
        ImageIO.write(minImage, "JPG", new File(destPath + MIN_SUFFIX));
        ImageIO.write(midImage, "JPG", new File(destPath + MID_SUFFIX));
        PictureLinkDTO pictureLinkDTO = PictureLinkDTO.builder().linkId(new SnowFlake(1, 2).nextId())
                .userId(0l).galleryId(galleryId).uniqueHash(uniqueHash).filePath(filePath)
                .createDate(LocalDateTime.now()).originName(fileName).description(null)
                .pictureStyle(null).url(null).position(position).status(1)
                .build();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("pictureLinkDTO", pictureLinkDTO);
        returnMap.put("otherMag", pictureMsg);
        return returnMap;
    }*/

   /* public static PictureMainDTO mainBeanDefaultGenerate() {
        PictureMainDTO pictureMainDTO = PictureMainDTO.builder().uniqueHash()
                .hammingHash()
                .fileHash()
                .filePath()
                .pictureStyle()
                .picturePixel()
                .fileSize()
                .likeTotal()
                .viewedTotal()
                .downloadedTotal()
                .searchedTotal()
                .fileSuffix()
                .uploadTotal()
                .lastUploadUserId()
                .adminUpload()
                .lastUploadDate()
                .weights()
                .status()
                .build();
    }*/

}
