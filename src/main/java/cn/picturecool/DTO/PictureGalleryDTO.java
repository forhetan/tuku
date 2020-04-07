package cn.picturecool.DTO;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * (PictureGallery)实体类
 *
 * @author makejava
 * @since 2020-02-23 17:39:49
 */
@Builder
@Data
@TableName("picture_gallery")
public class PictureGalleryDTO {
    @TableId
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long galleryId;
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long userId;
    private String galleryName;
    private LocalDateTime createDate;
    //private Integer likeTotal;
    private Integer pictureTotal;
    private String description;
    //private Integer status;
}