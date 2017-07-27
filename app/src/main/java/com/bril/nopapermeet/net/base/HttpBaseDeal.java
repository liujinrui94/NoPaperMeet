package com.bril.nopapermeet.net.base;

import android.content.Context;

import com.bril.nopapermeet.utils.ToastUtils;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;

public class HttpBaseDeal implements CommonCallback<HttpBean> {
    HttpCallBack httpCallback;
    Context context;
    APIBase base;
    Object tag;

    public HttpBaseDeal(Context context, HttpCallBack httpCallback, APIBase base, Object tag) {
        this.httpCallback = httpCallback;
        this.context = context;
        this.base = base;
        this.tag = tag;
    }

    @Override
    public void onSuccess(HttpBean httpBean) {
        LogUtil.d("requstBody====" + base.getBodyContent());
        if (httpBean != null) {
            int code = httpBean.getCode();
            String msg = httpBean.getMessage();
            if (code == 0) {
                Object data = base.parseData(httpBean.getData());
                if (data == null) {
                    data = httpBean.getData();
                }
                httpCallback.onSuccess(data, tag);
            } else if (code == 100) {
                LogUtil.e("请求错误－>::" + msg);
                httpCallback.onFail(String.valueOf(code), tag);
            } else if (code == 101) {
                httpCallback.onFail("访问错误，请稍后再试！", tag);
                LogUtil.e("缺少参数－>::" + msg);
                httpCallback.onFail(String.valueOf(code), tag);
            } else if (code == 102) {
                ToastUtils.longShow(context, httpBean.getMessage());
                httpCallback.onFail(String.valueOf(code), tag);
            } else if (code == 200) {
                LogUtil.e("服务器错误－>::" + msg);
                httpCallback.onFail(String.valueOf(code), tag);
            } else {
                LogUtil.e("未定义错误－>::" + msg);
                httpCallback.onFail(String.valueOf(code), tag);
            }
        } else {
            LogUtil.e("解析错误");
            httpCallback.onFail("数据解析数据错误", tag);
        }
    }

    @Override
    public void onError(Throwable paramThrowable, boolean paramBoolean) {
        LogUtil.e("请求失败");
        httpCallback.onFail("请求失败", tag);
        paramThrowable.printStackTrace();
    }

    @Override
    public void onCancelled(CancelledException paramCancelledException) {
        LogUtil.e("请求取消");
        httpCallback.onFail("请求取消", tag);
    }

    @Override
    public void onFinished() {
        // httpCallback.onFail("被finish");
    }

}
