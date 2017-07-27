package com.bril.nopapermeet.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * 选项
 */
@Entity
public class Option implements Serializable {
    @Transient
    public static final long serialVersionUID = 1L;
    @Id
    public Long id;
    public Long voteId;
    public String name;
    @ToMany(referencedJoinProperty = "optionId")
    public List<Ticket> tickets;//获得的票
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1708785938)
    private transient OptionDao myDao;

    @Generated(hash = 52069100)
    public Option(Long id, Long voteId, String name) {
        this.id = id;
        this.voteId = voteId;
        this.name = name;
    }

    @Generated(hash = 104107376)
    public Option() {
    }

    public Option(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public Long getVoteId() {
        return this.voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", voteId=" + voteId +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1229277410)
    public List<Ticket> getTickets() {
        if (tickets == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TicketDao targetDao = daoSession.getTicketDao();
            List<Ticket> ticketsNew = targetDao._queryOption_Tickets(id);
            synchronized (this) {
                if (tickets == null) {
                    tickets = ticketsNew;
                }
            }
        }
        return tickets;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 949726766)
    public synchronized void resetTickets() {
        tickets = null;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1972897771)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOptionDao() : null;
    }
}
