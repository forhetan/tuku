package cn.picturecool.controller.download;

import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.picture.PictureMainService;
import cn.picturecool.service.picture.PictureShareService;
import cn.picturecool.utils.md5.HideMD5;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-26 19:33
 **/
@RestController
public class DownloadShareController {
    @Autowired
    private PictureMainService pictureMainService;
    @Autowired
    private PictureShareService pictureShareService;

    static private int BUFFER_SIZE = 1024 * 1024;

    private String baseUrl = "http://planckspace.xyz:9999/download/share/";

    @GetMapping("/download/share/qrcode/{shareId}")
    public RespBean getQRCode(@PathVariable("shareId") String shareId,
                              HttpServletResponse response) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String destUrl = baseUrl + shareId;
        BitMatrix bitMatrix = qrCodeWriter.encode(destUrl, BarcodeFormat.QR_CODE, 350, 350);
        response.addHeader("Content-Disposition", "attachment;fileName=" + "qr" + ".png");// 设置文件名
        OutputStream os = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", os);
        return RespBean.error("");
    }

    @GetMapping("/download/share/{shareId}")
    public RespBean getShareById(@PathVariable("shareId") String shareId,
                                 HttpServletResponse response) throws IOException {
        if (pictureShareService.hasUniqueHashList(shareId)) {
            List<String> list = pictureShareService.getUniqueHashList(shareId);
            List<PictureMainDTO> mainDTOList = new ArrayList<>();
            for (String uniqueHash : list) {
                PictureMainDTO picture = pictureMainService.findPictureByUniqueHash(uniqueHash);
                if (picture != null) {
                    mainDTOList.add(picture);
                }
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(bos);
            for (PictureMainDTO pictureMainDTO : mainDTOList) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(pictureMainDTO.getUniqueHash() + "." + pictureMainDTO.getFileSuffix()));
                int len;
                FileInputStream in = new FileInputStream(
                        new File(pictureMainDTO.getFilePath()
                                + "." + pictureMainDTO.getFileSuffix()));
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                pictureMainService.updateMainDownloadTotal(pictureMainDTO.getUniqueHash(), pictureMainDTO.getDownloadedTotal() + 1);
                pictureMainService.uploadWeightsByUniqueHash(pictureMainDTO.getUniqueHash());
            }
            zos.close();
            response.addHeader("Content-Disposition", "attachment;fileName=" + shareId + ".zip");// 设置文件名
            OutputStream os = response.getOutputStream();
            bos.writeTo(os);
            bos.close();
            os.close();
            return RespBean.ok("", list);
        } else {
            return RespBean.error("");
        }
    }

    @PostMapping("/download/share")
    public RespBean sharePictureList(@RequestParam("uniqueHash") String[] uniqueHashList) throws Exception {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < uniqueHashList.length; i++) {
            if (!list.contains(uniqueHashList[i])) {
                PictureMainDTO pictureByUniqueHash = pictureMainService.findPictureByUniqueHash(uniqueHashList[i]);
                if (pictureByUniqueHash != null) {
                    list.add(uniqueHashList[i]);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String uniqueHash : list) {
            stringBuilder.append(uniqueHash);
        }
        System.out.println(list);
        String hashName = HideMD5.getMD5(stringBuilder.toString());
        if (!pictureShareService.hasUniqueHashList(hashName)) {
            if (pictureShareService.saveUniqueHashList(hashName, list) > 0) {
                Map<Object, Object> map = new HashMap<>();
                map.put("share", hashName);
                return RespBean.ok("", map);
            }
        } else {
            Map<Object, Object> map = new HashMap<>();
            map.put("share", hashName);
            return RespBean.ok("", map);
        }
        return RespBean.error("");
    }
}
