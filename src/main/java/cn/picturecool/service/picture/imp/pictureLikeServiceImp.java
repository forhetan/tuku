package cn.picturecool.service.picture.imp;

import cn.picturecool.DTO.PictureLikeDTO;
import cn.picturecool.mapper.PictureLikeMapper;
import cn.picturecool.service.picture.PictureLikeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-04 14:27
 **/
@Service
public class pictureLikeServiceImp implements PictureLikeService {

    @Autowired
    private PictureLikeMapper pictureLikeMapper;

    @Override
    public int insertLike(PictureLikeDTO pictureLikeDTO) {
        return pictureLikeMapper.insert(pictureLikeDTO);
    }

    @Override
    public List<PictureLikeDTO> findLikeByUserId(long userId) {
        QueryWrapper<PictureLikeDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("create_date");
        return pictureLikeMapper.selectList(queryWrapper);
    }

    @Override
    public List<PictureLikeDTO> findLikeByUniqueHash(String uniqueHash) {
        QueryWrapper<PictureLikeDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unique_hash", uniqueHash);
        return pictureLikeMapper.selectList(queryWrapper);
    }

    @Override
    public PictureLikeDTO findLikeByUniqueHashAndUserId(String uniqueHash, long userId) {
        QueryWrapper<PictureLikeDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unique_hash", uniqueHash).eq("user_id", userId);
        return pictureLikeMapper.selectOne(queryWrapper);
    }

    @Override
    public int deleteLikeByUserId(long userId) {
        UpdateWrapper<PictureLikeDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId);
        return pictureLikeMapper.delete(wrapper);
    }

    @Override
    public int deleteLikeByUserIdAndUniqueHash(String uniqueHash, long userId) {
        UpdateWrapper<PictureLikeDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId).eq("unique_hash", uniqueHash);
        return pictureLikeMapper.delete(wrapper);
    }

    @Override
    public int deleteLikeByUniqueHash(String uniqueHash) {
        UpdateWrapper<PictureLikeDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("unique_hash", uniqueHash);
        return pictureLikeMapper.delete(wrapper);
    }
}
