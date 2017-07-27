package com.bril.nopapermeet.presenter;

import android.util.Log;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.Summary;
import com.bril.nopapermeet.ui.fragment.MeetSummaryFragment;

import java.util.List;

import nucleus.presenter.Presenter;

/**
 * Created by Administrator on 2017/4/8.
 */

public class MeetSummaryPresenter extends Presenter<MeetSummaryFragment>{

    DaoSession dao= App.getsInstance().getDaoSession();

    public List<Summary> isData(){
        List<Summary> summary=dao.getSummaryDao().loadAll();
        return summary;
    }


    public void updateSummary(String info,long meetId) {
        List<Summary> summary = dao.getSummaryDao().loadAll();

        for (int i = 0; i < summary.size(); i++) {
            Summary s=summary.get(i);
            if (s.meetId==meetId){
                s.setContent(info);
                dao.getSummaryDao().update(s);
            }
        }
    }

}
