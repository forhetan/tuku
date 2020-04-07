package cn.picturecool.service.user;

import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.DTO.PictureUserDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface PictureUserService {

    int insertUser(PictureUserDTO pictureUserDTO);

    PictureUserDTO findUser(String userName, String password);

    PictureUserDTO findUserById(long userId);

    int updatePassword(String userName, String newPassword);

    int updatePassword(Long userId, String newPassword);

    int updateUserName(Long userId, String newUserName);

    int updatePhone(Long userId, String phone);

    int updateEmail(Long userId, String email);

    int updatePictureTotal(long userId, int pictureTotal);

    int updateGalleryTotal(long userId, int galleryTotal);

    int updateLikeTotal(long userId, int likeTotal);

    int updatePictureTotalAndGalleryTotal(long userId, int pictureTotal, int GalleryTotal);

    int deleteByUserId(long userId);

    IPage<PictureUserDTO> selectAllPage(Integer size, Integer current);

}
