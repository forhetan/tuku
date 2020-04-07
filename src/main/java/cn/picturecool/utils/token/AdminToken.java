package cn.picturecool.utils.token;

import cn.picturecool.DTO.AdminDTO;
import cn.picturecool.DTO.PictureUserDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: tuku
 * @description: user类生成token token校验生成user类
 * @author: 赵元昊
 * @create: 2020-03-24 18:58
 **/
public class AdminToken {

    static public String getToken(AdminDTO adminDTO) {
        String token = new String();
        Map<String, String> map = new HashMap<>();
        map.put("adminName", adminDTO.getAdminName());
        try {
            token = TokenTools.createToken(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    static public AdminDTO getUser(String token) {
        Map<String,String> map;
        AdminDTO adminDTO =new AdminDTO();
        try {
            map = TokenTools.verifyToken(token);
            adminDTO.setAdminName(map.get("adminName"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
        return adminDTO;
    }
}
