package com.example.demo.domain;

import java.util.HashMap;
import java.util.Map;

public class Msg {
    private Integer code;
    private String massage;
    private Map<String,Object> map=new HashMap<>();

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public static Msg success(){
        Msg msg=new Msg();
        msg.setCode(200);
        msg.setMassage("请求成功");
        return msg;
    }
    public static Msg success(String info){
        Msg msg=new Msg();
        msg.setCode(200);
        msg.setMassage(info);
        return msg;
    }
    public static Msg error(){
        Msg msg=new Msg();
        msg.setCode(400);
        msg.setMassage("失败");
        return msg;
    }
    public static Msg error(String info){
        Msg msg=new Msg();
        msg.setCode(400);
        msg.setMassage(info);
        return msg;
    }

    public Msg add(String key,Object value){
        System.out.println(value.toString());

        this.map.put(key,value);
        return this;
    }

}
