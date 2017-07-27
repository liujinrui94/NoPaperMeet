package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Administrator on 2017/3/31.
 */
@Entity
public class RecordList {
    @Id
    Long id;
    @NotNull
    Long meetId;
    String title;
    String time;
    String say;
    String content;

    public RecordList() {
    }

    @Generated(hash = 18011490)
    public RecordList(Long id, @NotNull Long meetId, String title, String time,
            String say, String content) {
        this.id = id;
        this.meetId = meetId;
        this.title = title;
        this.time = time;
        this.say = say;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
