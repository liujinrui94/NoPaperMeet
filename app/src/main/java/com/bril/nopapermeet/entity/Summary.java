package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/8.
 */
@Entity
public class Summary  {
    @Id
    public Long id;
    public Long meetId;
    public String content;

    @Generated(hash = 1461598545)
    public Summary() {
    }
    @Generated(hash = 1954700637)
    public Summary(Long id, Long meetId, String content) {
        this.id = id;
        this.meetId = meetId;
        this.content = content;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getMeetId() {
        return this.meetId;
    }
    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }
}
