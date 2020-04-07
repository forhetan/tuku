package cn.picturecool.DTO;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * (PictureMain)实体类
 *
 * @author makejava
 * @since 2020-02-23 17:39:49
 */
@Builder
@Data
@TableName("picture_main")
public class PictureMainDTO{
    @TableId
    private String uniqueHash;
    private String hammingHash;
    private String fileHash;
    private String filePath;
    private String pictureStyle;
    private String picturePixel;//
    private Long fileSize;//
    private Integer likeTotal;
    private Integer viewedTotal;
    private Integer downloadedTotal;
    private Integer searchedTotal;
    private String fileSuffix;//
    private Integer uploadTotal;
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long lastUploadUserId;
    private Integer adminUpload;
    private LocalDateTime lastUploadDate;
    private Object weights;
    private Integer status;
}