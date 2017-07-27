package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * 票
 */
@Entity
public class Ticket implements Serializable {
    @Transient
    public static final long serialVersionUID = 1L;
    @Id
    public Long id;
    public Long optionId;//投票ID
    public Long accoutId;//投票人

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", optionId=" + optionId +
                ", accoutId=" + accoutId +
                '}';
    }

    @Generated(hash = 798525654)
    public Ticket(Long id, Long optionId, Long accoutId) {
        this.id = id;
        this.optionId = optionId;
        this.accoutId = accoutId;
    }
    @Generated(hash = 941848399)
    public Ticket() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOptionId() {
        return this.optionId;
    }
    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }
    public Long getAccoutId() {
        return this.accoutId;
    }
    public void setAccoutId(Long accoutId) {
        this.accoutId = accoutId;
    }
}
