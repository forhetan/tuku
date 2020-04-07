package cn.picturecool.service.admin;

import cn.picturecool.DTO.AdminDTO;

public interface PictureAdminService {

    AdminDTO findAdmin(String userName, String password);

    AdminDTO findAdminByName(String userName);

}
