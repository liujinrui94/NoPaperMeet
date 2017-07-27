package com.bril.nopapermeet.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.adapter.BaseAdapterHelper;
import com.bril.nopapermeet.adapter.QuickAdapter;
import com.bril.nopapermeet.entity.CallService;
import com.bril.nopapermeet.presenter.CallOutPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(CallOutPresenter.class)
@ContentView(R.layout.activity_call_out)
public class CallOutActivity extends BaseActivity<CallOutPresenter> implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.gv_service)
    private GridView gvService;
    private QuickAdapter<CallService> quickAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quickAdapter = new QuickAdapter<CallService>(context, R.layout.itme_call_out) {
            @Override
            protected void convert(BaseAdapterHelper helper, CallService item) {
                helper.setText(R.id.tv_call_name, item.name);
                helper.setImageResource(R.id.iv_call_icon, item.icon);
            }
        };
        gvService.setAdapter(quickAdapter);
        gvService.setOnItemClickListener(this);
        getPresenter().initService();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void repalceCall(List<CallService> list) {
        quickAdapter.replaceAll(list);
    }

    Handler mhander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showSuccess("", "呼叫成功", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.cancel();
                    finish();
                }
            });
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        showLoading("呼叫中...");
        mhander.sendEmptyMessageDelayed(1, 3000);
    }

    @Event(R.id.tv_close)
    private void clickClose(View view) {
        finish();
    }
}
