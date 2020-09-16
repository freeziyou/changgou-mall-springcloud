package entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/*****
 * @Description: entity: MQ消息封装
 ****/
public class Message implements Serializable {

    /**
     * 执行的操作  1：增加，2：修改,3：删除
     */
    private int code;

    /**
     * 数据
     */
    private Object content;

    /**
     * 发送的 routkey
     */
    @JSONField(serialize = false)
    private String routekey;

    /**
     * 交换机
     */
    @JSONField(serialize = false)
    private String exchange;

    public Message() {
    }

    public Message(int code, Object content) {
        this.code = code;
        this.content = content;
    }

    public Message(int code, Object content, String routekey, String exchange) {
        this.code = code;
        this.content = content;
        this.routekey = routekey;
        this.exchange = exchange;
    }

    public String getRoutekey() {
        return routekey;
    }

    public void setRoutekey(String routekey) {
        this.routekey = routekey;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
