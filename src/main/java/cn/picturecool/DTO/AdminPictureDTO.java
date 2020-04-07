package cn.picturecool.DTO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * (PictureMain)实体类
 *
 * @author makejava
 * @since 2020-02-23 17:39:49
 */
@Builder
@Data
@TableName("picture_admin_main")
public class AdminPictureDTO {
    @TableId
    private String uniqueHash;
    private String hammingHash;
    private String fileHash;
    private String filePath;
    private String picturePixel;
    private Long fileSize;
    private String fileSuffix;
}