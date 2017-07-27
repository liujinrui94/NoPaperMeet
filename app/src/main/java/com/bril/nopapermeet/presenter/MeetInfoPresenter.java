package com.bril.nopapermeet.presenter;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.Lssue;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.ui.fragment.MeetInfoFragment;
import com.bril.nopapermeet.utils.ToastUtils;

import java.util.List;

import nucleus.presenter.Presenter;

/**
 * Created by LiuJinrui on 2017/3/30.
 */

public class MeetInfoPresenter extends Presenter<MeetInfoFragment> {
    DaoSession daoSession = App.getsInstance().getDaoSession();

    /**
     * 获取议题列表
     */
    public void netLssue() {
        MeetList meetList = getView().getMeetList();
        meetList.lssues = null;
        List<Lssue> lssues = meetList.getLssues();
        getView().repalceLssue(lssues);
    }

    public void closeLess(Lssue lssue) {
        lssue.state = 3;
        daoSession.getLssueDao().update(lssue);
        netLssue();
        ToastUtils.shortShow(getView().getContext(), "关闭成功！");
    }

    public void openLess(Lssue lssue) {
        lssue.state = 1;
        daoSession.getLssueDao().update(lssue);
        netLssue();
        ToastUtils.shortShow(getView().getContext(), "启动成功！");
    }
}
