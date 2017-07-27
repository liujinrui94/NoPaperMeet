package com.bril.nopapermeet.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.BaseAdapterHelper;
import com.bril.nopapermeet.adapter.QuickAdapter;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.RecordList;
import com.bril.nopapermeet.presenter.RecordPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by Administrator on 2017/3/30.
 */

@RequiresPresenter(RecordPresenter.class)
@ContentView(R.layout.fragment_record)
public class RecordFragment extends MeetBaseFragment<RecordPresenter> {

    @ViewInject(R.id.fragment_record_listView)
    private ListView lv;

    @ViewInject(R.id.et_fragment_record_content)
    private EditText et_record_content;

    @ViewInject(R.id.et_fragment_record_title)
    private EditText et_record_title;

    @ViewInject(R.id.fragment_record_cancel)
    private TextView bt_cancel;

    @ViewInject(R.id.fragment_record_save)
    private TextView bt_save;

    @ViewInject(R.id.iv_title_right)
    private ImageView iv_title_right;

    @ViewInject(R.id.fragment_record_relativeLayout)
    private RelativeLayout rLayout;
    private MeetList meetList;
    private ArrayList<RecordList> arr = new ArrayList<>();
    private QuickAdapter<RecordList> quickAdapter;


    private int index = 0;

    public static RecordFragment newInstance(MeetList meetList) {
        Bundle args = new Bundle();
        args.putSerializable("meetInfo", meetList);
        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        meetList = (MeetList) getArguments().getSerializable("meetInfo");
    }

    public MeetList getMeetList() {
        return meetList;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view, getResources().getString(R.string.menu_record));
        initVisbleEmpty(view);
        iv_title_right.setVisibility(View.VISIBLE);
        et_record_content.setEnabled(false);
        et_record_title.setEnabled(false);
        bt_save.setEnabled(false);
        quickAdapter = new QuickAdapter<RecordList>(context, R.layout.item_recordlist) {
            @Override
            protected void convert(final BaseAdapterHelper helper, final RecordList item) {
                int point = helper.getPosition() % 3;
                if (point == 0) {
                    helper.setBackgroundRes(R.id.item_recordlist_tvtitle, R.drawable.bg_jinxingzhong);
                    helper.setBackgroundRes(R.id.item_recordlist_delete, R.drawable.btn_jinxingzhong);
                    helper.setBackgroundColor(R.id.ll_item, getResources().getColor(R.color.bg_jinxingzhong));
                } else if (point == 1) {
                    helper.setBackgroundRes(R.id.item_recordlist_tvtitle, R.drawable.bg_weikaishi);
                    helper.setBackgroundRes(R.id.item_recordlist_delete, R.drawable.btn_weikaishi);
                    helper.setBackgroundColor(R.id.ll_item, getResources().getColor(R.color.bg_weikaishi));
                } else {
                    helper.setBackgroundRes(R.id.item_recordlist_tvtitle, R.drawable.bg_yijieshu);
                    helper.setBackgroundRes(R.id.item_recordlist_delete, R.drawable.btn_yijieshu);
                    helper.setBackgroundColor(R.id.ll_item, getResources().getColor(R.color.bg_yijieshu));
                }
                helper.setText(R.id.item_recordlist_tvtitle, item.getTitle());
                helper.setText(R.id.item_recordlist_tv_time, item.getTime());
                helper.setText(R.id.item_recordlist_tv_content, item.getContent());
                helper.setOnClickListener(R.id.item_recordlist_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BuilderDecelt(helper.getPosition(), item.getTitle());

                    }
                });

            }
        };
        lv.setAdapter(quickAdapter);
        getPresenter().GetRecordData();//添加数据
        initEvent(view);
    }

    //替换listview中的数据
    public void repalceList(List<RecordList> dd) {
        quickAdapter.replaceAll(dd);
    }


    private void initEvent(final View view) {
        //添加新的数据
        iv_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
        //
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_record_content.setEnabled(true);
                et_record_title.setEnabled(true);
                bt_save.setEnabled(true);
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_record_content.setEnabled(false);
                et_record_title.setEnabled(false);
                String title = et_record_title.getText().toString();
                String strContent = et_record_content.getText().toString();
                RecordList mRecordList = quickAdapter.getItem(index);
                mRecordList.setContent(strContent);
                mRecordList.setTitle(title);
                mRecordList.setTime(getSystemTime());
                getPresenter().updateRecordData(mRecordList);//保存数据
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                et_record_content.setEnabled(false);
                et_record_title.setEnabled(false);
                bt_save.setEnabled(false);
                RecordList recordList = quickAdapter.getItem(i);
                et_record_content.setText(recordList.getContent());
                et_record_title.setText(recordList.getTitle());
                index = i;
            }
        });

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
                        RecordList mRecordList = new RecordList();
                        mRecordList.setSay(strSay);
                        mRecordList.setContent(strContent);
                        mRecordList.setTitle(strSay);
                        mRecordList.setTime(getSystemTime());
                        mRecordList.setMeetId(meetList.id);
                        getPresenter().setRecordData(mRecordList);//添加数据

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        aDialog.show();
    }

    private void BuilderDecelt(final int position, String mes) {
        // 创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置标题
        builder.setTitle("确认删除");
        // 设置消息
        builder.setMessage(mes);
        // 添加确定和取消按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().removedata(quickAdapter.getItem(position));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    //获取系统时间
    private String getSystemTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
}
