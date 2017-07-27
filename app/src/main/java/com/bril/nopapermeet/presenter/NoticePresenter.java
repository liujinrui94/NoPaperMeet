package com.bril.nopapermeet.presenter;


import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.Notice;
import com.bril.nopapermeet.ui.fragment.NoticeFragment;

import java.util.List;

import nucleus.presenter.Presenter;

/**
 * 通知通告
 */
public class NoticePresenter extends Presenter<NoticeFragment> {
    DaoSession dao = App.getsInstance().getDaoSession();
    /**
     * 获取未读消息
     */
    public void netNotice(MeetList meetList) {
        List<Notice> list = meetList.getNotices();
        if (list != null) {
            getView().repalceMsg(list);
        }
    }
    public void saveNotice(Notice mNotice, MeetList meetList) {
        List<Notice> list = meetList.getNotices();
        list.add(mNotice);
        dao.getNoticeDao().saveInTx(list);
        getView().repalceMsg(list);
    }
}
