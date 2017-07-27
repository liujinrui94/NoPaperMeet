package com.bril.nopapermeet.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.presenter.SplashPresenter;

import org.xutils.view.annotation.ContentView;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(SplashPresenter.class)
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity<SplashPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().checkLogin();
    }

    public void goLogin() {
        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }

    public void goMain() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }
}
