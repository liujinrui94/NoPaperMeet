package com.bril.nopapermeet.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.R;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.RecordList;
import com.bril.nopapermeet.ui.fragment.RecordFragment;
import com.bril.nopapermeet.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nucleus.presenter.Presenter;

/**
 * Created by Administrator on 2017/3/30.
 */

public class RecordPresenter extends Presenter<RecordFragment> {
    DaoSession daoSession = App.getsInstance().getDaoSession();

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);

    }

    public void GetRecordData() {
        MeetList meetList = getView().getMeetList();
        meetList.recordLists = null;
        List<RecordList> arr = meetList.getRecordLists();
        getView().repalceList(arr);
        if (arr.size() == 0) {
            getView().setEmpty(true, "请添加记录列表");
        } else {
            getView().setEmpty(false);
        }
    }

    public void setRecordData(RecordList mRecordList) {
        daoSession.getRecordListDao().save(mRecordList);
        GetRecordData();
    }

    public void updateRecordData(RecordList mRecordList) {
        daoSession.getRecordListDao().update(mRecordList);
        GetRecordData();
        ToastUtils.longShow(getView().getContext(), "保存成功");
    }


    public void removedata(RecordList mRecordList) {
        daoSession.getRecordListDao().delete(mRecordList);
        GetRecordData();
    }

    //获取系统时间
    private String getSystemTime() {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date data = new Date(System.currentTimeMillis());
        String str = sdFormat.format(data);
        return str;
    }

}
