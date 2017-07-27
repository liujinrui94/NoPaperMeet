package com.bril.nopapermeet.dao;



import com.bril.nopapermeet.entity.Account;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * 登录账户数据
 */
public class DbAccount {

    private static DbAccount instance;

    private DbAccount() {

    }

    public static synchronized DbAccount getInstance() {
        if (instance == null) {
            instance = new DbAccount();
        }
        return instance;
    }

    /**
     * 获取登陆信息
     *
     * @param loginName
     * @return
     */
    public Account getAccount(String loginName) {
        try {
            DbManager db = x.getDb(ConfigSetting.daoConfig);
            return db.selector(Account.class).where("login_name", "=", loginName).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有登录用户
     *
     * @return
     */
    public List<Account> getAllAccount() {
        try {
            DbManager db = x.getDb(ConfigSetting.daoConfig);
            return db.selector(Account.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean save(Account account) {
        try {
            DbManager db = x.getDb(ConfigSetting.daoConfig);
            Account dbAcc = db.selector(Account.class).where("login_name", "=", account.loginName).findFirst();
            if (dbAcc != null) {
                account.id = dbAcc.id;
            }
            db.saveOrUpdate(account);
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
