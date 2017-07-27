package com.bril.nopapermeet.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bril.nopapermeet.App;
import com.bril.nopapermeet.R;
import com.bril.nopapermeet.entity.Account;
import com.bril.nopapermeet.entity.DaoSession;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.presenter.MeetDetailedPresenter;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by Administrator on 2017/4/7.
 */
@RequiresPresenter(MeetDetailedPresenter.class)
@ContentView(R.layout.fragment_meet_detailed)
public class MeetDetailedFragment extends MeetBaseFragment<MeetDetailedPresenter>{

    public MeetList meet;

    @ViewInject(R.id.fragment_meet_detailed_title)
    private TextView tv_title;
    @ViewInject(R.id.fragment_meet_detailed_data)
    private TextView tv_date;
    @ViewInject(R.id.fragment_meet_detailed_place)
    private TextView tv_place;
    @ViewInject(R.id.fragment_meet_detailed_type)
    private TextView tv_type;
    @ViewInject(R.id.fragment_meet_detailed_host)
    private TextView tv_host;
    @ViewInject(R.id.fragment_meet_detailed_reqiuce)
    private TextView tv_require;
    @ViewInject(R.id.fragment_meet_detailed_placeRequice)
    private TextView tv_placeRequire;
    @ViewInject(R.id.fragment_meet_detailed_content)
    private TextView tv_content;
    @ViewInject(R.id.fragment_meet_detailed_info)
    private TextView tv_info;
    @ViewInject(R.id.fragment_meet_detailed_people)
    private TextView tv_people;


    public static MeetDetailedFragment newInstance(MeetList meetList){
        Bundle bundle=new Bundle();
        bundle.putSerializable("meetList",meetList);
        MeetDetailedFragment fragment=new MeetDetailedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        meet= (MeetList) getArguments().getSerializable("meetList");
        Log.e("TAG",meet.hostId+"----会议id是");

        Log.e("TAG","参会人员"+meet.getMeetPersons().size());

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view, getResources().getString(R.string.meet_info));
        //
        String hostName=getPresenter().getHostName(meet);
        tv_host.setText(hostName);

        String people=getPresenter().getPeople(meet);
        tv_people.setText(people);


        tv_title.setText(meet.title);
        tv_date.setText(meet.date);
        tv_place.setText(meet.place);
        tv_type.setText(meet.type);
        tv_require.setText(meet.require);
        tv_placeRequire.setText(meet.placeRequire);
        tv_content.setText(meet.msg);
        tv_info.setText(meet.information);

    }
}
