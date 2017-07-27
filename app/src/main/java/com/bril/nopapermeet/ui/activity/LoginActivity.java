package com.bril.nopapermeet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.presenter.LoginPresenter;
import com.bril.nopapermeet.ui.fragment.LoginAnimFragment;
import com.bril.nopapermeet.ui.view.PaintView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(LoginPresenter.class)
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity<LoginPresenter> {
    @ViewInject(R.id.pv_sign)
    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_to_anim, new LoginAnimFragment()).commit();
    }

    @Event(R.id.btn_login)
    private void clickLogin(View view) {
        getPresenter().login();
    }

    @Event(R.id.btn_clear)
    private void clickClear(View view) {
        paintView.removeAllPaint();
    }

    public void goMain() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }
}

