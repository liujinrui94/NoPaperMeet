package com.bril.nopapermeet.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.MeetSign;
import com.bril.nopapermeet.ui.fragment.MeetSignInfoFragment;

import java.util.ArrayList;
import java.util.List;

import nucleus.presenter.Presenter;

/**
 * Created by LiuJinrui on 2017/3/30.
 */

public class MeetSignInfoPresenter extends Presenter<MeetSignInfoFragment> {

    private DaoSession daoSession;
    public void netMeetSign(boolean boo) {
//        String[] appname = {"公文流转", "值班管理", "车辆申请", "办公用品申请", "会议管理"};
//        List<MeetSign> meet_list = new ArrayList<MeetSign>();
//        for (int i = 0; i < appname.length; i++) {
//            MeetSign mMeetSign = new MeetSign();
//            mMeetSign.setName(appname[i]);
//            meet_list.add(mMeetSign);
//        }
        List<MeetSign> meetSignFalse=new ArrayList<>();//未签到列表
//        meetSignFalse.clear();
        List<MeetSign> meetSignTrue=new ArrayList<>();//签到列表
//        meetSignTrue.clear();
        daoSession=App.getsInstance().getDaoSession();
        List<MeetSign> meet_list=daoSession.getMeetSignDao().loadAll();
        if (meet_list!=null) {

            for (int i = 0; i < meet_list.size(); i++) {
               boolean tag= meet_list.get(i).getIsSigned();
                if (tag){

                    meetSignTrue.add(meet_list.get(i));
                }else {

                    meetSignFalse.add(meet_list.get(i));
                }
            }
            if (boo){
                getView().replaceAll(meetSignTrue);
            } else {
                getView().replaceAll(meetSignFalse);
            }
        }
    }


    public void insertSign(){
        daoSession= App.getsInstance().getDaoSession();
        List<MeetSign> meet_list=daoSession.getMeetSignDao().loadAll();
        if (meet_list!=null){
            for (int i = 0; i < meet_list.size(); i++) {
                daoSession.delete(meet_list.get(i));
            }
        }

        for (int i = 0; i < 10; i++) {
            MeetSign meetSign=new MeetSign();
            meetSign.setName("小明("+i+")");
            meetSign.setNumber(i+"");
            meetSign.setDepartment("技术部门");
            meetSign.setDuty("java");
            meetSign.setSigntime("2017-4-5");
            if (i%2==0) {
                meetSign.setIsSigned(true);//false代表签到----偶数
            }else {
                meetSign.setIsSigned(false);
            }
            daoSession.getMeetSignDao().insert(meetSign);
        }
    }



}
