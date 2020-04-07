package cn.picturecool.DTO;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
/**
 * (PictureLink)实体类
 *
 * @author makejava
 * @since 2020-02-23 17:39:49
 */
@Builder
@Data
@TableName("picture_link")
public class PictureLinkDTO{
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long userId;//从前端传进来
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long galleryId;//从前端传进来
    private String uniqueHash;//生成
    //private String filePath;//生成
    private LocalDateTime createDate;//自动生成
    private String originName;//从源文件获取
    private String description;//从前端传进来
    private String pictureStyle;//从前端传进来
    //private String url;//假如是链接文件 则含有url，否则为null
    //private Integer position;//图片的相册位置 以后再说 真麻烦
    //private Integer status;//显示状态
}