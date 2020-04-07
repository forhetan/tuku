package cn.picturecool.controller.test;

import cn.picturecool.authorization.annotation.Authorization;
import cn.picturecool.model.RespBean;
import cn.picturecool.service.picture.imp.PictureLinkServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-27 12:28
 **/
@RestController
public class TestLinkController {
    @Autowired
    private PictureLinkServiceImp pictureLinkServiceImp;
    @GetMapping("/test/link/{uniqueHash}")
    public RespBean linkFindTest(@PathVariable String uniqueHash){
        return RespBean.ok("",pictureLinkServiceImp.findLinkByUniqueHashOrderByTime(uniqueHash));
    }
}
