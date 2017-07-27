package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * 议题
 */
@Entity
public class Lssue implements Serializable {
    public Lssue(Long meetId, String title, int state, String time, String reportName) {
        this.title = title;
        this.state = state;
        this.time = time;
        this.reportName = reportName;
        this.meetId = meetId;
    }

    @Generated(hash = 344523883)
    public Lssue(Long id, @NotNull Long meetId, String title, String time, String reportName,
            boolean isCheck, int state) {
        this.id = id;
        this.meetId = meetId;
        this.title = title;
        this.time = time;
        this.reportName = reportName;
        this.isCheck = isCheck;
        this.state = state;
    }

    @Generated(hash = 883843522)
    public Lssue() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public boolean getIsCheck() {
        return this.isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getMeetId() {
        return this.meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }

    @Transient
    public static final long serialVersionUID = 1L;
    @Id
    public Long id;
    @NotNull
    public Long meetId;
    public String title;
    public String time;
    public String reportName;
    public boolean isCheck;
    public int state;//1.进行中2.未进行.3.已结束
}
