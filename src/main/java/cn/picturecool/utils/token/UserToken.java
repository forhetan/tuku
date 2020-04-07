package cn.picturecool.utils.token;

import cn.picturecool.DTO.PictureUserDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: tuku
 * @description: user类生成token token校验生成user类
 * @author: 赵元昊
 * @create: 2020-03-24 18:58
 **/
public class UserToken {

    static public String getToken(PictureUserDTO userDTO) {
        String token = new String();
        Map<String, String> map = new HashMap<>();
        map.put("userId", userDTO.getUserId().toString());
        map.put("userName", userDTO.getUserName());
        try {
            token = TokenTools.createToken(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    static public PictureUserDTO getUser(String token) {
        Map<String,String> map;
        PictureUserDTO pictureUserDTO=new PictureUserDTO();
        try {
            map = TokenTools.verifyToken(token);
            pictureUserDTO.setUserId(Long.valueOf(map.get("userId")));
            pictureUserDTO.setUserName(map.get("userName"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
        return pictureUserDTO;
    }
}
