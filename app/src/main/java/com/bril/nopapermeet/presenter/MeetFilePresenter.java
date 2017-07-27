package com.bril.nopapermeet.presenter;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.MeetFile;
import com.bril.nopapermeet.ui.fragment.MeetFileFragment;

import java.util.ArrayList;
import java.util.List;

import nucleus.presenter.Presenter;

/**
 * Created by Administrator on 2017/4/9.
 */

public class MeetFilePresenter extends Presenter<MeetFileFragment>{

    DaoSession daoSession;
    public void updateList(){
        daoSession= App.getsInstance().getDaoSession();
        List<MeetFile> data=daoSession.getMeetFileDao().loadAll();
        if (data!=null){
            getView().replaceMeetFile(data);
        }
    }


    public void addToDao(MeetFile meet) {
        daoSession=App.getsInstance().getDaoSession();
        daoSession.getMeetFileDao().save(meet);
        updateList();
    }

    public void deleteToDao(MeetFile item) {
        daoSession=App.getsInstance().getDaoSession();
        daoSession.delete(item);
        updateList();
    }
}
