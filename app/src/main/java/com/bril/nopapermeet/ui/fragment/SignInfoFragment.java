package com.bril.nopapermeet.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.presenter.MeetSignInfoPresenter;
import com.bril.nopapermeet.presenter.SignInfoPresenter;

import org.xutils.view.annotation.ContentView;

import nucleus.factory.RequiresPresenter;

/**
 * Created by LiuJinrui on 2017/3/31.
 */
@RequiresPresenter(SignInfoPresenter.class)
@ContentView(R.layout.sign_info_fragment)
public class SignInfoFragment extends BaseFragment<SignInfoPresenter>{

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
