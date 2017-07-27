package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LiuJinrui on 2017/3/31.
 */
@Entity
public class Vote implements Serializable {
    @Transient
    public static final long serialVersionUID = 1L;
    @Id
    public Long id;

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", meetId=" + meetId +
                ", isSigle=" + isSigle +
                ", options=" + options +
                ", theme='" + theme + '\'' +
                ", mostoption=" + mostoption +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                '}';
    }

    @NotNull
    public Long meetId;
    public boolean isSigle;
    @ToMany(referencedJoinProperty = "voteId")
    public List<Option> options;

    public String theme;
    public int mostoption;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 161899964)
    private transient VoteDao myDao;

    @Generated(hash = 90530136)
    public Vote(Long id, @NotNull Long meetId, boolean isSigle, String theme,
            int mostoption) {
        this.id = id;
        this.meetId = meetId;
        this.isSigle = isSigle;
        this.theme = theme;
        this.mostoption = mostoption;
    }

    @Generated(hash = 1235976307)
    public Vote() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsSigle() {
        return this.isSigle;
    }

    public void setIsSigle(boolean isSigle) {
        this.isSigle = isSigle;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getMostoption() {
        return this.mostoption;
    }

    public void setMostoption(int mostoption) {
        this.mostoption = mostoption;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 820623108)
    public List<Option> getOptions() {
        if (options == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OptionDao targetDao = daoSession.getOptionDao();
            List<Option> optionsNew = targetDao._queryVote_Options(id);
            synchronized (this) {
                if (options == null) {
                    options = optionsNew;
                }
            }
        }
        return options;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 37457025)
    public synchronized void resetOptions() {
        options = null;
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

    public Long getMeetId() {
        return this.meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 67043038)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getVoteDao() : null;
    }
}
