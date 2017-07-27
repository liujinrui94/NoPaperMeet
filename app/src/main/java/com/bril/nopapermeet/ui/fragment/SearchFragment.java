package com.bril.nopapermeet.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.presenter.SearchPresenter;
import com.bril.nopapermeet.ui.activity.MainActivity;
import com.bril.nopapermeet.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import nucleus.factory.RequiresPresenter;

/**
 * 资料检索
 */
@RequiresPresenter(SearchPresenter.class)
@ContentView(R.layout.fragment_search)
public class SearchFragment extends BaseFragment<SearchPresenter> {

    @ViewInject(R.id.fragment_search_spinner)
    private Spinner spinner;
    @ViewInject(R.id.fragment_search_editText)
    private EditText editText;
    @ViewInject(R.id.fragment_search_button)
    private Button button;
    private ArrayAdapter<String> arr;
    private String spinnerType[] = {"全部", "会议", "会议资料", "会议记录", "会议纪要"};

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view, getResources().getString(R.string.menu_search));
        arr = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, R.id.item_spinner_tv, spinnerType);
        spinner.setAdapter(arr);
        //
        initEvent();
    }

    private void initEvent() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextStr = editText.getText().toString();
                String spinnerStr = spinner.getSelectedItem().toString();//法律文库
                if (TextUtils.isEmpty(editTextStr)) {
                    ToastUtils.shortShow(context, "请输入搜索关键字!");
                    return;
                }
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.selectFragment(SearchListFragment.newInstance(spinnerStr, editTextStr), "SearchResult");
            }
        });
    }
}
