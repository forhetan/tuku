package cn.picturecool.service.user.imp;

import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.mapper.PictureUserMapper;
import cn.picturecool.service.user.PictureUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-02-25 12:33
 **/
@Service
public class PictureUserServiceImp implements PictureUserService {

    @Autowired
    private PictureUserMapper pictureUserMapper;

    @Override
    public int insertUser(PictureUserDTO pictureUserDTO) {
        return pictureUserMapper.insert(pictureUserDTO);
    }

    @Override
    public PictureUserDTO findUser(String userName, String password) {
        QueryWrapper<PictureUserDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName).eq("password", password);
        return pictureUserMapper.selectOne(queryWrapper);
    }

    @Override
    public PictureUserDTO findUserById(long userId) {
        return pictureUserMapper.selectById(userId);
    }

    @Override
    public int updatePassword(String userName, String newPassword) {
        UpdateWrapper<PictureUserDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name", userName).set("password", newPassword);
        return pictureUserMapper.update(null, updateWrapper);
    }

    @Override
    public int updatePassword(Long userId, String newPassword) {
        UpdateWrapper<PictureUserDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).set("password", newPassword);
        return pictureUserMapper.update(null, updateWrapper);
    }

    @Override
    public int updateUserName(Long userId, String newUserName) {
        UpdateWrapper<PictureUserDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).set("user_name", newUserName);
        return pictureUserMapper.update(null, updateWrapper);
    }

    @Override
    public int updatePhone(Long userId, String phone) {
        UpdateWrapper<PictureUserDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).set("phone", phone);
        return pictureUserMapper.update(null, updateWrapper);
    }

    @Override
    public int updateEmail(Long userId, String email) {
        UpdateWrapper<PictureUserDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).set("email", email);
        return pictureUserMapper.update(null, updateWrapper);
    }

    @Override
    public int updatePictureTotal(long userId,int pictureTotal) {
        UpdateWrapper<PictureUserDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).set("picture_total", pictureTotal);
        return pictureUserMapper.update(null, updateWrapper);
    }

    @Override
    public int updateGalleryTotal(long userId, int galleryTotal) {
        UpdateWrapper<PictureUserDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).set("gallery_total", galleryTotal);
        return pictureUserMapper.update(null, updateWrapper);
    }

    @Override
    public int updateLikeTotal(long userId,int likeTotal) {
        UpdateWrapper<PictureUserDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).set("like_total", likeTotal);
        return pictureUserMapper.update(null, updateWrapper);
    }

    @Override
    public int updatePictureTotalAndGalleryTotal(long userId, int pictureTotal, int GalleryTotal) {
        UpdateWrapper<PictureUserDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).set("gallery_total", GalleryTotal).set("picture_total",pictureTotal);
        return pictureUserMapper.update(null, updateWrapper);
    }

    @Override
    public int deleteByUserId(long userId) {
        UpdateWrapper<PictureUserDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId);
        return pictureUserMapper.delete(wrapper);
    }

    @Override
    public IPage<PictureUserDTO> selectAllPage(Integer size, Integer current) {
        QueryWrapper<PictureUserDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("create_date");
        Page<PictureUserDTO> page = new Page<>(current, size);
        return pictureUserMapper.selectPage(page, queryWrapper);
    }

}
