package com.bril.nopapermeet.presenter;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.ui.fragment.MeetDetailedFragment;

import java.util.List;

import nucleus.presenter.Presenter;

/**
 * Created by Administrator on 2017/4/7.
 */

public class MeetDetailedPresenter extends Presenter<MeetDetailedFragment>{

    DaoSession daoSession = App.getsInstance().getDaoSession();
    List<Account> account = daoSession.getAccountDao().loadAll();
    public String getHostName(MeetList meet) {

        for (int i = 0; i < account.size(); i++) {
            if (account.get(i).id == meet.hostId) {
                String hostName=account.get(i).nickName;
                return hostName;
            }
        }
        return null;
    }


    public String getPeople(MeetList meet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < meet.getMeetPersons().size(); i++) {

            if (i == (meet.getMeetPersons().size() - 1)) {
//                if (meet.getMeetPersons().get(i).getAccountId()==account.get(i).id){
                    sb.append(account.get(i).nickName);
//                }
            } else {
//                if (meet.getMeetPersons().get(i).getAccountId()==account.get(i).id){
                    sb.append(account.get(i).nickName+"、");
//                }
            }
        }
        return sb.toString();
    }

}
