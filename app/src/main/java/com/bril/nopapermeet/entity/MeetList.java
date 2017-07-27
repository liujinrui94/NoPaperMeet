package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by LiuJinrui on 2017/3/29.
 */
@Entity
public class MeetList implements Serializable {
    @Transient
    public static final long serialVersionUID = 1L;
    @Id
    public Long id;//
    public String title;//会议标题
    public String date;//会议时间
    public String msg;//会议内容

    public String place;//会议地点
    public String type;//会议类型
    public String require;//参会需求
    public String placeRequire;//会场需求
    public String people;//参会人员
    public String information;//参会资料


    public String hostName;//
    //主持人
    public Long hostId;
    public boolean isHost;
    public int state;//0.未开启 1,进行中 2，
    @ToMany(referencedJoinProperty = "meetId")
    public List<MeetPerson> meetPersons;//参会人
    @ToMany(referencedJoinProperty = "meetId")
    public List<Lssue> lssues;//
    @ToMany(referencedJoinProperty = "meetId")
    public List<Notice> notices;//
    @ToMany(referencedJoinProperty = "meetId")
    public List<Vote> votes;//
    @ToMany(referencedJoinProperty = "meetId")
    public List<RecordList> recordLists;//
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 145458612)
    private transient MeetListDao myDao;


    @Generated(hash = 138406045)
    public MeetList() {
    }

    @Generated(hash = 1566202014)
    public MeetList(Long id, String title, String date, String msg, String place, String type,
                    String require, String placeRequire, String people, String information, String hostName,
                    Long hostId, boolean isHost, int state) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.msg = msg;
        this.place = place;
        this.type = type;
        this.require = require;
        this.placeRequire = placeRequire;
        this.people = people;
        this.information = information;
        this.hostName = hostName;
        this.hostId = hostId;
        this.isHost = isHost;
        this.state = state;
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

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Long getHostId() {
        return this.hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public boolean getIsHost() {
        return this.isHost;
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1078385620)
    public List<MeetPerson> getMeetPersons() {
        if (meetPersons == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MeetPersonDao targetDao = daoSession.getMeetPersonDao();
            List<MeetPerson> meetPersonsNew = targetDao
                    ._queryMeetList_MeetPersons(id);
            synchronized (this) {
                if (meetPersons == null) {
                    meetPersons = meetPersonsNew;
                }
            }
        }
        return meetPersons;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1430081161)
    public synchronized void resetMeetPersons() {
        meetPersons = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequire() {
        return this.require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getPlaceRequire() {
        return this.placeRequire;
    }

    public void setPlaceRequire(String placeRequire) {
        this.placeRequire = placeRequire;
    }

    public String getPeople() {
        return this.people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getInformation() {
        return this.information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return this.type;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1795453267)
    public List<Lssue> getLssues() {
        if (lssues == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LssueDao targetDao = daoSession.getLssueDao();
            List<Lssue> lssuesNew = targetDao._queryMeetList_Lssues(id);
            synchronized (this) {
                if (lssues == null) {
                    lssues = lssuesNew;
                }
            }
        }
        return lssues;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 976560207)
    public synchronized void resetLssues() {
        lssues = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1610051996)
    public List<Notice> getNotices() {
        if (notices == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NoticeDao targetDao = daoSession.getNoticeDao();
            List<Notice> noticesNew = targetDao._queryMeetList_Notices(id);
            synchronized (this) {
                if (notices == null) {
                    notices = noticesNew;
                }
            }
        }
        return notices;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1545912460)
    public synchronized void resetNotices() {
        notices = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 460552941)
    public List<Vote> getVotes() {
        if (votes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VoteDao targetDao = daoSession.getVoteDao();
            List<Vote> votesNew = targetDao._queryMeetList_Votes(id);
            synchronized (this) {
                if (votes == null) {
                    votes = votesNew;
                }
            }
        }
        return votes;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1738347372)
    public synchronized void resetVotes() {
        votes = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 461972274)
    public List<RecordList> getRecordLists() {
        if (recordLists == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RecordListDao targetDao = daoSession.getRecordListDao();
            List<RecordList> recordListsNew = targetDao._queryMeetList_RecordLists(id);
            synchronized (this) {
                if (recordLists == null) {
                    recordLists = recordListsNew;
                }
            }
        }
        return recordLists;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 242951813)
    public synchronized void resetRecordLists() {
        recordLists = null;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 972390376)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMeetListDao() : null;
    }
}
