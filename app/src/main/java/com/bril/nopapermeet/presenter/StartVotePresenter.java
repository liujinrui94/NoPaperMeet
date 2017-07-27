package com.bril.nopapermeet.presenter;

import android.app.Activity;
import android.util.Log;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.Vote;
import com.bril.nopapermeet.ui.activity.StartVoteActivity;
import com.bril.nopapermeet.utils.ToastUtils;

import nucleus.presenter.Presenter;

/**
 * Created by LiuJinrui on 2017/3/31.
 */

public class StartVotePresenter extends Presenter<StartVoteActivity> {
    DaoSession daoSession = App.getsInstance().getDaoSession();

    public void netCommitVote(Vote vote) {
        daoSession.getVoteDao().save(vote);
        for (int i = 0; i < vote.options.size(); i++) {
            vote.options.get(i).voteId = vote.getId();
        }
        daoSession.getOptionDao().saveInTx(vote.options);
        ToastUtils.shortShow(getView(), "发起成功！");
        getView().setResult(Activity.RESULT_OK);
        getView().finish();
    }

}
