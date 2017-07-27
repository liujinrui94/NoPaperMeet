package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * 参会人
 */
@Entity
public class MeetPerson implements Serializable {
    @Transient
    public static final long serialVersionUID = 1L;
    @Id
    public Long id;
    @NotNull
    public Long meetId;
    @NotNull
    public Long accountId;

    @Generated(hash = 1933524497)
    public MeetPerson(Long id, @NotNull Long meetId, @NotNull Long accountId) {
        this.id = id;
        this.meetId = meetId;
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "MeetPerson{" +
                "id=" + id +
                ", meetId=" + meetId +
                ", accountId=" + accountId +
                '}';
    }

    @Generated(hash = 1126301514)
    public MeetPerson() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getMeetId() {
        return this.meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }
}
