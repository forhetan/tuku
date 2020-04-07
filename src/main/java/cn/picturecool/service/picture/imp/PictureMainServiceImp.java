package cn.picturecool.service.picture.imp;

import cn.picturecool.DTO.PictureLikeDTO;
import cn.picturecool.DTO.PictureMainDTO;
import cn.picturecool.mapper.PictureMainMapper;
import cn.picturecool.service.picture.PictureMainService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-02-28 15:02
 **/
@Service
public class PictureMainServiceImp implements PictureMainService {

    @Autowired
    private PictureMainMapper pictureMainMapper;

    @Override
    public PictureMainDTO findPictureByUniqueHash(String uniqueHash) {
        return pictureMainMapper.selectById(uniqueHash);
    }

    @Override
    public int insertMain(PictureMainDTO pictureMainDTO) {
        return pictureMainMapper.insert(pictureMainDTO);
    }

    @Override
    public int updateMainStyle(String uniqueHash, String pictureStyle) {
        UpdateWrapper<PictureMainDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("unique_hash", uniqueHash)
                .set("picture_style", pictureStyle);

        return pictureMainMapper.update(null, updateWrapper);
    }

    @Override
    public int updateMainLikeTotal(String uniqueHash, int likeTotal) {
        UpdateWrapper<PictureMainDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("unique_hash", uniqueHash)
                .set("like_total", likeTotal);

        return pictureMainMapper.update(null, updateWrapper);
    }

    @Override
    public int updateMainSearchTotal(String uniqueHash, int searchTotal) {
        UpdateWrapper<PictureMainDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("unique_hash", uniqueHash)
                .set("searched_total", searchTotal);

        return pictureMainMapper.update(null, updateWrapper);
    }

    @Override
    public int updateMainDownloadTotal(String uniqueHash, int downloadTotal) {
        UpdateWrapper<PictureMainDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("unique_hash", uniqueHash)
                .set("downloaded_total", downloadTotal);

        return pictureMainMapper.update(null, updateWrapper);
    }

    @Override
    public int updateByNewUpload(String uniqueHash, int uploadTotal, Long lastUploadUserId, LocalDateTime lastUploadDate, String pictureStyle) {
        UpdateWrapper<PictureMainDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("unique_hash", uniqueHash)
                .set("upload_total", uploadTotal)
                .set("last_upload_user_id", lastUploadUserId)
                .set("last_upload_date", lastUploadDate)
                .set("picture_style", pictureStyle);

        return pictureMainMapper.update(null, updateWrapper);
    }

    @Override
    public int updateUploadTotalAndUploadUser(String uniqueHash, int uploadTotal, Long lastUploadUserId, LocalDateTime lastUploadDate) {
        UpdateWrapper<PictureMainDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("unique_hash", uniqueHash)
                .set("upload_total", uploadTotal)
                .set("last_upload_user_id", lastUploadUserId)
                .set("last_upload_date", lastUploadDate);

        return pictureMainMapper.update(null, updateWrapper);
    }

    @Override
    public int updateUploadTotal(String uniqueHash, int uploadTotal) {
        UpdateWrapper<PictureMainDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("unique_hash", uniqueHash)
                .set("upload_total", uploadTotal);

        return pictureMainMapper.update(null, updateWrapper);
    }

    @Override
    public int deleteByUniqueHash(String uniqueHash) {
        UpdateWrapper<PictureMainDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("unique_hash", uniqueHash);
        return pictureMainMapper.delete(wrapper);
    }

    @Override
    public void uploadWeightsByUniqueHash(String uniqueHash) {
        PictureMainDTO pictureMainDTO = pictureMainMapper.selectById(uniqueHash);
        int weights = pictureMainDTO.getUploadTotal()
                + pictureMainDTO.getLikeTotal()
                + pictureMainDTO.getSearchedTotal()
                + pictureMainDTO.getViewedTotal();
        UpdateWrapper<PictureMainDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("unique_hash", uniqueHash)
                .set("weights", weights);
        pictureMainMapper.update(null, updateWrapper);
    }

    public List<PictureMainDTO> selectAll() {
        return pictureMainMapper.selectList(null);
    }

    @Override
    public List<PictureMainDTO> selectAllByTime() {
        QueryWrapper<PictureMainDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("last_upload_date");
        return pictureMainMapper.selectList(queryWrapper);
    }

    @Override
    public List<PictureMainDTO> selectAllByHot() {
        QueryWrapper<PictureMainDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("weights");
        return pictureMainMapper.selectList(queryWrapper);
    }

    @Override
    public List<PictureMainDTO> selectAllByLabel(String word) {
        QueryWrapper<PictureMainDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("picture_style", word)
                .orderByDesc("last_upload_date");
        return pictureMainMapper.selectList(queryWrapper);
    }

    @Override
    public List<PictureMainDTO> selectAllByAdminUpload() {
        QueryWrapper<PictureMainDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_upload", 1)
                .orderByDesc("last_upload_date");
        return pictureMainMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<PictureMainDTO> selectAllByLabelPage(Integer size, Integer current, String word) {
        QueryWrapper<PictureMainDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("picture_style", word)
                .orderByDesc("last_upload_date");
        Page<PictureMainDTO> page = new Page<>(current, size);
        return pictureMainMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage<PictureMainDTO> selectAllByTimePage(Integer size, Integer current) {
        QueryWrapper<PictureMainDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("last_upload_date");
        Page<PictureMainDTO> page = new Page<>(current, size);
        return pictureMainMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage<PictureMainDTO> selectAllByHotPage(Integer size, Integer current) {
        QueryWrapper<PictureMainDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("weights");
        Page<PictureMainDTO> page = new Page<>(current, size);
        return pictureMainMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage<PictureMainDTO> selectAllPage(Integer size, Integer current) {
        Page<PictureMainDTO> page = new Page<>(current, size);
        return pictureMainMapper.selectPage(page, null);
    }
}
