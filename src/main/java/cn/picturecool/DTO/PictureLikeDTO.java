package cn.picturecool.DTO;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("picture_like")
public class PictureLikeDTO {

    @JSONField(serializeUsing= ToStringSerializer.class)
    private long userId;
    private String uniqueHash;
    private LocalDateTime createDate;
    //public int status;
    public PictureLikeDTO(){}

}