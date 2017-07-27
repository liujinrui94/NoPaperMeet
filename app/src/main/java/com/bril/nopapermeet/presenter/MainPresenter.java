package com.bril.nopapermeet.presenter;


import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.Menu;
import com.bril.nopapermeet.manager.LoginHelper;
import com.bril.nopapermeet.ui.activity.MainActivity;
import com.bril.nopapermeet.ui.fragment.MeetFileFragment;
import com.bril.nopapermeet.ui.fragment.MeetDetailedFragment;
import com.bril.nopapermeet.ui.fragment.MeetInfoFragment;
import com.bril.nopapermeet.ui.fragment.MeetSignInfoFragment;
import com.bril.nopapermeet.ui.fragment.MeetSummaryFragment;
import com.bril.nopapermeet.ui.fragment.MeetsFragment;
import com.bril.nopapermeet.ui.fragment.NoticeFragment;
import com.bril.nopapermeet.ui.fragment.RecordFragment;
import com.bril.nopapermeet.ui.fragment.SearchFragment;
import com.bril.nopapermeet.ui.fragment.UnReadMsgFragment;
import com.bril.nopapermeet.ui.fragment.VoteFragment;

import java.util.ArrayList;
import java.util.List;

import nucleus.presenter.Presenter;

/**
 * 主页
 */
public class MainPresenter extends Presenter<MainActivity> {
    List<Menu> homeMenu;

    /**
     * 首页菜单
     */
    public void openHomeMenu() {
        if (homeMenu == null) {
            homeMenu = new ArrayList<>();
            homeMenu.add(new Menu(getView().getString(R.string.menu_meet), R.drawable.login_title));
            homeMenu.add(new Menu(getView().getString(R.string.menu_msg), R.drawable.login_title));
            homeMenu.add(new Menu(getView().getString(R.string.menu_search), R.drawable.login_title));
            homeMenu.add(new Menu(getView().getString(R.string.menu_exit), R.drawable.login_title));
        }
        getView().repalceMenu(homeMenu);
    }

    /**
     * 打开会议
     *
     * @param meetList
     */
    public void openMeetInfo(MeetList meetList) {
        List<Menu> meetMenu = new ArrayList<>();
        meetMenu.add(new Menu(getView().getString(R.string.menu_info), R.drawable.login_title, meetList));
        meetMenu.add(new Menu(getView().getString(R.string.menu_lssue), R.drawable.login_title, meetList));
        meetMenu.add(new Menu(getView().getString(R.string.menu_call_out), R.drawable.login_title, meetList));
        meetMenu.add(new Menu(getView().getString(R.string.menu_say), R.drawable.login_title, meetList));
        meetMenu.add(new Menu(getView().getString(R.string.menu_vote), R.drawable.login_title, meetList));
        meetMenu.add(new Menu(getView().getString(R.string.menu_notice), R.drawable.login_title, meetList));
        meetMenu.add(new Menu(getView().getString(R.string.menu_record), R.drawable.login_title, meetList));
        meetMenu.add(new Menu(getView().getString(R.string.menu_summary), R.drawable.login_title, meetList));
        meetMenu.add(new Menu(getView().getString(R.string.menu_file), R.drawable.login_title, meetList));
        if (meetList.isHost) {
            meetMenu.add(new Menu(getView().getString(R.string.menu_sign), R.drawable.login_title, meetList));
            meetMenu.add(new Menu(getView().getString(R.string.menu_meet_end), R.drawable.login_title, meetList));
        }
        FragmentManager fm = getView().getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        for (int i = 0; i < meetMenu.size(); i++) {
            Menu menu = meetMenu.get(i);
            Fragment tagFragment = fm.findFragmentByTag(menu.name);
            if (tagFragment != null) {
                tr.remove(tagFragment).remove(tagFragment);
            }
        }
        tr.commitNow();
        getView().repalceMenu(meetMenu);
    }

    /**
     * @param menu
     */
    public void openItemMenu(Menu menu) {
        String name = menu.name;
        if (!name.equals(getView().getResources().getString(R.string.menu_exit)) && !name.equals(getView().getResources().getString(R.string.menu_meet_end))) {
            getView().checkItme(name);
        }
        if (name.equals(getView().getResources().getString(R.string.menu_meet))) {
            getView().selectFragment(new MeetsFragment(), name);
        } else if (name.equals(getView().getResources().getString(R.string.menu_msg))) {
            getView().selectFragment(new UnReadMsgFragment(), name);
        } else if (name.equals(getView().getResources().getString(R.string.menu_search))) {
            openSearch();
        } else if (name.equals(getView().getResources().getString(R.string.menu_exit))) {
            new AlertDialog.Builder(getView()).setTitle(R.string.exit_title)
                    .setMessage(R.string.exit_hint_msg)
                    .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            exitLogin();
                        }
                    }).setNegativeButton(R.string.btn_no, null)
                    .show();
        } else if (name.equals(getView().getResources().getString(R.string.menu_lssue))) {
            getView().selectFragment(MeetInfoFragment.newInstance((MeetList) menu.itemObj), name);
        } else if (name.equals(getView().getResources().getString(R.string.menu_call_out))) {
            getView().goCallOut((MeetList) menu.itemObj);
        } else if (name.equals(getView().getResources().getString(R.string.menu_say))) {
            getView().goApplySay((MeetList) menu.itemObj);
        } else if (name.equals(getView().getResources().getString(R.string.menu_vote))) {
            getView().selectFragment(VoteFragment.newInstance((MeetList) menu.itemObj), name);
        } else if (name.equals(getView().getResources().getString(R.string.menu_notice))) {
            getView().selectFragment(NoticeFragment.newInstance((MeetList) menu.itemObj), name);
        } else if (name.equals(getView().getResources().getString(R.string.menu_record))) {
            getView().selectFragment(RecordFragment.newInstance((MeetList) menu.itemObj), name);
        } else if (name.equals(getView().getResources().getString(R.string.menu_sign))) {
            getView().selectFragment(new MeetSignInfoFragment(), name);
            //TODO
        }else if (name.equals(getView().getResources().getString(R.string.meet_info))) {
            getView().selectFragment(MeetDetailedFragment.newInstance((MeetList) menu.itemObj), name);
            //TODO   详情
        }else if (name.equals(getView().getResources().getString(R.string.menu_summary))) {
            getView().selectFragment(MeetSummaryFragment.newInstance((MeetList) menu.itemObj), name);
            //TODO  纪要
        }else if (name.equals(getView().getResources().getString(R.string.menu_file))) {
            getView().selectFragment(new MeetFileFragment(), name);
            //TODO   归档
        }
        else if (name.equals(getView().getResources().getString(R.string.menu_meet_end))) {
            new AlertDialog.Builder(getView()).setTitle(R.string.end_meet_title)
                    .setMessage(R.string.end_meet_hint)
                    .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            openHomeMenu();
                        }
                    }).setNegativeButton(R.string.btn_no, null)
                    .show();
        }
    }

    public void openSearch() {
        String name = getView().getResources().getString(R.string.menu_search);
        getView().selectFragment(new SearchFragment(), name);
    }


    public void exitLogin() {
        LoginHelper.getInstance(getView()).exitAuccount();
        getView().goLogin();
    }

}
