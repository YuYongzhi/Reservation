package com.reservation.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ToolUtils {
	
	public static String getAppAgent() {
		String appAgent = "";
		StringBuilder ua = new StringBuilder("MEDI_BOT");
		ua.append("/Android");
		ua.append("/"+android.os.Build.VERSION.RELEASE);
		ua.append("/"+android.os.Build.MODEL);
		appAgent = ua.toString();
		return appAgent;
	}
	
	public static String getAppPackageName(Context context) {
		String packageName = "";
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			packageName = packageInfo.packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageName;
	}
	
	/**
	 * 判断是否有网络连接
	 * @param context
	 * @return
	 * 
	 * @author 2014-2-5
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager manager = 
					(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = 
					manager.getActiveNetworkInfo();
			if (info != null) {
				return info.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 判断Wifi网络是否可用
	 * @param context
	 * @return
	 * 
	 * @author 2014-2-5
	 */
	public boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager manager = 
					(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = 
					manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 判断MOBILE网络是否可用
	 * @param context
	 * @return
	 * 
	 * @author 2014-2-5
	 */
	public boolean isMobileConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mMobileNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	        if (mMobileNetworkInfo != null) {  
	            return mMobileNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	/**
	 * 获取当前网络连接的类型信息
	 * @param context
	 * @return
	 * 
	 * @author 2014-2-5
	 */
	public static int getConnectedType(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {  
	            return mNetworkInfo.getType();  
	        }  
	    }  
	    return -1;  
	}
	
	
}
