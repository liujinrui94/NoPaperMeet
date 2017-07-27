package com.bril.nopapermeet.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.entity.Option;
import com.bril.nopapermeet.entity.Vote;
import com.bril.nopapermeet.presenter.MyVotePresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by LiuJinrui on 2017/3/31.
 */
@RequiresPresenter(MyVotePresenter.class)
@ContentView(R.layout.my_vote_activity)
public class MyVoteActivity extends BaseActivity<MyVotePresenter> implements View.OnClickListener {

    @ViewInject(R.id.tv_checkbox_most)
    private TextView tv_checkbox_most;

    @ViewInject(R.id.my_vote_activity_rg)
    private RadioGroup rg;

    @ViewInject(R.id.my_vote_activity_tv)
    private TextView my_vote_activity_tv;

    private Vote mVote;

    @ViewInject(R.id.ll_activity_vote_option)
    private LinearLayout ll_activity_vote_option;
    private List<Option> selectOption = new ArrayList<>();

    private List<CheckBox> mCheckBoxs = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVote = (Vote) getIntent().getSerializableExtra("vote");
        my_vote_activity_tv.setText(mVote.theme);
        if (mVote.isSigle) {
            rg.setVisibility(View.VISIBLE);
            for (int i = 0; i < mVote.options.size(); i++) {
                addRadioButton(rg, mVote.options.get(i));
            }
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                    Option option = (Option) radioButton.getTag();
                    selectOption.clear();;
                    selectOption.add(option);
                }
            });
            ((RadioButton) rg.getChildAt(0)).setChecked(true);
        } else {
            tv_checkbox_most.setText("最多可选" + mVote.mostoption + "项");
            for (int i = 0; i < mVote.options.size(); i++) {
                initCheckBox(ll_activity_vote_option, mVote.options.get(i));
            }
        }
    }

    public void addRadioButton(RadioGroup group, Option option) {
        RadioButton radioButton = new RadioButton(this);
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        radioButton.setLayoutParams(layoutParams);
        radioButton.setText(option.name);
        radioButton.setTag(option);
        radioButton.setTextSize(16);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setPadding(10, 10, 10, 10);
        group.addView(radioButton);//将单选按钮添加到RadioGroup中
    }


    private void initCheckBox(LinearLayout linearLayout, Option option) {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(option.name);
        checkBox.setTextSize(16.0f);
        checkBox.setOnCheckedChangeListener(listener);
        checkBox.setTag(option);
        mCheckBoxs.add(checkBox);
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        checkBoxParams.gravity = Gravity.CENTER_VERTICAL;
        checkBoxParams.weight = 1.0f;
        linearLayout.addView(checkBox, checkBoxParams);
    }


    private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Option option = (Option) buttonView.getTag();
            if (isChecked) {
                selectOption.add(option);
            } else {
                selectOption.remove(option);
            }
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_vote_activity:
                getPresenter().netSendVote(selectOption);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
