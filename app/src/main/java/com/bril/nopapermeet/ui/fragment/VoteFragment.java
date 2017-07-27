package com.bril.nopapermeet.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.BaseAdapterHelper;
import com.bril.nopapermeet.adapter.QuickAdapter;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.entity.MeetPerson;
import com.bril.nopapermeet.entity.Option;
import com.bril.nopapermeet.entity.Vote;
import com.bril.nopapermeet.entity.VotePerson;
import com.bril.nopapermeet.presenter.VotePresenter;
import com.bril.nopapermeet.ui.activity.MyVoteActivity;
import com.bril.nopapermeet.ui.activity.StartVoteActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.github.mikephil.charting.utils.ValueFormatter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nucleus.factory.RequiresPresenter;

/**
 * Created by Administrator on 2017/3/31.
 */
@RequiresPresenter(VotePresenter.class)
@ContentView(R.layout.fragment_vote)
public class VoteFragment extends MeetBaseFragment<VotePresenter> implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.fragment_vote_pieChart)
    private PieChart pieChart;
    @ViewInject(R.id.fragment_vote_barChart)
    private BarChart barChart;
    private XAxis xAxis;
    @ViewInject(R.id.fragment_vote_listView)
    private ListView lv;
    @ViewInject(R.id.fragment_vote_lv_people)
    private ListView lv_people;
    @ViewInject(R.id.ll_vote_content)
    private LinearLayout llContent;
    @ViewInject(R.id.ll_persons)
    private LinearLayout llPersons;
    @ViewInject(R.id.tv_vote_title)
    private TextView tvVoteTitle;

    MeetList meetList;
    private QuickAdapter<Vote> adapterVote;
    private QuickAdapter<VotePerson> personQuickAdapter;

    private Vote preVote;

    public static VoteFragment newInstance(MeetList meetList) {
        Bundle args = new Bundle();
        args.putSerializable("meetInfo", meetList);
        VoteFragment fragment = new VoteFragment();
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
        setTitle(view, getResources().getString(R.string.menu_vote));
        initVisbleEmpty(view);
        if (meetList.isHost) {
            setTitleRight(view, R.drawable.icon_add);
        }
        adapterVote = new QuickAdapter<Vote>(getContext(), R.layout.itme_vote) {
            @Override
            protected void convert(BaseAdapterHelper helper, Vote item) {
                helper.setText(R.id.tv_vote_them, item.theme);
                int position = helper.getPosition();
                if (position % 3 == 0) {
                    helper.setBackgroundRes(R.id.ll_item, R.color.btn_jinxingzhong);
                } else if (position % 3 == 1) {
                    helper.setBackgroundRes(R.id.ll_item, R.color.btn_weikaishi);
                } else {
                    helper.setBackgroundRes(R.id.ll_item, R.color.btn_yijieshu);
                }
            }
        };
        lv.setAdapter(adapterVote);
        lv.setOnItemClickListener(this);

        personQuickAdapter = new QuickAdapter<VotePerson>(context, R.layout.item_vote_people) {
            @Override
            protected void convert(BaseAdapterHelper helper, VotePerson item) {
                helper.setText(R.id.tv_vote_person, item.name);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < item.options.size(); i++) {
                    if (i != 0) {
                        sb.append(",");
                    }
                    sb.append(item.options.get(i));
                }
                helper.setText(R.id.tv_vote_options, sb.toString());
            }
        };
        lv_people.setAdapter(personQuickAdapter);
        getPresenter().netVotes(meetList);
    }

    public void replace(List<Vote> list) {
        adapterVote.replaceAll(list);
    }


    public void replacePerson(List<VotePerson> list) {
        personQuickAdapter.replaceAll(list);
    }


    @Override
    public void clickTitleRight() {
        super.clickTitleRight();
        Intent intent = new Intent(context, StartVoteActivity.class);
        intent.putExtra("meetInfo", meetList);
        startActivityForResult(intent, 11);
    }

    private void setPieChart(Vote vote) {
        //饼状图
        //1、基本设置
        pieChart.setDrawCenterText(false);  //饼状图中间文字不显示
        pieChart.setDescription("");
        pieChart.setDrawHoleEnabled(false);    //设置实心
        pieChart.setRotationAngle(90); // 初始旋转角度
        //2、添加数据
        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        Random random = new Random();
        int totleCount = 0;
        for (int i = 0; i < vote.options.size(); i++) {
            vote.options.get(i).tickets = null;
            int count = vote.options.get(i).getTickets() == null ? 0 : vote.options.get(i).getTickets().size();
            totleCount += count;
        }
        for (int i = 0; i < vote.options.size(); i++) {
            xValues.add(vote.options.get(i).name);
            vote.options.get(i).tickets = null;
            int count = vote.options.get(i).getTickets() == null ? 0 : vote.options.get(i).getTickets().size();
            yValues.add(new Entry((count / (totleCount * 1f)) * 100, i));
            colors.add(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        }
        //3、y轴数据
        PieDataSet pieDataSet = new PieDataSet(yValues, "");/*显示在比例图上*/
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        //4、设置颜色
        pieDataSet.setColors(colors);
        //5、 设置数据
        PieData pieData = new PieData(xValues, pieDataSet);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        pieData.setValueFormatter(new PercentFormatter());//显示百分比
        //6、去掉比例尺和说明
        Legend legend = pieChart.getLegend();//下标说明，false
        legend.setEnabled(false);
        pieChart.setDescription("");
        //7、显示百分比
        pieData.setValueFormatter(new PercentFormatter());
        //8、显示
        pieChart.animateY(1000);
        pieChart.setData(pieData);
        setBarChart(vote, colors);
    }

    private void setBarChart(Vote vote, ArrayList<Integer> colors) {
//1、基本设置
        xAxis = barChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.setTouchEnabled(false); // 设置是否可以触摸
        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放
        //2、y轴和比例尺

        barChart.setDescription("");// 数据描述

        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        Legend legend = barChart.getLegend();//隐藏比例尺
        legend.setEnabled(false);

        //3、x轴数据,和显示位置
        ArrayList<String> xValues = new ArrayList<String>();
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        Random random = new Random();
        for (int i = 0; i < vote.options.size(); i++) {
            Option option = vote.options.get(i);
            xValues.add(option.name);
            vote.options.get(i).tickets = null;
            int count = vote.options.get(i).getTickets() == null ? 0 : vote.options.get(i).getTickets().size();
            yValues.add(new BarEntry(count, i));
        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//数据位于底部
        //4、y轴数据
        //new BarEntry(20, 0)前面代表数据，后面代码柱状图的位置；

        //5、设置显示的数字为整形
        BarDataSet barDataSet = new BarDataSet(yValues, "");
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v) {
                int n = (int) v;
                return n + "";
            }
        });
        //6、设置柱状图的颜色
        barDataSet.setColors(colors);
        //7、显示，柱状图的宽度和动画效果
        BarData barData = new BarData(xValues, barDataSet);
        barDataSet.setBarSpacePercent(40f);//值越大，柱状图就越宽度越小；
        barChart.animateY(1000);
        barChart.setData(barData);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        llContent.setVisibility(View.GONE);
        llPersons.setVisibility(View.GONE);
        preVote = adapterVote.getItem(position);
        getPresenter().checkVote(preVote);
    }

    public void goToMyVote(Vote vote) {
        Intent mIntent = new Intent(context, MyVoteActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("vote", vote);
        mIntent.putExtras(mBundle);
        startActivityForResult(mIntent, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 10) {//展示页面
            setDetail();
        } else if (resultCode == Activity.RESULT_OK && requestCode == 11) {
            meetList.votes = null;
            getPresenter().netVotes(meetList);
        }
    }

    public void setDetail() {
        llContent.setVisibility(View.VISIBLE);
        tvVoteTitle.setText(preVote.theme);
        //设置饼状图
        setPieChart(preVote);
        if (meetList.isHost) {
            llPersons.setVisibility(View.VISIBLE);
            getPresenter().netMeetPerson(meetList.getMeetPersons(), preVote);
        }

    }
}
