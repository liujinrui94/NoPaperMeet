package com.bril.nopapermeet.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.entity.MeetList;
import com.bril.nopapermeet.presenter.ApplySayPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(ApplySayPresenter.class)
@ContentView(R.layout.activity_apply_say)
public class ApplySayActivity extends BaseActivity<ApplySayPresenter> {
    @ViewInject(R.id.et_say_content)
    private EditText etContent;
    MeetList meetInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meetInfo = (MeetList) getIntent().getSerializableExtra("meetInfo");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    Handler mhander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showSuccess("", "申请成功", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.cancel();
                    finish();
                }
            });
        }
    };

    @Event(R.id.btn_ok)
    private void clickCommit(View view) {
        showLoading("提交中...");
        mhander.sendEmptyMessageDelayed(1, 3000);
    }

    @Event(R.id.btn_no)
    private void clickClose(View view) {
        finish();
    }
}
