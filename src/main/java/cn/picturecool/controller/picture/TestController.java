package cn.picturecool.controller.picture;

import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.authorization.annotation.Authorization;
import cn.picturecool.authorization.annotation.CurrentUser;
import cn.picturecool.model.RequestBean;
import cn.picturecool.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-24 20:54
 **/
@RestController
public class TestController {
    @Authorization
    @PostMapping("/testuser")
    public RespBean testAnnotation(@CurrentUser PictureUserDTO pictureUserDTO){
        return RespBean.ok("",pictureUserDTO);
    }
    @GetMapping("/testshare")
    public RespBean testShare(){
        RequestBean requestBean=new RequestBean();
        requestBean.setRequestMsg("aaaaaa");
        List<String> list =new ArrayList<>();
        list.add("adhikshds");
        list.add("adhssodfh");
        list.add("adkljfa");
        Map<Object,Object> map=new HashMap<>();
        map.put("list",list);
        requestBean.setRequestMap(map);
        return RespBean.ok("",requestBean);
    }
}
