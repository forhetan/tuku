package cn.picturecool;

import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.mapper.PictureUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @program: tuku
 * @description: CRUD
 * @author: 赵元昊
 * @create: 2020-02-25 11:59
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCRUDApplication {

    @Autowired
    private PictureUserMapper pictureUserMapper;

    @Test
    public void testInsert() {
        PictureUserDTO pictureUserDTO = PictureUserDTO.build(1088250446457389058L, "hetan", "123456");
        int count = pictureUserMapper.insert(pictureUserDTO);
        System.out.println(count);
    }

}
