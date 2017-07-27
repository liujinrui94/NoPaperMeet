package com.bril.nopapermeet.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.BaseAdapterHelper;
import com.bril.nopapermeet.adapter.QuickAdapter;
import com.bril.nopapermeet.entity.Attachment;
import com.bril.nopapermeet.entity.Lssue;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.Notice;
import com.bril.nopapermeet.presenter.MeetInfoPresenter;
import com.bril.nopapermeet.ui.activity.PdfActivity;
import com.bril.nopapermeet.utils.OpenFiles;
import com.bril.nopapermeet.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by LiuJinrui on 2017/3/30.
 */

@RequiresPresenter(MeetInfoPresenter.class)
@ContentView(R.layout.fragment_meet_info)
public class MeetInfoFragment extends MeetBaseFragment<MeetInfoPresenter> implements AdapterView.OnItemClickListener {
    MeetList meetList;
    @ViewInject(R.id.list_view)
    private ListView listView;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.tv_time)
    private TextView tvTime;
    @ViewInject(R.id.tv_report_name)
    private TextView tvReportName;
    @ViewInject(R.id.grid_view)
    private GridView gridView;
    private QuickAdapter<Lssue> lssueQuickAdapter;
    private QuickAdapter<Attachment> attachmentQuickAdapter;

    public static MeetInfoFragment newInstance(MeetList meetList) {
        Bundle args = new Bundle();
        args.putSerializable("meetInfo", meetList);
        MeetInfoFragment fragment = new MeetInfoFragment();
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
        setTitle(view, getResources().getString(R.string.menu_lssue));
        lssueQuickAdapter = new QuickAdapter<Lssue>(context, R.layout.item_lssue) {
            @Override
            protected void convert(BaseAdapterHelper helper, final Lssue item) {
                helper.setText(R.id.tv_lssue_title, item.title);
                //1.进行中2.未进行.3.已结束
                if (item.state == 1) {
                    helper.setText(R.id.tv_lssue_statce, "进行中");
                    helper.setBackgroundRes(R.id.tv_lssue_statce, R.drawable.bg_jinxingzhong);
                    helper.setBackgroundRes(R.id.btn_lssue, R.drawable.btn_jinxingzhong);
                    helper.setBackgroundColor(R.id.ll_item, getResources().getColor(R.color.bg_jinxingzhong));
                } else if (item.state == 2) {
                    helper.setText(R.id.tv_lssue_statce, "未进行");
                    helper.setBackgroundRes(R.id.tv_lssue_statce, R.drawable.bg_weikaishi);
                    helper.setBackgroundRes(R.id.btn_lssue, R.drawable.btn_weikaishi);
                    helper.setBackgroundColor(R.id.ll_item, getResources().getColor(R.color.bg_weikaishi));
                } else {
                    helper.setText(R.id.tv_lssue_statce, "已结束");
                    helper.setBackgroundRes(R.id.tv_lssue_statce, R.drawable.bg_yijieshu);
                    helper.setBackgroundRes(R.id.btn_lssue, R.drawable.btn_yijieshu);
                    helper.setBackgroundColor(R.id.ll_item, getResources().getColor(R.color.bg_yijieshu));
                }
                if (meetList.isHost && item.state != 3) {
                    helper.setVisible(R.id.btn_lssue, true);
                    if (item.state == 1) {
                        helper.setText(R.id.btn_lssue, "关闭");
                        helper.setOnClickListener(R.id.btn_lssue, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getPresenter().closeLess(item);
                            }
                        });
                    } else if (item.state == 2) {
                        helper.setText(R.id.btn_lssue, "启动");
                        helper.setOnClickListener(R.id.btn_lssue, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getPresenter().openLess(item);
                            }
                        });
                    }
                } else {
                    helper.setVisible(R.id.btn_lssue, false);
                }
            }
        };
        listView.setAdapter(lssueQuickAdapter);
        listView.setOnItemClickListener(this);
        List<Attachment> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Attachment attachment = new Attachment();
            attachment.type = i % 3;
            attachment.name = "附件" + i;
            list.add(attachment);
        }
        attachmentQuickAdapter = new QuickAdapter<Attachment>(context, R.layout.item_attachment, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, Attachment item) {
                helper.setText(R.id.tv_title, item.name);
                if (item.type == 0) {
                    helper.setImageResource(R.id.iv_type, R.drawable.icon_word);
                } else if (item.type == 1) {
                    helper.setImageResource(R.id.iv_type, R.drawable.icon_pdf);
                } else if (item.type == 2) {
                    helper.setImageResource(R.id.iv_type, R.drawable.icon_ppt);
                }
            }
        };
        gridView.setAdapter(attachmentQuickAdapter);
        getPresenter().netLssue();
        listView.performItemClick(null, 0, 0);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = null;
                if ((attachmentQuickAdapter.getItem(i).type) == 0) {
                    File mWorkingPath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + App.Word);
                    mIntent = OpenFiles.getWordFileIntent(mWorkingPath);
                } else if ((attachmentQuickAdapter.getItem(i).type) == 1) {
                    mIntent = new Intent(context, PdfActivity.class);
                } else if ((attachmentQuickAdapter.getItem(i).type) == 2) {
                    File mWorkingPath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + App.PPT);
                    mIntent = OpenFiles.getPPTFileIntent(mWorkingPath);
                }
                startActivity(mIntent);
            }
        });

    }

    public void repalceLssue(List<Lssue> lssues) {
        lssueQuickAdapter.replaceAll(lssues);
    }

    @Override
    public void clickTitleRight() {
        super.clickTitleRight();

    }

    /**
     * 选中菜单
     */
    public void checkItme(int i) {
        List<Lssue> menus = lssueQuickAdapter.getAll();
        for (int j = 0; j < menus.size(); j++) {
            Lssue me = menus.get(j);
            if (i == j) {
                me.isCheck = true;
            } else {
                me.isCheck = false;
            }
        }
        lssueQuickAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Lssue lssue = (Lssue) lssueQuickAdapter.getItem(i);
        checkItme(i);
        flashLssus(lssue);
    }


    public void flashLssus(Lssue lssue) {
        tvTitle.setText(lssue.title);
        tvTime.setText(lssue.time);
        tvReportName.setText("汇报人：" + lssue.reportName);
    }
}
