package cn.picturecool.service.admin.imp;

import cn.picturecool.DTO.AdminDTO;
import cn.picturecool.mapper.PictureAdminMapper;
import cn.picturecool.service.admin.PictureAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-02-25 12:33
 **/
@Service
public class PictureAdminServiceImp implements PictureAdminService {

    @Autowired
    private PictureAdminMapper pictureAdminMapper;


    @Override
    public AdminDTO findAdmin(String userName, String password) {
        QueryWrapper<AdminDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_name", userName).eq("password", password);
        return pictureAdminMapper.selectOne(queryWrapper);
    }

    @Override
    public AdminDTO findAdminByName(String userName) {
        QueryWrapper<AdminDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_name", userName);
        return pictureAdminMapper.selectOne(queryWrapper);
    }
}
