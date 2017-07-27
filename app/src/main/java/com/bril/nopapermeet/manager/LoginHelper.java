package com.bril.nopapermeet.manager;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.entity.AccountDao;
import com.bril.nopapermeet.entity.DaoSession;

/**
 * 登录
 */

public class LoginHelper {
    private static LoginHelper instance;
    private static Context mContext;
    private Account currentAccount;
    DaoSession daoSession = App.getsInstance().getDaoSession();

    private LoginHelper() {
    }

    public synchronized static LoginHelper getInstance(Context context) {
        if (instance == null) {
            instance = new LoginHelper();
        }
        mContext = context;
        return instance;
    }

    public Account getCurrentAccount() {
        if (currentAccount != null) {
            return currentAccount;
        }
        getLoginName();
        currentAccount = daoSession.getAccountDao().queryBuilder().where(AccountDao.Properties.LoginName.eq(getLoginName())).build().unique();
        return currentAccount;
    }

    public void saveCurrentAuccount(Account account) {
        currentAccount = null;
        saveLoginName(account.loginName);
        saveLastLoginName(account.loginName);
    }

    public void saveLoginName(String loginName) {
        saveShared("loginName", loginName);
    }

    public String getLoginName() {
        return getShared("loginName");
    }

    public void saveLastLoginName(String loginName) {
        saveShared("loginLast", loginName);
    }

    public String getLastLoginName() {
        return getShared("loginLast");
    }

    public void saveLoginPwd(String loginPwd) {
        saveShared("pwd", loginPwd);
    }

    public String getLoginPwd() {
        return getShared("pwd");
    }

    private void saveShared(String key, String vlue) {
        SharedPreferences sp = mContext.getSharedPreferences("loginFile", Context.MODE_PRIVATE);
        sp.edit().putString(key, vlue).commit();
    }

    private String getShared(String key) {
        SharedPreferences sp = mContext.getSharedPreferences("loginFile", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public void exitAuccount() {
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        currentAccount = null;
        saveLoginName("");
    }
}
