package cn.picturecool.controller.user;

import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.authorization.annotation.Authorization;
import cn.picturecool.authorization.annotation.CurrentUser;
import cn.picturecool.model.RequestBean;
import cn.picturecool.model.RespBean;
import cn.picturecool.model.UserEntity;
import cn.picturecool.service.user.PictureUserService;
import cn.picturecool.utils.id.SnowFlake;
import cn.picturecool.utils.md5.HideMD5;
import cn.picturecool.utils.token.UserToken;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: tuku
 * @description: 用户控制器
 * @author: 赵元昊
 * @create: 2020-03-26 17:45
 **/
@RestController
public class UserController {
    @Autowired
    private PictureUserService pictureUserService;

    @PostMapping("/user")
    public RespBean userRegister(@RequestParam("userName") String username,
                                 @RequestParam("userPassword") String password) throws Exception {
        long id = new SnowFlake(1, 1).nextId();
        String psMD5= HideMD5.getMD5Text(password);
        PictureUserDTO pictureUserDTO = PictureUserDTO.build(id, username, psMD5);
        if (pictureUserService.insertUser(pictureUserDTO) != 1) {
            return RespBean.error("注册失败");
        }
        UserEntity userEntity = UserEntity.toUserEntity(pictureUserService.findUser(username, psMD5));
        return RespBean.ok("注册成功", userEntity);
    }

    @PostMapping("/user/login")
    public RespBean userLogin(@RequestParam("userName") String username,
                              @RequestParam("userPassword") String password) throws Exception {
        String psMD5= HideMD5.getMD5Text(password);
        PictureUserDTO pictureUserDTO = pictureUserService.findUser(username, psMD5);
        if (pictureUserDTO != null) {
            String token = UserToken.getToken(pictureUserDTO);
            Map<Object, Object> map = new LinkedHashMap<>();
            map.put("token", token);
            /*map.put("userId",pictureUserDTO.getUserId());
            map.put("userName",pictureUserDTO.getUserName());*/
            UserEntity userEntity = UserEntity.toUserEntity(pictureUserDTO);
            return RespBean.ok("登陆成功", map);
        } else {
            return RespBean.error("用户名或密码错误");
        }
    }

    @Authorization
    @GetMapping("/user")
    public RespBean getUser(@CurrentUser PictureUserDTO pictureUserDTO) {
        if (pictureUserDTO != null) {
            pictureUserDTO.setPassword("");
            Map<Object, Object> map = new LinkedHashMap<>();
            map.put("user", pictureUserDTO);
            return RespBean.ok("用户信息", map);
        } else {
            return RespBean.error("用户信息错误");
        }
    }

    @Authorization
    @PutMapping("/user")
    public RespBean updateUser(@CurrentUser PictureUserDTO pictureUserDTO,
                               @RequestBody JSONObject jsonParam) {
        RequestBean requestBean = jsonParam.toJavaObject(RequestBean.class);
        if (requestBean.getRequestMsg().equals("password")) {
            String newPassword = (String) requestBean.getRequestMap().get("password");
            if ((newPassword != null) && (!newPassword.equals(""))) {
                if (pictureUserService.updatePassword(pictureUserDTO.getUserId(), newPassword) > 0) {
                    return RespBean.ok("");
                }
            }
            return RespBean.error("");
        } else if (requestBean.getRequestMsg().equals("userName")) {
            String newUserName = (String) requestBean.getRequestMap().get("userName");
            if ((newUserName != null) && (!newUserName.equals(""))) {
                if (pictureUserService.updateUserName(pictureUserDTO.getUserId(), newUserName) > 0) {
                    return RespBean.ok("");
                }
            }
            return RespBean.error("");
        } else if (requestBean.getRequestMsg().equals("userMsg")) {
            /*for (Map.Entry entry : requestBean.getRequestMap().entrySet()){
                switch ((String) entry.getKey()){
                    case ""
                }//??????我为啥要写这么复杂？？？脑袋抽了？？？
            }*/
            String phone =(String) requestBean.getRequestMap().get("phone");
            String email =(String) requestBean.getRequestMap().get("email");
            if (phone!=null){
                pictureUserService.updatePhone(pictureUserDTO.getUserId(),phone);
            }
            if (email!=null){
                pictureUserService.updateEmail(pictureUserDTO.getUserId(),email);
            }
            return RespBean.ok("");
        }
        return RespBean.error("修改信息失败");
    }

}
