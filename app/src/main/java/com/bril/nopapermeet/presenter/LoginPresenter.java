package com.bril.nopapermeet.presenter;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.manager.LoginHelper;
import com.bril.nopapermeet.ui.activity.LoginActivity;
import com.bril.nopapermeet.utils.ToastUtils;

import java.util.List;
import java.util.Random;

import nucleus.factory.RequiresPresenter;
import nucleus.presenter.Presenter;

/**
 * 登录
 */
public class LoginPresenter extends Presenter<LoginActivity> {
    DaoSession daoSession = App.getsInstance().getDaoSession();

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
    }

    public void login() {
        getView().showLoading("提交中...");
        List<Account> accounts = daoSession.getAccountDao().loadAll();
        Random random = new Random();
        int randomInt = random.nextInt(accounts.size());
        Log.e("random", "---" + randomInt);
        Account account = accounts.get(randomInt);
        loginSuccess(account);
    }

    private void loginSuccess(Account account) {
        LoginHelper.getInstance(getView()).saveCurrentAuccount(account);
        getView().closeLoading();
        getView().goMain();
    }

    private void loginFail() {
        getView().closeLoading();
        ToastUtils.shortShow(getView(), "验证失败请稍后再试！");
    }
}
