package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * 通知通告
 */
@Entity
public class Notice implements Serializable {
    @Transient
    public static final long serialVersionUID = 1L;
    @Id
    public Long id;
    @NotNull
    public Long meetId;
    public String title;
    public String msessage;
    public String time;
    public boolean isCheck;

    @Generated(hash = 2007284613)
    public Notice(Long id, @NotNull Long meetId, String title, String msessage,
            String time, boolean isCheck) {
        this.id = id;
        this.meetId = meetId;
        this.title = title;
        this.msessage = msessage;
        this.time = time;
        this.isCheck = isCheck;
    }

    @Generated(hash = 1880392847)
    public Notice() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsessage() {
        return this.msessage;
    }

    public void setMsessage(String msessage) {
        this.msessage = msessage;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getIsCheck() {
        return this.isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeetId() {
        return this.meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }


}
