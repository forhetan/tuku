package cn.picturecool.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-25 14:33
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageInfo<T> extends Page<T> {
    private static final long serialVersionUID = 5194933845448697148L;

    private Integer selectInt;
    private String selectStr;
    private String name;

    public PageInfo(long current, long size) {
        super(current, size);
    }
}
