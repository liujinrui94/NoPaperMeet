package com.bril.nopapermeet.net.base;

import org.xutils.http.RequestParams;
import org.xutils.http.app.ParamsBuilder;

import java.io.Serializable;

public abstract class APIBase extends RequestParams implements Serializable {
    public APIBase() {
    }

    public APIBase(String uri, ParamsBuilder builder) {
        this(uri, builder, null, null);

    }

    public APIBase(String uri) {
        super(uri);
    }

    public APIBase(String uri, ParamsBuilder builder, String[] signs, String[] cacheKeys) {
        super(uri, builder, signs, cacheKeys);
    }

    /**
     * 解析返回data数据
     *
     * @param @param  dataJson
     * @param @return
     * @return Object
     * @Title: parseData
     * @Description: TODO
     */
    public abstract Object parseData(String dataJson);
}
