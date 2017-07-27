package com.bril.nopapermeet.presenter;


import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.UnReadMsg;
import com.bril.nopapermeet.ui.fragment.UnReadMsgFragment;

import java.util.ArrayList;
import java.util.List;

import nucleus.presenter.Presenter;

/**
 * 未读消息
 */
public class UnReadMsgPresenter extends Presenter<UnReadMsgFragment> {
    /**
     * 获取未读消息
     */

    private DaoSession daoSession;
    public void netUnReadMsg() {
        //TODO 假数据;
        daoSession=App.getsInstance().getDaoSession();
        List<UnReadMsg> list =daoSession.getUnReadMsgDao().loadAll();
        getView().repalceMsg(list);
    }

}
