package cn.picturecool.service.picture;

import cn.picturecool.DTO.PictureLinkDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface pictureLinkService {
    int insertLink(PictureLinkDTO pictureLinkDTO);

    List<PictureLinkDTO> findLinkByGalleryId(long galleryId);

    List<PictureLinkDTO> findLinkByUserId(long userId);

    List<PictureLinkDTO> findLinkByWord(String word);

    List<PictureLinkDTO> findLinkByUniqueHashOrderByTime(String uniqueHash);

    PictureLinkDTO findLinkByGalleryIdAndUniqueHash(long galleryId, String uniqueHash);

    PictureLinkDTO findLinkByGalleryIdAndUniqueHashAndTime(long galleryId, String uniqueHash,LocalDateTime createDate);

    int deleteLinkByGalleryIdAndUniqueHash(long galleryId, String uniqueHash);

    int deleteLinkByGalleryIdAndUniqueHashAndDate(long galleryId, String uniqueHash, LocalDateTime createDate);
}
