package com.bril.nopapermeet.presenter;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.ui.activity.MainActivity;
import com.bril.nopapermeet.ui.fragment.SearchSingleFragment;
import java.util.List;
import nucleus.presenter.Presenter;

/**
 * Created by Administrator on 2017/4/8.
 */

public class SearchSinglePresenter extends Presenter<SearchSingleFragment>{

    DaoSession daoSession= App.getsInstance().getDaoSession();

    public List<MeetList> searchData() {

        List<MeetList> meet_list = daoSession.getMeetListDao().loadAll();
        return meet_list;
    }

    public void particularMeet(MeetList meetList) {
        MainActivity mainActivity = (MainActivity) getView().getActivity();
        mainActivity.getPresenter().openMeetInfo(meetList);
    }
}
