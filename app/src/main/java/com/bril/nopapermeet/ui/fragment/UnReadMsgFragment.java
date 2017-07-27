package com.bril.nopapermeet.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.BaseAdapterHelper;
import com.bril.nopapermeet.adapter.QuickAdapter;
import com.bril.nopapermeet.entity.Menu;
import com.bril.nopapermeet.entity.UnReadMsg;
import com.bril.nopapermeet.presenter.UnReadMsgPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * 未读消息
 */
@RequiresPresenter(UnReadMsgPresenter.class)
@ContentView(R.layout.fragment_unread_msg)
public class UnReadMsgFragment extends BaseFragment<UnReadMsgPresenter> {
    @ViewInject(R.id.gv_msg)
    private GridView gvMsg;
    QuickAdapter quickAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view, getResources().getString(R.string.menu_msg));
        quickAdapter = new QuickAdapter<UnReadMsg>(context, R.layout.itme_unread_msg) {
            @Override
            protected void convert(BaseAdapterHelper helper, UnReadMsg item) {
                helper.setText(R.id.tv_msg_title, item.title);
                helper.setText(R.id.tv_msg_time, item.time);
                helper.setText(R.id.tv_msg_content, item.content);
            }
        };
        gvMsg.setAdapter(quickAdapter);
        getPresenter().netUnReadMsg();
    }

    public void repalceMsg(List<UnReadMsg> list) {
        quickAdapter.replaceAll(list);
    }
}
