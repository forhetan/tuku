package cn.picturecool.service.admin;

import cn.picturecool.DTO.AdminPictureDTO;


import java.util.List;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-02-28 15:01
 **/
public interface AdminPictureService {
    AdminPictureDTO findPictureByUniqueHash(String uniqueHash);

    List<AdminPictureDTO> selectAll();

    int insertMain(AdminPictureDTO adminPictureDTO);

    int deleteByUniqueHash(String uniqueHash);
}
