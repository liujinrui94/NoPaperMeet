package com.bril.nopapermeet.net.base;

import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

public class JsonResponseParser implements ResponseParser {

    @Override
    public void checkResponse(UriRequest paramUriRequest) throws Throwable {

    }

    @Override
    public Object parse(Type paramType, Class<?> paramClass, String paramString) throws Throwable {
        LogUtil.d("返回内容：" + paramString.length()+"---" + paramString);
        HttpBean httpBean = new HttpBean();
        JSONObject jsonObject = new JSONObject(paramString);
        if (jsonObject.has("code"))
            httpBean.setCode(jsonObject.getInt("code"));
        if (jsonObject.has("message"))
            httpBean.setMessage(jsonObject.getString("message"));
        if (jsonObject.has("data"))
            httpBean.setData((jsonObject.getJSONObject("data").toString()));
        return httpBean;
    }
}
