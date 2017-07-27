package com.bril.nopapermeet.presenter;


import com.bril.nopapermeet.R;
import com.bril.nopapermeet.entity.CallService;
import com.bril.nopapermeet.ui.activity.CallOutActivity;

import java.util.ArrayList;

import nucleus.presenter.Presenter;

/**
 * 呼叫服务
 */
public class CallOutPresenter extends Presenter<CallOutActivity> {
    public void initService() {
        ArrayList<CallService> list = new ArrayList<>();
        list.add(new CallService(getView().getString(R.string.call_water), R.drawable.cs));
        list.add(new CallService(getView().getString(R.string.call_mike), R.drawable.mkf));
        list.add(new CallService(getView().getString(R.string.call_pen), R.drawable.zb));
        list.add(new CallService(getView().getString(R.string.call_flower), R.drawable.xh));
        list.add(new CallService(getView().getString(R.string.call_person), R.drawable.fw));
        getView().repalceCall(list);
    }
}
