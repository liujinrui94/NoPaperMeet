package com.bril.nopapermeet.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.BaseAdapterHelper;
import com.bril.nopapermeet.adapter.QuickAdapter;
import com.bril.nopapermeet.entity.MeetFile;
import com.bril.nopapermeet.entity.RecordList;
import com.bril.nopapermeet.entity.UnReadMsg;
import com.bril.nopapermeet.presenter.MeetFilePresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by Administrator on 2017/4/9.
 * 会议归档
 */
@RequiresPresenter(MeetFilePresenter.class)
@ContentView(R.layout.fragment_meet_file)
public class MeetFileFragment extends MeetBaseFragment<MeetFilePresenter>{

    @ViewInject(R.id.fragment_file_gridView)
    private GridView gv;
    QuickAdapter<MeetFile> adapter;

    @ViewInject(R.id.iv_title_right)
    private ImageView iv_add;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        setTitle(view,"会议归档");
        iv_add.setVisibility(View.VISIBLE);
        adapter=new QuickAdapter<MeetFile>(context,R.layout.item_meet_file) {
            @Override
            protected void convert(final BaseAdapterHelper helper, final MeetFile item) {
                helper.setText(R.id.item_meet_file_time, item.date);
                helper.setText(R.id.item_meet_file_content, item.content);
                helper.setOnClickListener(R.id.item_meet_file_button, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteMeetFile(helper.getPosition(),item.content);
                    }
                });
            }
        };
        gv.setAdapter(adapter);
        getPresenter().updateList();
        //
        initEvent();
    }



    private void initEvent() {
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMeetFile();
            }
        });
    }

    private void addMeetFile() {
        AlertDialog.Builder aDialog = new AlertDialog.Builder(getContext());
        final View view = LayoutInflater.from(getActivity()).
                inflate(R.layout.item_file_add, null);
        aDialog.setView(view)
                .setTitle("添加归档标签")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                        EditText et_content = (EditText) view.findViewById(R.id.item_file_add_content);

                        String strContent = et_content.getText().toString();

                        MeetFile meet=new MeetFile();
                        meet.content=strContent;
                        meet.date=getSystemTime();
                        getPresenter().addToDao(meet);//添加数据

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        aDialog.show();
    }


    private void deleteMeetFile(final int position, String title) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("确认删除");
        builder.setMessage(title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().deleteToDao(adapter.getItem(position));
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    //
    public void replaceMeetFile(List<MeetFile> meetFiles){
        adapter.replaceAll(meetFiles);
    }
    //获取系统时间
    private String getSystemTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
}
