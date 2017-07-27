package com.bril.nopapermeet.ui.activity;

import android.content.Context;
import android.os.Bundle;

import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.manager.LoginHelper;

import org.xutils.x;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusAppCompatActivity;

public abstract class BaseActivity<P extends Presenter> extends NucleusAppCompatActivity<P> {
    public Context context;
    SweetAlertDialog sweetAlertDialog;
    public Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        context = this;
        account = LoginHelper.getInstance(context).getCurrentAccount();
        setFinishOnTouchOutside(false);
        getPresenter().takeView(this);
    }

    public void showLoading(String msg) {
        if (sweetAlertDialog != null) {
            sweetAlertDialog.cancel();
        } else {
            sweetAlertDialog = new SweetAlertDialog(context);
            sweetAlertDialog.setCancelable(false);
        }
        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText(msg).show();
    }

    public void closeLoading() {
        if (sweetAlertDialog != null) {
            sweetAlertDialog.cancel();
        }
    }

    public void showSuccess(String title, String msg, SweetAlertDialog.OnSweetClickListener confirmListener) {
        if (sweetAlertDialog != null) {
            sweetAlertDialog.setTitleText(title)
                    .setContentText(msg)
                    .setConfirmText("确认")
                    .showCancelButton(false)
                    .setCancelClickListener(null)
                    .setConfirmClickListener(confirmListener).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        }
    }
}
