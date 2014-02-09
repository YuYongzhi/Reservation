package com.reservation.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;

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

	/**
	 * 检查是否挂载SD卡
	 * @return
	 * 
	 * @author 2014-2-9
	 */
	public static boolean isSDCardExist() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取SD卡剩余空间
	 * @return  （单位 :KB）
	 * 
	 * @author 2014-2-9
	 */
	public static int getSDCardAvailableSize() {
		StatFs mStatFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
		long blockSize = mStatFs.getBlockSize();
		long availableBlocks = mStatFs.getAvailableBlocks();
		
		return (int)(availableBlocks * blockSize / 1024);
	}
	
	public static Bitmap getRoundBitmap(Context context, Bitmap bitmap, int radius) {
		float density = context.getResources().getDisplayMetrics().density;
		float radiusPx = radius * density;
		Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_4444);
		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		
		paint.setAntiAlias(true);
		canvas.drawRoundRect(rectF, radiusPx, radiusPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		
		return bmp;
	}
	
}
