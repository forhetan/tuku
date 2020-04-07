package cn.picturecool.model;

import lombok.Data;

import java.util.Map;

/**
 * @program: tuku
 * @description: 请求体
 * @author: 赵元昊
 * @create: 2020-03-26 17:57
 **/
@Data
public class RequestBean {
    private String requestMsg;
    private Map<Object,Object> requestMap;
}
