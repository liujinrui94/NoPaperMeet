package com.bril.nopapermeet.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.R;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.RecordList;
import com.bril.nopapermeet.entity.Summary;
import com.bril.nopapermeet.presenter.MeetSummaryPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by Administrator on 2017/4/8.
 * 会议纪要
 */
@RequiresPresenter(MeetSummaryPresenter.class)
@ContentView(R.layout.fragment_meet_summary)
public class MeetSummaryFragment extends MeetBaseFragment<MeetSummaryPresenter>{

    @ViewInject(R.id.et_fragment_summary_content)
    private EditText et_content;
    @ViewInject(R.id.fragment_summary_save)
    private TextView tv_save;
    @ViewInject(R.id.fragment_summary_cancel)
    private TextView tv_cancel;

    private String info;
    private List<Summary> summary;
    //
    public MeetList meet;
    public long meetId;

    public static MeetSummaryFragment newInstance(MeetList meetList){
        Bundle bundle=new Bundle();
        bundle.putSerializable("meetList",meetList);
        MeetSummaryFragment fragment=new MeetSummaryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        meet= (MeetList) getArguments().getSerializable("meetList");
        meetId=meet.id;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view, getResources().getString(R.string.menu_summary));
        //
        et_content.setEnabled(false);
        summary=getPresenter().isData();//会议纪要的数组
        //首先获取会议的id，再与会议纪要里面的数据进行对比
        if (summary!=null){
            for (int i = 0; i < summary.size(); i++) {
                long sMeetId=summary.get(i).getMeetId();
                if (sMeetId==meetId&&!summary.get(i).getContent().equals("")){
                    et_content.setText(summary.get(i).getContent());
                }
            }
        }
        initEvent();
    }

    private void initEvent() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    et_content.setEnabled(true);
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行数据保存
                info=et_content.getText().toString().trim();
                getPresenter().updateSummary(info,meetId);
                et_content.setEnabled(false);
            }
        });
    }
}
