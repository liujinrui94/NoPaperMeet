package com.bril.nopapermeet.presenter;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.entity.AccountDao;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.MeetPerson;
import com.bril.nopapermeet.entity.Option;
import com.bril.nopapermeet.entity.Ticket;
import com.bril.nopapermeet.entity.TicketDao;
import com.bril.nopapermeet.entity.Vote;
import com.bril.nopapermeet.entity.VotePerson;
import com.bril.nopapermeet.manager.LoginHelper;
import com.bril.nopapermeet.ui.fragment.VoteFragment;

import java.util.ArrayList;
import java.util.List;

import nucleus.presenter.Presenter;

/**
 * Created by Administrator on 2017/3/31.
 */

public class VotePresenter extends Presenter<VoteFragment> {
    DaoSession daoSession = App.getsInstance().getDaoSession();

    public void netVotes(MeetList meetList) {
        List<Vote> list = meetList.getVotes();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).getOptions();
        }
        getView().replace(list);
        if (list.size() == 0) {
            getView().setEmpty(true, "暂无投票数据");
        } else {
            getView().setEmpty(false);
        }
    }

    public void checkVote(Vote vote) {
        Account account = LoginHelper.getInstance(getView().getActivity()).getCurrentAccount();
        TicketDao ticketDao = daoSession.getTicketDao();
        List<Option> options = vote.getOptions();
        boolean needVote = true;
        for (int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            List<Ticket> ticketList = ticketDao.queryBuilder().where(TicketDao.Properties.AccoutId.eq(account.id), TicketDao.Properties.OptionId.eq(option.id)).list();
            if (ticketList != null && ticketList.size() > 0) {
                needVote = false;
                break;
            }
        }
        if (needVote) {
            getView().goToMyVote(vote);
        } else {
            getView().setDetail();
        }
    }

    public void netMeetPerson(List<MeetPerson> meetPersons, Vote vote) {
        if (meetPersons != null) {
            ArrayList<VotePerson> list = new ArrayList<>();
            List<Option> options = vote.getOptions();
            for (int i = 0; i < meetPersons.size(); i++) {
                VotePerson votePerson = new VotePerson();
                MeetPerson meetPerson = meetPersons.get(i);
                Account account = daoSession.getAccountDao().queryBuilder().where(AccountDao.Properties.Id.eq(meetPerson.accountId)).unique();
                votePerson.name = account.nickName;
                votePerson.options = new ArrayList<>();
                for (int j = 0; j < options.size(); j++) {
                    Option option = options.get(j);
                    Ticket tickets = daoSession.getTicketDao().queryBuilder().where(TicketDao.Properties.AccoutId.eq(meetPerson.accountId), TicketDao.Properties.OptionId.eq(option.id)).unique();
                    if (tickets != null) {
                        votePerson.options.add(option.name);
                    }
                }
                list.add(votePerson);
            }
            getView().replacePerson(list);
        }
    }
}
