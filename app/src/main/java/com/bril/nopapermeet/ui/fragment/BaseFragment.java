package com.bril.nopapermeet.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.manager.LoginHelper;

import org.xutils.x;

import nucleus.presenter.Presenter;
import nucleus.view.NucleusSupportFragment;

public abstract class BaseFragment<P extends Presenter> extends NucleusSupportFragment<P> {

    private boolean injected = false;
    public Context context;
    private TextView tvTitle;
    private ImageView leftBtn;
    private ImageView rightBtn;
    public Account account;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        account = LoginHelper.getInstance(getActivity()).getCurrentAccount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        injected = true;
        getPresenter().takeView(this);
        return x.view().inject(this, inflater, container);
    }

    public void setTitle(View view, String name) {
        if (tvTitle == null)
            tvTitle = (TextView) view.findViewById(R.id.tv_title_name);
        tvTitle.setText(name);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().takeView(this);
    }

    public void setBackVisble(View view) {
        if (leftBtn == null)
            leftBtn = (ImageView) view.findViewById(R.id.iv_title_left);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setImageResource(R.drawable.icon_back);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBack();
            }
        });
    }

    public void setTitleRight(View view, int iconId) {
        if (rightBtn == null)
            rightBtn = (ImageView) view.findViewById(R.id.iv_title_right);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setImageResource(iconId);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTitleRight();
            }
        });
    }

    public void clickBack() {

    }

    public void clickTitleRight() {

    }
}
