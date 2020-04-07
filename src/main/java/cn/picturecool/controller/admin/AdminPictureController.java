package cn.picturecool.controller.admin;

import cn.picturecool.DTO.AdminDTO;
import cn.picturecool.DTO.AdminPictureDTO;
import cn.picturecool.authorization.admin.annotation.Admin;
import cn.picturecool.authorization.admin.annotation.CurrentAdmin;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.admin.AdminPictureService;
import cn.picturecool.utils.file.FilePath;
import cn.picturecool.utils.file.PictureBeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @program: tuku
 * @description: 首页轮播图的控制
 * @author: 赵元昊
 * @create: 2020-03-29 14:58
 **/
@RestController
public class AdminPictureController {


    static final String MIN_SUFFIX = "_min.jpg";
    static final String MID_SUFFIX = "_mid.jpg";

    @Autowired
    private AdminPictureService adminPictureService;

    @Admin
    @PostMapping("/admin/picture")
    public RespBean uploadNewPicture(@RequestParam("file") MultipartFile file,
                                     @CurrentAdmin AdminDTO adminDTO) throws IOException {

        PictureBeanHelper pictureBeanHelper;
        if (FilePath.isImage(file.getContentType())) {
            pictureBeanHelper = PictureBeanHelper.init(file.getInputStream());

            AdminPictureDTO adminPictureDTO = AdminPictureDTO.builder().uniqueHash(pictureBeanHelper.getUniqueHash())
                    .hammingHash(pictureBeanHelper.getMsgHash())
                    .fileHash(pictureBeanHelper.getFileHash())
                    .filePath(pictureBeanHelper.getBasePath() + File.separator
                            + pictureBeanHelper.getUniqueHash() + "_admin")
                    .picturePixel(pictureBeanHelper.getPicturePixel())
                    .fileSize(file.getSize())
                    .fileSuffix(FilePath.getSuffix(file.getContentType()))
                    .build();

            PictureBeanHelper.createFolders(pictureBeanHelper.getBasePath());
            file.transferTo(new File(adminPictureDTO.getFilePath()
                    + "." + FilePath.getSuffix(file.getContentType())));

            adminPictureService.insertMain(adminPictureDTO);
            return RespBean.ok("", adminPictureDTO);
        }
        return RespBean.error("");
    }

    @CrossOrigin
    @Admin
    @DeleteMapping("/admin/picture/{uniqueHash}")
    public RespBean deletePicture(@CurrentAdmin AdminDTO adminDTO,
                                  @PathVariable("uniqueHash") String uniqueHash) {


        if (adminPictureService.findPictureByUniqueHash(uniqueHash) != null) {
            adminPictureService.deleteByUniqueHash(uniqueHash);
            return RespBean.ok("");
        }
        return RespBean.error("");
    }

    @CrossOrigin
    @Admin
    @DeleteMapping("/admin/picture")
    public RespBean deleteAll(@CurrentAdmin AdminDTO adminDTO) {

        List<AdminPictureDTO> adminPictureDTOS = adminPictureService.selectAll();
        for (AdminPictureDTO adminPictureDTO : adminPictureDTOS) {
            adminPictureService.deleteByUniqueHash(adminPictureDTO.getUniqueHash());
        }
        return RespBean.ok("");
    }

}
