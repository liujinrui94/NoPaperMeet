package com.bril.nopapermeet.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtil {

	public static boolean isNetworkAvailable(Context context) {
		boolean ret = false;
		if (context != null) {
			ConnectivityManager manger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (manger != null) {
				NetworkInfo info = manger.getActiveNetworkInfo();
				if (info != null && info.isAvailable()) {
					ret = true;
				}
			}

		}
		return ret;
	}
}
