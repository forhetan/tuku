package cn.picturecool.service.picture.imp;

import cn.picturecool.DTO.PictureLinkDTO;
import cn.picturecool.mapper.PictureLinkMapper;
import cn.picturecool.service.picture.pictureLinkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-04 14:27
 **/
@Service
public class PictureLinkServiceImp implements pictureLinkService {

    @Autowired
    private PictureLinkMapper pictureLinkMapper;

    @Override
    public int insertLink(PictureLinkDTO pictureLinkDTO) {
        return pictureLinkMapper.insert(pictureLinkDTO);
    }

    @Override
    public List<PictureLinkDTO> findLinkByGalleryId(long galleryId) {
        QueryWrapper<PictureLinkDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gallery_id", galleryId).orderByDesc("create_date");
        return pictureLinkMapper.selectList(queryWrapper);
    }

    @Override
    public List<PictureLinkDTO> findLinkByUserId(long userId) {
        QueryWrapper<PictureLinkDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("create_date");
        return pictureLinkMapper.selectList(queryWrapper);
    }

    @Override
    public List<PictureLinkDTO> findLinkByWord(String word) {
        QueryWrapper<PictureLinkDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("description", word)
                .or().like("origin_name", word)
                .or().like("picture_style", word)
                .orderByDesc("create_date");
        return pictureLinkMapper.selectList(queryWrapper);
    }

    @Override
    public List<PictureLinkDTO> findLinkByUniqueHashOrderByTime(String uniqueHash) {
        QueryWrapper<PictureLinkDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unique_hash", uniqueHash)
                .orderByDesc("create_date");
        return pictureLinkMapper.selectList(queryWrapper);
    }

    @Override
    public PictureLinkDTO findLinkByGalleryIdAndUniqueHash(long galleryId, String uniqueHash) {
        QueryWrapper<PictureLinkDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gallery_id", galleryId).eq("unique_hash", uniqueHash);
        return pictureLinkMapper.selectOne(queryWrapper);
    }

    @Override
    public PictureLinkDTO findLinkByGalleryIdAndUniqueHashAndTime(long galleryId, String uniqueHash, LocalDateTime createDate) {
        QueryWrapper<PictureLinkDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gallery_id", galleryId).eq("unique_hash", uniqueHash).eq("create_date",createDate);
        return pictureLinkMapper.selectOne(queryWrapper);
    }

    @Override
    public int deleteLinkByGalleryIdAndUniqueHash(long galleryId, String uniqueHash) {
        UpdateWrapper<PictureLinkDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("gallery_id", galleryId).eq("unique_hash", uniqueHash);
        return pictureLinkMapper.delete(updateWrapper);
    }

    @Override
    public int deleteLinkByGalleryIdAndUniqueHashAndDate(long galleryId, String uniqueHash, LocalDateTime createDate) {
        UpdateWrapper<PictureLinkDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("gallery_id", galleryId)
                .eq("unique_hash", uniqueHash)
                .eq("create_date",createDate);
        return pictureLinkMapper.delete(updateWrapper);
    }
}
