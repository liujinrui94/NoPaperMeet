package com.bril.nopapermeet.presenter;

import android.app.Activity;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.Option;
import com.bril.nopapermeet.entity.Ticket;
import com.bril.nopapermeet.manager.LoginHelper;
import com.bril.nopapermeet.ui.activity.MyVoteActivity;
import com.bril.nopapermeet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import nucleus.presenter.Presenter;

/**
 *
 */

public class MyVotePresenter extends Presenter<MyVoteActivity> {
    public DaoSession daoSession = App.getsInstance().getDaoSession();

    public void netSendVote(List<Option> options) {
        Account account = LoginHelper.getInstance(getView()).getCurrentAccount();
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            Ticket ticket = new Ticket();
            ticket.accoutId = account.id;
            ticket.optionId = option.id;
            tickets.add(ticket);
        }
        daoSession.getTicketDao().saveInTx(tickets);
        ToastUtils.shortShow(getView(), "投票成功！");
        getView().setResult(Activity.RESULT_OK);
        getView().finish();
    }
}
