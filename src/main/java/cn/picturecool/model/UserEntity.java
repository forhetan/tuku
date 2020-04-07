package cn.picturecool.model;

import cn.picturecool.DTO.PictureUserDTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * @program: PictureCoolV0.1
 * @description: 返回前端的用户bean
 * @author: 赵元昊
 * @create: 2020-02-23 18:00
 **/
@Builder
@Data
public class UserEntity {

    public static UserEntity toUserEntity(PictureUserDTO pictureUser) {
        UserEntity userEntity = UserEntity.builder().build();
        userEntity.setUserId(pictureUser.getUserId());
        userEntity.setUserName(pictureUser.getUserName());
        userEntity.setPhone(pictureUser.getPhone());
        userEntity.setEmail(pictureUser.getEmail());
        userEntity.setCreateDate(pictureUser.getCreateDate());
        userEntity.setGalleryTotal(pictureUser.getGalleryTotal());
        userEntity.setPictureTotal(pictureUser.getPictureTotal());
        userEntity.setLikeTotal(pictureUser.getLikeTotal());
        return userEntity;
    }

    private Long userId;

    private String userName;

    private String phone;

    private String email;

    private LocalDateTime createDate;

    private Integer galleryTotal;

    private Integer pictureTotal;

    private Integer likeTotal;
}
