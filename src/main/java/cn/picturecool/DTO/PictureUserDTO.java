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
 * @create: 2020-02-25 11:54
 **/
@Data
@TableName("picture_user")
public class PictureUserDTO {
    @TableId("user_id")
    @JSONField(serializeUsing= ToStringSerializer.class)
    private Long userId;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private LocalDateTime createDate;
    private Integer galleryTotal;
    private Integer pictureTotal;
    private Integer likeTotal;

    public static PictureUserDTO build(Long userId, String userName, String password) {
        PictureUserDTO pictureUser = new PictureUserDTO();
        pictureUser.setUserId(userId);
        pictureUser.setUserName(userName);
        pictureUser.setPassword(password);
        pictureUser.setCreateDate(LocalDateTime.now());
        pictureUser.setPhone(null);
        pictureUser.setEmail(null);
        pictureUser.setGalleryTotal(0);
        pictureUser.setPictureTotal(0);
        pictureUser.setLikeTotal(0);
        return pictureUser;
    }
}
