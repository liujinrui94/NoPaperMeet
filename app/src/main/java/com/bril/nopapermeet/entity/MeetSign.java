package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LiuJinrui on 2017/3/31.
 */
@Entity
public class MeetSign {

    @Id
    private Long id;
    private String number;
    private String name;
    private String department;
    private String duty;
    private String signtime;
    private boolean isSigned;


    @Generated(hash = 939057140)
    public MeetSign(Long id, String number, String name, String department,
            String duty, String signtime, boolean isSigned) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.department = department;
        this.duty = duty;
        this.signtime = signtime;
        this.isSigned = isSigned;
    }

    @Generated(hash = 1793445377)
    public MeetSign() {
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getSigntime() {
        return signtime;
    }

    public void setSigntime(String signtime) {
        this.signtime = signtime;
    }


    @Override
    public String toString() {
        return "MeetSign{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", duty='" + duty + '\'' +
                ", signtime='" + signtime + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsSigned() {
        return this.isSigned;
    }

    public void setIsSigned(boolean isSigned) {
        this.isSigned = isSigned;
    }
}
