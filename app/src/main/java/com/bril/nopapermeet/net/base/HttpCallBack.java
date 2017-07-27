package com.bril.nopapermeet.net.base;

public interface HttpCallBack {
	abstract void onSuccess(Object t, Object tag);

	abstract void onFail(String msg, Object tag);
}
