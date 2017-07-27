package com.bril.nopapermeet.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.presenter.MeetInfoPresenter;
import com.bril.nopapermeet.ui.activity.MainActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import nucleus.factory.RequiresPresenter;
import nucleus.presenter.Presenter;

/**
 * Created by LiuJinrui on 2017/3/30.
 */

public class MeetBaseFragment<P extends Presenter> extends BaseFragment<P> {
    private LinearLayout llEmpty;
    private TextView tvEmpty;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackVisble(view);
    }

    public void clickBack() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getPresenter().openHomeMenu();
    }

    public void initVisbleEmpty(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.ll_empty);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("onHiddenChanged", getClass().getName() + "===" + hidden);
    }

    public void setEmpty(boolean isEmpty) {
        setEmpty(isEmpty, null);
    }

    public void setEmpty(boolean isEmpty, String hint) {
        if (isEmpty) {
            llEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText(hint);
        } else {
            llEmpty.setVisibility(View.GONE);
        }
    }
}
