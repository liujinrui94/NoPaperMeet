package com.bril.nopapermeet.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.BaseAdapterHelper;
import com.bril.nopapermeet.adapter.QuickAdapter;
import com.bril.nopapermeet.dao.DbAccount;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.Menu;
import com.bril.nopapermeet.entity.Notice;
import com.bril.nopapermeet.entity.RecordList;
import com.bril.nopapermeet.presenter.NoticePresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * 通知通告
 */
@RequiresPresenter(NoticePresenter.class)
@ContentView(R.layout.fragment_notice)
public class NoticeFragment extends MeetBaseFragment<NoticePresenter> implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.lv_notice)
    private ListView lvNotice;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.tv_msg)
    private TextView tvMsg;
    @ViewInject(R.id.tv_time)
    private TextView tvTime;
    QuickAdapter quickAdapter;
    MeetList meetList;
    @ViewInject(R.id.iv_title_right)
    private ImageView iv_right;

    public static NoticeFragment newInstance(MeetList meetList) {
        Bundle args = new Bundle();
        args.putSerializable("meetInfo", meetList);
        NoticeFragment fragment = new NoticeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        meetList = (MeetList) getArguments().getSerializable("meetInfo");


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view, getResources().getString(R.string.menu_notice));
        if (meetList.getIsHost()) {
            iv_right.setVisibility(View.VISIBLE);
        }
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });



        quickAdapter = new QuickAdapter<Notice>(context, R.layout.itme_notice) {
            @Override
            protected void convert(BaseAdapterHelper helper, Notice item) {
                helper.setText(R.id.tv_msg_title, item.title);
                if (item.isCheck) {
                    helper.setBackgroundRes(R.id.ll_item, R.color.item_select);
                } else {
                    helper.setBackgroundRes(R.id.ll_item, R.color.item);
                }
            }
        };
        lvNotice.setAdapter(quickAdapter);
        lvNotice.setOnItemClickListener(this);
        getPresenter().netNotice(meetList);
        lvNotice.performItemClick(null, 0, 0);
    }


    public void repalceMsg(List<Notice> list) {
        quickAdapter.replaceAll(list);
    }

    /**
     * 选中菜单
     */
    public void checkItme(int i) {
        List<Notice> menus = quickAdapter.getAll();
        for (int j = 0; j < menus.size(); j++) {
            Notice me = menus.get(j);
            if (i == j) {
                me.isCheck = true;
            } else {
                me.isCheck = false;
            }
        }
        quickAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Notice notice = (Notice) quickAdapter.getItem(i);
        checkItme(i);
        if (notice != null) {
            flashNotice(notice);
        }
    }

    public void flashNotice(Notice notice) {
        tvTitle.setText(notice.title);
        tvMsg.setText(notice.msessage);
        tvTime.setText(notice.time);
    }

    private void addData() {
        AlertDialog.Builder aDialog = new AlertDialog.Builder(getContext());
        final View view = LayoutInflater.from(getActivity()).
                inflate(R.layout.item_recore_add, null);
        aDialog.setView(view)
                .setTitle("添加会议记录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                        EditText et_say = (EditText) view.findViewById(R.id.item_record_add_say);
                        EditText et_content = (EditText) view.findViewById(R.id.item_record_add_content);
                        String strSay = et_say.getText().toString();
                        String strContent = et_content.getText().toString();
                        Notice mNotice=new Notice();
                        mNotice.setTitle(strSay);
                        mNotice.setMsessage(strContent);
                        mNotice.setMeetId(meetList.getId());
                        mNotice.setTime(getSystemTime());
                        getPresenter().saveNotice(mNotice,meetList);//添加数据

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        aDialog.show();
    }

    //获取系统时间
    private String getSystemTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

}
