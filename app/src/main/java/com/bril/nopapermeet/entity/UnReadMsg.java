package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 123456 on 2017/3/30.
 */
@Entity
public class UnReadMsg {

    @Id
    public Long id;
    public String title;
    public String time;
    public String content;
    
    @Generated(hash = 907646704)
    public UnReadMsg(Long id, String title, String time, String content) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;
    }
    @Generated(hash = 699535280)
    public UnReadMsg() {
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
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
