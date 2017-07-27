package com.bril.nopapermeet.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.BaseAdapterHelper;
import com.bril.nopapermeet.adapter.QuickAdapter;
import com.bril.nopapermeet.entity.MeetSign;
import com.bril.nopapermeet.presenter.MeetSignInfoPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by LiuJinrui on 2017/3/30.
 * 签到统计
 */
@RequiresPresenter(MeetSignInfoPresenter.class)
@ContentView(R.layout.meet_sign_info_fragment)
public class MeetSignInfoFragment extends BaseFragment<MeetSignInfoPresenter> {


    @ViewInject(R.id.meet_sign_info_lv)
    private ListView lv;
    @ViewInject(R.id.rg_sign_info)
    private RadioGroup rg_sign;
    private QuickAdapter<MeetSign> quickAdapter;
    //是否是第一次加载数据
    private boolean isFirst = true;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view, getResources().getString(R.string.meet_sign));
        getPresenter().insertSign();
        quickAdapter = new QuickAdapter<MeetSign>(context, R.layout.meet_sign_info_item) {

            @Override
            protected void convert(BaseAdapterHelper helper, MeetSign item) {
                if (helper.getPosition() == 0) {
                    helper.setBackgroundRes(R.id.ll_item, R.color.background_bule);
                    helper.setText(R.id.tv_sign_number, getResources().getString(R.string.sign_number));
                    helper.setText(R.id.tv_sign_name, getResources().getString(R.string.sign_name));
                    helper.setText(R.id.tv_sign_department, getResources().getString(R.string.sign_department));
                    helper.setText(R.id.tv_sign_duty, getResources().getString(R.string.sign_duty));
                    helper.setText(R.id.tv_sign_signtime, getResources().getString(R.string.sign_signtime));
                } else {
                    helper.setText(R.id.tv_sign_number, item.getNumber());
                    helper.setText(R.id.tv_sign_name, item.getName());
                    helper.setText(R.id.tv_sign_department, item.getDepartment());
                    helper.setText(R.id.tv_sign_duty, item.getDuty());
                    helper.setText(R.id.tv_sign_signtime, item.getSigntime());
                }
            }
        };
        lv.setAdapter(quickAdapter);

        getPresenter().netMeetSign(true);
        initEvent();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        isFirst=true;
        Log.e("TAG", "----------setUserVisibleHint----------");
        if (isVisibleToUser && isFirst) {
            Log.e("TAG", "----------sint----------");
            getPresenter().insertSign();
            isFirst = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

//    @Override
//    public void onStart() {
//        if (getUserVisibleHint()&&isFirst){
//            getPresenter().insertSign();
//        }
//        super.onStart();
//    }

    public void replaceAll(List<MeetSign> list) {
        list.add(0, new MeetSign());
        quickAdapter.replaceAll(list);
    }


    private void initEvent() {

        rg_sign.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        getPresenter().netMeetSign(true);
                        break;
                    case R.id.rb2:
                        getPresenter().netMeetSign(false);
                        break;
                }

            }
        });
    }

}
