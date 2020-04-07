package cn.picturecool.service.picture;

import cn.picturecool.DTO.PictureLikeDTO;

import java.util.List;

public interface PictureLikeService {
    int insertLike(PictureLikeDTO pictureLikeDTO);

    List<PictureLikeDTO> findLikeByUserId(long userId);

    List<PictureLikeDTO> findLikeByUniqueHash(String uniqueHash);

    PictureLikeDTO findLikeByUniqueHashAndUserId(String uniqueHash, long userId);

    int deleteLikeByUserId(long userId);

    int deleteLikeByUserIdAndUniqueHash(String uniqueHash, long userId);

    int deleteLikeByUniqueHash(String uniqueHash);
}
