package com.bril.nopapermeet.net.base;

import android.content.Context;
import android.text.TextUtils;

import com.bril.nopapermeet.R;
import com.bril.nopapermeet.utils.NetworkUtil;
import com.bril.nopapermeet.utils.ResourceUtil;
import com.bril.nopapermeet.utils.ToastUtils;

import org.xutils.common.Callback;
import org.xutils.x;


public class Http {

    /**
     * post请求
     *
     * @return
     */
    public static Callback.Cancelable doPost(Context context, APIBase base, Object tag, HttpCallBack httpCallback) {
        if (!NetworkUtil.isNetworkAvailable(context)) {
            String error = context.getResources().getString(R.string.hint_no_net);
            ToastUtils.shortShow(context, error);
            httpCallback.onFail(error, tag);
            return null;
        }
        if (TextUtils.isEmpty(NetConstant.Host_Url)) {
            NetConstant.Host_Url = ResourceUtil.getConfString(context, "NET_HOST_URL");
        }
        Callback.Cancelable cancelable = x.http().post(base, new HttpBaseDeal(context, httpCallback, base, tag));
        base.setConnectTimeout(15*1000);
        return cancelable;
    }

    /**
     * @param context
     * @param base
     * @param httpCallback
     * @return
     */
    public static Callback.Cancelable doPost(Context context, APIBase base, HttpCallBack httpCallback) {
        return doPost(context, base, null, httpCallback);
    }

    /**
     * @param context
     * @param base
     * @param httpCallback
     * @return
     */
    public static Callback.Cancelable doGet(Context context, APIBase base, HttpCallBack httpCallback) {
        return doGet(context, base, null, httpCallback);
    }

    /**
     * get请求
     *
     * @return
     */
    public static Callback.Cancelable doGet(Context context, APIBase base, Object tag, HttpCallBack httpCallback) {
        if (!NetworkUtil.isNetworkAvailable(context)) {
            String error = context.getString(R.string.hint_no_net);
            ToastUtils.shortShow(context, error);
            httpCallback.onFail(error, tag);
            return null;
        }
        if (TextUtils.isEmpty(NetConstant.Host_Url)) {
            NetConstant.Host_Url = ResourceUtil.getConfString(context, "NET_HOST_URL");
        }
        base.setConnectTimeout(15*1000);
        Callback.Cancelable cancelable = x.http().get(base, new HttpBaseDeal(context, httpCallback, base, tag));
        return cancelable;
    }

}
