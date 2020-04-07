package cn.picturecool.DTO;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-04-02 22:02
 **/
@Data
@TableName("picture_admin")
public class AdminDTO {
    @TableId("admin_name")
    private String adminName;
    private String password;
}
