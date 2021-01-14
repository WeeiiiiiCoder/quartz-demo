package com.lazyboy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @auther: zhouwei
 * @date: 2020/8/17 10:37
 */
public class Employee  {

    public static final Integer STATE_NORMAL = 0;
    public static final Integer STATE_DELETE = 1;

    public static final Integer GENDER_MAN = 0;
    public static final Integer GENDER_WOMAN = 1;

    private Integer id;

    private String code;

    private String name;

    private Integer dept_id;

    private String email;

    //0男 1女
    private Integer gender;

    private Integer state;

    private Integer suppliers_id;
    //是否推送邮件 true(1)推送 false(0)不推送
    private Boolean push_qn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDept_id() {
        return dept_id;
    }

    public void setDept_id(Integer dept_id) {
        this.dept_id = dept_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSuppliers_id() {
        return suppliers_id;
    }

    public void setSuppliers_id(Integer suppliers_id) {
        this.suppliers_id = suppliers_id;
    }

    public Boolean getPush_qn() {
        return push_qn;
    }

    public void setPush_qn(Boolean push_qn) {
        this.push_qn = push_qn;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static Integer getGenderMan() {
        return GENDER_MAN;
    }

    public static Integer getGenderWoman() {
        return GENDER_WOMAN;
    }
}