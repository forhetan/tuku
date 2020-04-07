package cn.picturecool.model;

public class RespBean {

    public static final Integer OK=200;
    public static final Integer ERROR=500;
    public static final Integer NOT_FOUND=404;
    public static final Integer PASSWORD_ERROR=40001;
    public static final Integer USERNAME_ERROR=40002;

    private Integer status;
    private String msg;
    private Object data;

    public static RespBean build(){
        return new RespBean();
    }

    public static RespBean ok(String msg){
        return new RespBean(OK,msg,null);
    }

    public static RespBean ok(String msg, Object data){
        return new RespBean(OK,msg,data);
    }

    public static RespBean error(String msg){
        return new RespBean(ERROR,msg,null);
    }

    public static RespBean error(String msg, Object data){
        return new RespBean(ERROR,msg,data);
    }

    private RespBean(){
    }

    private RespBean(Integer status, String msg, Object data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public RespBean setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RespBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RespBean setData(Object data) {
        this.data = data;
        return this;
    }
}
