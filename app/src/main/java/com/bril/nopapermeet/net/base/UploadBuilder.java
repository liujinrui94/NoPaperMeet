package com.bril.nopapermeet.net.base;

import android.text.TextUtils;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.ParamsBuilder;

import javax.net.ssl.SSLSocketFactory;

public class UploadBuilder implements ParamsBuilder {

	@Override
	public String buildUri(RequestParams paramRequestParams,
                           HttpRequest paramHttpRequest) {
		StringBuffer sbUri = new StringBuffer();
		if (!TextUtils.isEmpty(paramHttpRequest.host())) {
			sbUri.append(paramHttpRequest.host());
		} else {
			sbUri.append(NetConstant.Host_Url);
		}
		sbUri.append("/").append(paramHttpRequest.path());
		return sbUri.toString();
	}

	@Override
	public String buildCacheKey(RequestParams paramRequestParams,
                                String[] paramArrayOfString) {
		return null;
	}

	@Override
	public SSLSocketFactory getSSLSocketFactory() {
		return null;
	}

	@Override
	public void buildParams(RequestParams paramRequestParams) {
		paramRequestParams.setMultipart(true);
	}

	@Override
	public void buildSign(RequestParams paramRequestParams,
			String[] paramArrayOfString) {

	}

}
