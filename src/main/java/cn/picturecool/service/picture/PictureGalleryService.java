package cn.picturecool.service.picture;

import cn.picturecool.DTO.PictureGalleryDTO;

import java.util.List;

public interface PictureGalleryService {
    PictureGalleryDTO findGalleryById(long id);

    List<PictureGalleryDTO>  findGalleryListByUserId(long userId);

    int insertGallery(PictureGalleryDTO pictureGalleryDTO);

    int updatePictureTotal(Long galleryID,int pictureTotal);

    int updateGalleryNameAadDescription(Long galleryID,String galleryName,String description);
    int deleteGalleryById(Long galleryId);
}
