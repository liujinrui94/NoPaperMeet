package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 账户
 */
@Entity
public class Account {
    @Id
    public Long id;
    public String loginName;
    public String loginID;
    public String nickName;
    public String job;
    public String phone;
    public String email;
    public String photoUrl;
    public String departmentName;
    public String departmentId;
    @Generated(hash = 1079028499)
    public Account(Long id, String loginName, String loginID, String nickName,
            String job, String phone, String email, String photoUrl,
            String departmentName, String departmentId) {
        this.id = id;
        this.loginName = loginName;
        this.loginID = loginID;
        this.nickName = nickName;
        this.job = job;
        this.phone = phone;
        this.email = email;
        this.photoUrl = photoUrl;
        this.departmentName = departmentName;
        this.departmentId = departmentId;
    }
    @Generated(hash = 882125521)
    public Account() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLoginName() {
        return this.loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getLoginID() {
        return this.loginID;
    }
    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getJob() {
        return this.job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhotoUrl() {
        return this.photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public String getDepartmentName() {
        return this.departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getDepartmentId() {
        return this.departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
