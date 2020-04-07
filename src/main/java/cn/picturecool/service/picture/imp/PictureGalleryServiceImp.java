package cn.picturecool.service.picture.imp;

import cn.picturecool.DTO.PictureGalleryDTO;
import cn.picturecool.DTO.PictureLikeDTO;
import cn.picturecool.mapper.PictureGalleryMapper;
import cn.picturecool.service.picture.PictureGalleryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-01 18:27
 **/
@Service
public class PictureGalleryServiceImp implements PictureGalleryService {

    @Autowired
    private PictureGalleryMapper pictureGalleryMapper;

    @Override
    public PictureGalleryDTO findGalleryById(long id) {
        return pictureGalleryMapper.selectById(id);
    }

    @Override
    public List<PictureGalleryDTO> findGalleryListByUserId(long userId) {
        QueryWrapper<PictureGalleryDTO> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).orderByDesc("create_date");
        return pictureGalleryMapper.selectList(queryWrapper);
    }

    @Override
    public int insertGallery(PictureGalleryDTO pictureGalleryDTO) {
        return pictureGalleryMapper.insert(pictureGalleryDTO);
    }

    @Override
    public int updatePictureTotal(Long galleryID, int pictureTotal) {
        UpdateWrapper<PictureGalleryDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("gallery_id", galleryID).set("picture_total", pictureTotal);
        return pictureGalleryMapper.update(null, updateWrapper);
    }

    @Override
    public int updateGalleryNameAadDescription(Long galleryID, String galleryName, String description) {
        UpdateWrapper<PictureGalleryDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("gallery_id", galleryID).set("gallery_name", galleryName).set("description",description);
        return pictureGalleryMapper.update(null, updateWrapper);
    }

    @Override
    public int deleteGalleryById(Long galleryId) {
        UpdateWrapper<PictureGalleryDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("gallery_id", galleryId);
        return pictureGalleryMapper.delete(wrapper);
    }

}
