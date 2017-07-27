package com.bril.nopapermeet.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.BaseAdapterHelper;
import com.bril.nopapermeet.adapter.QuickAdapter;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.presenter.MeetsPresenter;
import com.bril.nopapermeet.widget.HorizontalListView;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * 会议列表
 */
@RequiresPresenter(MeetsPresenter.class)
@ContentView(R.layout.fragment_meets)
public class MeetsFragment extends BaseFragment<MeetsPresenter> {
    @ViewInject(R.id.fragment_meet_list_hlv)
    private HorizontalListView mHorizontalListView;
    private QuickAdapter<MeetList> quickAdapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view, getResources().getString(R.string.menu_meet));
        quickAdapter = new QuickAdapter<MeetList>(context, R.layout.meet_list_item_new) {
            @Override
            protected void convert(BaseAdapterHelper helper, MeetList item) {
                helper.setVisible(R.id.tv_meet_participation_top, true);
                helper.setVisible(R.id.tv_meet_participation_bottom, true);
                helper.setVisible(R.id.ll_host_top_tv, true);
                helper.setVisible(R.id.ll_host_bottom_tv, true);
                helper.setVisible(R.id.tv_start_meet_top, true);
                helper.setVisible(R.id.tv_start_meet_bottom, true);
                helper.setVisible(R.id.tv_end_meet_top, true);
                helper.setVisible(R.id.tv_end_meet_bottom, true);

                helper.setText(R.id.tv_meet_list_item_title, item.title);
                helper.setText(R.id.tv_meet_list_item_title_bottom, item.title);
                helper.setText(R.id.tv_meet_list_item_top_date, item.date);
                helper.setText(R.id.tv_meet_list_item_bottom_date, item.date);
                helper.setText(R.id.tv_meet_list_item_msg, item.msg);
                helper.setText(R.id.tv_meet_list_item_msg_bottom, item.msg);
                helper.setText(R.id.tv_meet_list_item_place, item.place);
                helper.setText(R.id.tv_meet_list_item_place_bottom, item.place);
                helper.setText(R.id.tv_meet_list_item_type, item.type);
                helper.setText(R.id.tv_meet_list_item_type_bottom, item.type);

                String hostName=getPresenter().getHostName(item);
                helper.setText(R.id.tv_meet_list_item_host, hostName);
                helper.setText(R.id.tv_meet_list_item_host_bottom,hostName);


                if (helper.getPosition() % 2 == 0) {
                    helper.setInVisible(R.id.ll_meet_list_item_top, true);
                    helper.setInVisible(R.id.ll_meet_list_item_bottom, false);

                } else {
                    helper.setInVisible(R.id.ll_meet_list_item_top, false);
                    helper.setInVisible(R.id.ll_meet_list_item_bottom, true);
                }

                if (item.isHost) {
                    helper.setVisible(R.id.tv_meet_participation_top, false);
                    helper.setVisible(R.id.tv_meet_participation_bottom, false);
                    helper.setVisible(R.id.ll_host_top_tv, true);
                    helper.setVisible(R.id.ll_host_bottom_tv, true);
                    helper.setBackgroundRes(R.id.meet_list_top_ll, R.drawable.bg_huiyi_cheng_xia);
                    helper.setBackgroundRes(R.id.meet_list_bottom_ll, R.drawable.bg_huiyi_cheng_shang);
                    helper.setTextColorRes(R.id.tv_meet_list_item_top_date, R.color.bg_time_txt_2);
                    helper.setTextColorRes(R.id.tv_meet_list_item_bottom_date, R.color.bg_time_txt_2);
                } else {
                    helper.setVisible(R.id.tv_meet_participation_top, true);
                    helper.setVisible(R.id.tv_meet_participation_bottom, true);
                    helper.setVisible(R.id.ll_host_top_tv, false);
                    helper.setVisible(R.id.ll_host_bottom_tv, false);
                    helper.setBackgroundRes(R.id.meet_list_top_ll, R.drawable.bg_huiyi_lv_shang);
                    helper.setBackgroundRes(R.id.meet_list_bottom_ll, R.drawable.bg_huiyi_lv_xia);
                    helper.setTextColorRes(R.id.tv_meet_list_item_top_date, R.color.bg_time_txt_1);
                    helper.setTextColorRes(R.id.tv_meet_list_item_bottom_date, R.color.bg_time_txt_1);
                }
//                if (item.state == 0) {
//                    helper.setText(R.id.tv_meet_state, "未开始");
//                    helper.setText(R.id.tv_meet_state1, "未开始");
//                } else if (item.state == 1) {
//                    helper.setText(R.id.tv_meet_state, "进行中");
//                    helper.setText(R.id.tv_meet_state1, "进行中");
//                } else if (item.state == 2) {
//                    helper.setText(R.id.tv_meet_state, "已结束");
//                    helper.setText(R.id.tv_meet_state1, "已结束");
//                }

                helper.setOnClickListener(R.id.tv_start_meet_top, new ClickOpen(item));
                helper.setOnClickListener(R.id.tv_start_meet_bottom, new ClickOpen(item));
                helper.setOnClickListener(R.id.tv_end_meet_top, new ClickClose(item));
                helper.setOnClickListener(R.id.tv_end_meet_bottom, new ClickClose(item));
                helper.setOnClickListener(R.id.tv_meet_participation_top, new ClickVote(item));
                helper.setOnClickListener(R.id.tv_meet_participation_bottom, new ClickVote(item));
                if (item.state == 1) {
                    helper.setText(R.id.tv_start_meet_top, "参与");
                    helper.setText(R.id.tv_start_meet_bottom, "参与");
                    helper.setOnClickListener(R.id.tv_start_meet_top, new ClickVote(item));
                    helper.setOnClickListener(R.id.tv_start_meet_bottom, new ClickVote(item));
                }
                if (item.state == 2) {
                    helper.setText(R.id.tv_start_meet_top, "查看");
                    helper.setText(R.id.tv_start_meet_bottom, "查看");
                    helper.setVisible(R.id.tv_end_meet_top, false);
                    helper.setVisible(R.id.tv_end_meet_bottom, false);
                    helper.setOnClickListener(R.id.tv_start_meet_top, new ClickVote(item));
                    helper.setOnClickListener(R.id.tv_start_meet_bottom, new ClickVote(item));
                }
            }
        };
        mHorizontalListView.setAdapter(quickAdapter);
        getPresenter().netMeetList();
    }

    public void flashMeetList(List<MeetList> list) {
        quickAdapter.replaceAll(list);
    }

    class ClickOpen implements View.OnClickListener {
        MeetList meetList;

        public ClickOpen(MeetList meetList) {
            this.meetList = meetList;
        }

        @Override
        public void onClick(View view) {
            getPresenter().netOpenMeet(meetList);
        }
    }

    class ClickClose implements View.OnClickListener {
        MeetList meetList;

        public ClickClose(MeetList meetList) {
            this.meetList = meetList;
        }

        @Override
        public void onClick(View view) {
            new AlertDialog.Builder(context)
                    .setMessage("确认要关闭此会议吗？")
                    .setNegativeButton(R.string.btn_no, null)
                    .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            getPresenter().netCloseMeet(meetList);
                        }
                    }).show();
        }
    }

    class ClickVote implements View.OnClickListener {
        MeetList meetList;

        public ClickVote(MeetList meetList) {
            this.meetList = meetList;
        }

        @Override
        public void onClick(View view) {
            getPresenter().netJoinMeet(meetList);
        }
    }

}
