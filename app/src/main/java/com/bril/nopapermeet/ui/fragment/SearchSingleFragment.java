package com.bril.nopapermeet.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.SearchSingleAdapter;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.presenter.SearchListPresenter;
import com.bril.nopapermeet.presenter.SearchSinglePresenter;
import com.bril.nopapermeet.ui.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by Administrator on 2017/3/30.
 */

@RequiresPresenter(SearchSinglePresenter.class)
@ContentView(R.layout.fragment_search_single)
public class SearchSingleFragment extends BaseFragment<SearchSinglePresenter> {

    @ViewInject(R.id.fragmentSearchSingle_listView)
    private ListView lv;
    private ArrayList<String> arr = new ArrayList<>();

    private String tag;
    private List<MeetList> data;

    private SearchSingleAdapter adapter;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        tag=getArguments().getString("00");
        data=getPresenter().searchData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        if (data!=null){
            for (int i = 0; i < data.size(); i++) {
                arr.add(tag+data.get(i).title);
            }
        }
        adapter=new SearchSingleAdapter(getContext(), arr);
        lv.setAdapter(adapter);
        //每一个item的点击事件
        initEvent();

    }

    private void initEvent() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data!=null) {
                    getPresenter().particularMeet(data.get(position));
                }
            }
        });
    }
}
