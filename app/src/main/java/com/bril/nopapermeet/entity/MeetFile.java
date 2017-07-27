package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Administrator on 2017/4/9.
 */
@Entity
public class MeetFile {

    @Id
    public Long id;
    @NotNull
    public String date;
    public String content;
    @Generated(hash = 382293426)
    public MeetFile(Long id, @NotNull String date, String content) {
        this.id = id;
        this.date = date;
        this.content = content;
    }
    @Generated(hash = 1611965540)
    public MeetFile() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
}


