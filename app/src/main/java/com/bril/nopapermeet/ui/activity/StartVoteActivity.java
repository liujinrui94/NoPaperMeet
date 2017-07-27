package com.bril.nopapermeet.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.Option;
import com.bril.nopapermeet.entity.RecordList;
import com.bril.nopapermeet.entity.Vote;
import com.bril.nopapermeet.presenter.StartVotePresenter;
import com.bril.nopapermeet.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import nucleus.factory.RequiresPresenter;

/**
 * Created by LiuJinrui on 2017/3/31.
 */

@RequiresPresenter(StartVotePresenter.class)
@ContentView(R.layout.activity_start_vote)
public class StartVoteActivity extends BaseActivity<StartVotePresenter> implements View.OnClickListener {

    @ViewInject(R.id.start_vote_rg)
    private RadioGroup rg;
    //最多选几项
    @ViewInject(R.id.ll_start_vote_count)
    private LinearLayout ll_start_vote_count;
    //添加的选项
    @ViewInject(R.id.ll_add_vote_options)
    private LinearLayout ll_add_vote_options;

    @ViewInject(R.id.et_start_count)
    private EditText et_start_count;

    @ViewInject(R.id.et_start_vote_theme)
    private EditText et_start_vote_theme;

    @ViewInject(R.id.tv_add_options)
    private TextView tv_add_options;
    //确定
    @ViewInject(R.id.tv_start_vote_ok)
    private TextView tv_start_vote_ok;

    @ViewInject(R.id.start_vote_radio)
    private RadioButton start_vote_radio;


    private ArrayList<View> editViews = new ArrayList<View>();

    private Vote mVote = new Vote();
    private MeetList meetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meetList = (MeetList) getIntent().getSerializableExtra("meetInfo");
        start_vote_radio.setChecked(true);
        mVote.isSigle = true;
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.start_vote_radio:
                        ll_start_vote_count.setVisibility(View.INVISIBLE);
                        mVote.isSigle = true;
                        break;
                    case R.id.start_vote_multiple:
                        ll_start_vote_count.setVisibility(View.VISIBLE);
                        mVote.isSigle = false;
                        break;
                }
            }
        });
        addView();
        addView();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_add_options:
                addView();
                break;
            case R.id.tv_start_vote_ok:
                if (!mVote.isSigle && "".equals(et_start_count.getText().toString())) {
                    ToastUtils.longShow(context, "请输入最多选几项");
                    break;
                }
                if ("".equals(et_start_vote_theme.getText().toString())) {
                    ToastUtils.longShow(context, "请输入主题");
                    break;
                }
                Set<String> strings = new HashSet<>();
                for (int i = 0; i < editViews.size(); i++) {
                    EditText text = (EditText) editViews.get(i).findViewById(R.id.et_add_option);
                    String str = text.getText().toString().trim();
                    if (!TextUtils.isEmpty(str)) {
                        strings.add(str);
                    }
                }
                if (strings.size() < 2) {
                    ToastUtils.longShow(context, "最少两个选项,或两个选项不能相同");
                    break;
                }
                Iterator<String> iterator = strings.iterator();
                mVote.options = new ArrayList<>();
                while (iterator.hasNext()) {
                    mVote.options.add(new Option(iterator.next()));
                }
                if (!"".equals(et_start_count.getText().toString())) {
                    mVote.mostoption = Integer.parseInt(et_start_count.getText().toString());
                }
                mVote.theme = et_start_vote_theme.getText().toString();
                mVote.meetId = meetList.id;
                getPresenter().netCommitVote(mVote);
                break;
        }

    }


    private void addView() {
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.start_vote_edittext, null);
        LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ll_add_vote_options.addView(view, params);
        editViews.add(view);
    }

    @Event(R.id.tv_activity_start_back)
    private void clickNo(View view) {
        finish();
    }

}
