package com.bril.nopapermeet.presenter;


import android.os.Handler;
import android.os.Message;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.manager.LoginHelper;
import com.bril.nopapermeet.ui.activity.SplashActivity;

import nucleus.presenter.Presenter;

/**
 * 启动页
 */
public class SplashPresenter extends Presenter<SplashActivity> {
    public final int WHATE_OPEN_LOGIN = 10;
    public final int WHATE_OPEN_MAIN = 11;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHATE_OPEN_LOGIN:
                    getView().goLogin();
                    break;
                case WHATE_OPEN_MAIN:
                    getView().goMain();
                    break;
            }

        }
    };

    public void checkLogin() {
        App.getsInstance().initDate(new App.InitLinsner() {
            @Override
            public void start() {
                getView().showLoading("初始化数据中...");
            }

            @Override
            public void onSuccess() {
                getView().closeLoading();
                Account account = LoginHelper.getInstance(getView()).getCurrentAccount();
                if (account == null) {
                    mHandler.sendEmptyMessageDelayed(WHATE_OPEN_LOGIN, 1000);
                } else {
                    mHandler.sendEmptyMessageDelayed(WHATE_OPEN_MAIN, 300);
                }
            }
        });
    }

}
