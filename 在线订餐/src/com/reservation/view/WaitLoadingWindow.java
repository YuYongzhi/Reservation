package com.reservation.view;

import com.reservation.R;
import com.reservation.utils.StringUtils.Manager;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class WaitLoadingWindow extends PopupWindow{

	private static final String TAG = "WaitLoadingWindow";
	private Context mContext;
	private View mView;
	private LoadingReceiver loadingReceiver;
	
	public final static int FLAG_DISMISS = 0x01;
	
	public WaitLoadingWindow(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		mView = inflater.inflate(R.layout.wait_loading_popup_window, null);
		
		this.setContentView(mView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(false);
		ColorDrawable colorDrawable = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(colorDrawable);
		
		registerReceiver();
	}
	
	public void show(View parent) {
		Log.i(TAG, "show");
		showAtLocation(parent, Gravity.CENTER, 0, 0);
	}
	
	@Override
	public void dismiss() {
		super.dismiss();
		Log.i(TAG, "dismiss");
		unregisterReceiver();
	}
	
	private void registerReceiver() {
		Log.i(TAG, "registerReceiver");
		loadingReceiver = new LoadingReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Manager.WAIT_LOADING_ACTION);
		mContext.registerReceiver(loadingReceiver, filter);
	}
	
	private void unregisterReceiver() {
		Log.i(TAG, "unregisterReceiver");
		if (loadingReceiver != null) {
			mContext.unregisterReceiver(loadingReceiver);
		}
	}
	
	public class LoadingReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.i(TAG, "Action:" + action);
			if (Manager.WAIT_LOADING_ACTION.equals(action)) {
				int flag = intent.getIntExtra("flag", -1);
				switch (flag) {
				case FLAG_DISMISS:
					dismiss();
					break;

				default:
					break;
				}
			}
		}
	}
}
