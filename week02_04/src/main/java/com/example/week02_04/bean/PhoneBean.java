package com.example.week02_04.bean;

public class PhoneBean {

    /**
     * msg : 登录成功
     * code : 0
     * data : {"age":null,"appkey":"974cf475233fff33","appsecret":"9B8DEE208BAA4B6CF20C9F7A53238E67","createtime":"2018-12-07T18:54:14","email":null,"fans":null,"follow":null,"gender":null,"icon":null,"latitude":null,"longitude":null,"mobile":"18234073466","money":null,"nickname":null,"password":"8F669074CAF5513351A2DE5CC22AC04C","praiseNum":null,"token":"A5280A65F631C4886A4F5875F78B7C0F","uid":23011,"userId":null,"username":"18234073466"}
     */

    private String msg;
    private String code;
    private final String SUCCESS="0";
    public boolean isSuccess(){
        return code.equals(SUCCESS);
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
