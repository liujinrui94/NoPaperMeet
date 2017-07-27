package com.bril.nopapermeet.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.presenter.MainPresenter;
import com.bril.nopapermeet.presenter.SearchListPresenter;
import com.bril.nopapermeet.presenter.SearchPresenter;
import com.bril.nopapermeet.ui.activity.MainActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by Administrator on 2017/3/30.
 */

@RequiresPresenter(SearchListPresenter.class)
@ContentView(R.layout.fragment_search_list)
public class SearchListFragment extends MeetBaseFragment<SearchListPresenter> {

    @ViewInject(R.id.fragmentSearchList_tableLayout)
    private TabLayout tabLayout;
    @ViewInject(R.id.fragmentSearchList_viewPager)
    private ViewPager viewPager;
    private String tab[] = {"会议", "会议资料", "会议记录", "会议纪要"};
    private ArrayList<Fragment> arr;
    private String type;
    private String key;
    //

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        type = getArguments().getString("type");
        key = getArguments().getString("searchKey");
    }

    public static SearchListFragment newInstance(String searchType, String searchKey) {
        Bundle args = new Bundle();
        args.putString("type", searchType);
        args.putString("key", searchKey);
        SearchListFragment fragment = new SearchListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout.setupWithViewPager(viewPager);
        arr = new ArrayList<>();
        setTitle(view, getResources().getString(R.string.menu_search));
        if (type.equals("全部")) {
            for (int i = 0; i < tab.length; i++) {
                SearchSingleFragment fragment = getSearchSingleFragment(tab[i]);
                arr.add(fragment);
            }
        } else {
            arr.add(getSearchSingleFragment(type));
            tab = new String[]{type};
        }
        viewPager.setAdapter(new SearchTabAdapter(getChildFragmentManager(), tab, arr));

    }

    @NonNull
    private SearchSingleFragment getSearchSingleFragment(String value) {
        Bundle args = new Bundle();
        args.putString("00", value);
        SearchSingleFragment fragment = new SearchSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("setUserVisibleHint", "----" + isVisibleToUser);
    }

    @Override
    public void clickBack() {
        MainActivity activity = (MainActivity) getActivity();
        activity.getPresenter().openSearch();
        getFragmentManager().beginTransaction().remove(this).commit();
    }


    class SearchTabAdapter extends FragmentStatePagerAdapter {

        private String strTab[];
        private ArrayList<Fragment> arrs;

        public SearchTabAdapter(FragmentManager fm, String strTab[], ArrayList<Fragment> arrs) {
            super(fm);
            this.strTab = strTab;
            this.arrs = arrs;
        }

        @Override
        public Fragment getItem(int position) {
            return arrs.get(position);
        }

        @Override
        public int getCount() {
            return strTab.length;
        }

        //
        @Override
        public CharSequence getPageTitle(int position) {
            return strTab[position];
        }
    }
}
