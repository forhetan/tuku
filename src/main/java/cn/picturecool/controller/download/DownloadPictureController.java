package cn.picturecool.controller.download;

import cn.picturecool.DTO.AdminPictureDTO;
import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.admin.AdminPictureService;
import cn.picturecool.service.picture.PictureMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @program: tuku
 * @description: 下载控制器
 * @author: 赵元昊
 * @create: 2020-02-25 19:07
 **/

@RestController
public class DownloadPictureController {

    @Autowired
    private PictureMainService pictureMainService;
    @Autowired
    private AdminPictureService adminPictureService;

    @GetMapping("/download/picture/min/{id}")
    public RespBean getPictureOfMin(@PathVariable("id") String id) {
        return RespBean.ok("");
    }

    @GetMapping("/download/picture/mid/{id}")
    public RespBean getPictureOfMid(@PathVariable("id") String id,HttpServletResponse response) throws IOException {

        ServletOutputStream servletOutputStream = response.getOutputStream();
        //设置文件名称
        //response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileId, "utf-8"));
        //设置为图片格式
        response.setContentType("application/octet-stream;charset=UTF-8");
        //servletOutputStream.write(bytes);

        return RespBean.ok("");
    }

    @GetMapping("/download/picture/max/{id}")
    public RespBean getPictureOfMax(@PathVariable("id") String id, HttpServletResponse response) {
        PictureMainDTO pictureMainDTO = pictureMainService.findPictureByUniqueHash(id);
        if(pictureMainDTO==null){
            return RespBean.error("图片数据错误");
        }else {
            String filePath = pictureMainDTO.getFilePath()+"."+pictureMainDTO.getFileSuffix();
            File file =new File(filePath);
            //response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + id+"."+pictureMainDTO.getFileSuffix());// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return RespBean.ok("图片获取成功！");
    }

    @GetMapping("/download/picture/home/{id}")
    public RespBean getHomePicture(@PathVariable("id") String id, HttpServletResponse response) {

        AdminPictureDTO pictureByUniqueHash = adminPictureService.findPictureByUniqueHash(id);
        if(pictureByUniqueHash==null){
            return RespBean.error("图片数据错误");
        }else {
            String filePath = pictureByUniqueHash.getFilePath()+"."+pictureByUniqueHash.getFileSuffix();
            File file =new File(filePath);
            //response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + id+"."+pictureByUniqueHash.getFileSuffix());// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return RespBean.ok("图片获取成功！");
    }
}
