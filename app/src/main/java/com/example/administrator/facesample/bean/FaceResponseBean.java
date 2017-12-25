package com.example.administrator.facesample.bean;

/**
 * Created by liujie on 2017/7/7.
 */

public class FaceResponseBean {

    /**
     * code : 200
     * msg : ok
     * company_id : 1
     * member_id : 1281
     */

    private int code;
    private String msg;
    private String company_id;
    private String member_id;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
}
