package cn.picturecool.service.admin.imp;

import cn.picturecool.DTO.AdminPictureDTO;
import cn.picturecool.mapper.AdminPictureMapper;
import cn.picturecool.service.admin.AdminPictureService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-02-28 15:02
 **/
@Service
public class AdminPictureServiceImp implements AdminPictureService {

    @Autowired
    private AdminPictureMapper adminPictureMapper;

    @Override
    public AdminPictureDTO findPictureByUniqueHash(String uniqueHash) {
        return adminPictureMapper.selectById(uniqueHash);
    }

    @Override
    public int insertMain(AdminPictureDTO adminPictureDTO) {
        return adminPictureMapper.insert(adminPictureDTO);
    }

    @Override
    public int deleteByUniqueHash(String uniqueHash) {
        UpdateWrapper<AdminPictureDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("unique_hash", uniqueHash);
        return adminPictureMapper.delete(wrapper);
    }

    public List<AdminPictureDTO> selectAll() {
        return adminPictureMapper.selectList(null);
    }

}
