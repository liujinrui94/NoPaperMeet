package com.bril.nopapermeet.presenter;


import android.util.Log;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.MeetListDao;
import com.bril.nopapermeet.entity.MeetPerson;
import com.bril.nopapermeet.entity.MeetPersonDao;
import com.bril.nopapermeet.manager.LoginHelper;
import com.bril.nopapermeet.ui.activity.MainActivity;
import com.bril.nopapermeet.ui.fragment.MeetsFragment;

import java.util.ArrayList;
import java.util.List;

import nucleus.presenter.Presenter;

/**
 * 会议
 */
public class MeetsPresenter extends Presenter<MeetsFragment> {
    DaoSession daoSession = App.getsInstance().getDaoSession();

    public void netMeetList() {
        Account account = LoginHelper.getInstance(getView().getActivity()).getCurrentAccount();
        List<MeetPerson> list = daoSession.getMeetPersonDao().queryBuilder().where(MeetPersonDao.Properties.AccountId.eq(account.id)).list();
        List<MeetList> meet_list = new ArrayList<>();
        Log.e("----", account.id + "-----" + list.size());
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                MeetPerson meetPerson = list.get(i);
                MeetList meetList = daoSession.getMeetListDao().queryBuilder().where(MeetListDao.Properties.Id.eq(meetPerson.meetId)).unique();
                if (meetList.hostId == account.id) {
                    meetList.isHost = true;
                } else {
                    meetList.isHost = false;
                }
                meet_list.add(meetList);
            }
        }
        Log.e("----", "-----" + list.size());
        if (meet_list!=null) {
            getView().flashMeetList(meet_list);
        }
    }

    public String getHostName(MeetList meet) {
        DaoSession daoSession = App.getsInstance().getDaoSession();
        List<Account> account = daoSession.getAccountDao().loadAll();
        for (int i = 0; i < account.size(); i++) {
            if (account.get(i).id == meet.hostId) {
                String hostName=account.get(i).nickName;
                return hostName;
            }
        }
        return null;
    }


    public void netCloseMeet(MeetList meetList) {
        meetList.state = 2;
        daoSession.update(meetList);
        netMeetList();
    }

    public void netOpenMeet(MeetList meetList) {
        meetList.state = 1;
        daoSession.update(meetList);
        MainActivity mainActivity = (MainActivity) getView().getActivity();
        mainActivity.getPresenter().openMeetInfo(meetList);
        netMeetList();
    }

    public void netJoinMeet(MeetList meetList) {
        MainActivity mainActivity = (MainActivity) getView().getActivity();
        mainActivity.getPresenter().openMeetInfo(meetList);
    }
}
