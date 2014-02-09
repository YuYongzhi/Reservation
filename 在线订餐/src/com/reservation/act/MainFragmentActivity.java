package com.reservation.act;

import static com.reservation.utils.ToolUtils.isNetworkConnected;

import com.reservation.R;
import com.reservation.fragment.MainContentFragment;
import com.reservation.fragment.MainLeftFragment;
import com.reservation.net.HttpAPI;
import com.reservation.utils.ActivityUtils;
import com.reservation.utils.ReservationListener.OnExListViewItemClickListener;
import com.reservation.utils.ReservationListener.OnLeftRequestListener;
import com.reservation.utils.ReservationListener.OnListRequestListener;
import com.reservation.utils.ReservationListener.OnNetworkDialogClickListener;
import com.reservation.utils.StringUtils.Manager;
import com.reservation.view.LoadingDialog;
import com.reservation.view.NetworkDialog;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainFragmentActivity extends FragmentActivity 
	implements 
		OnNetworkDialogClickListener, 
		OnLeftRequestListener, 
		OnListRequestListener,
		OnExListViewItemClickListener{

	private final static String TAG = "MainFragmentActivity";
	
	private final static int CONNECT_SUCCESS = 0x01;
	private final static int REQUEST_FINISH = 0x02;
	
	private SlidingMenu menu;
	private Context mContext;
	private NetworkDialog networkDialog;	
	private NetworkReceiver networkReceiver;
	private MainHandle mHandler;
	private LoadingDialog loadingDialog;
	
	private OnLeftRequestListener onLeftRequestListener;
	private OnListRequestListener onListRequestListener;
	private OnExListViewItemClickListener itemClickListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = MainFragmentActivity.this;
		
		ActivityUtils.addAct(MainFragmentActivity.this);
		
		setContentView(R.layout.main_fragment_activity_layout);

		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		menu.setFadeDegree(0.35f);
		menu.setBehindOffset((int)(dm.widthPixels * (1 - 0.7)));
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		
		menu.setMode(SlidingMenu.LEFT);
		
		menu.setContent(R.layout.slidingmenu_content);
		
		menu.setMenu(R.layout.slidingmenu_menu_left);
		menu.setShadowDrawable(R.drawable.slidingmenu_shadow_left);
		
//		menu.setSecondaryMenu(R.layout.slidingmenu_menu_right);
//		menu.setSecondaryShadowDrawable(R.drawable.slidingmenu_shadow_right);
		
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.slidingmenu_content, new MainContentFragment(menu))
			.commit();
		
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.slidingmenu_menu_left, new MainLeftFragment(menu))
			.commit();
	
//		getSupportFragmentManager()
//			.beginTransaction()
//			.replace(R.id.slidingmenu_menu_right, new MainRightFragment(menu))
//			.commit();
		
		networkDialog = new NetworkDialog(mContext, this);
		mHandler = new MainHandle();
		
		loadingDialog = new LoadingDialog(mContext);

		if (isNetworkConnected(mContext) == false) {
			if (networkDialog.isShow() == false) {
				networkDialog.show();
			}
		} else {
			registerReceiver();
		}
	}

	@Override
	public void setPositiveButton(View view) {
		startActivity(new Intent(Settings.ACTION_SETTINGS));
		networkDialog.dismiss();
		registerReceiver();
	}

	@Override
	public void setNegativeButton(View view) {
		networkDialog.dismiss();
		unregisterReceiver();
		ActivityUtils.finishAll();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private void registerReceiver() {
		networkReceiver = new NetworkReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Manager.CONNECTIVITY_ACTION);
		registerReceiver(networkReceiver, filter);
	}
	
	private void unregisterReceiver() {
		if (networkReceiver != null) {
			unregisterReceiver(networkReceiver);
		}
	}
	
	public class NetworkReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			/**
			 * Action: android.net.conn.CONNECTIVITY_CHANGE
			 */
			if (Manager.CONNECTIVITY_ACTION.equals(action)) {
				ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo mobileNetInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				NetworkInfo wifiNetInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (!mobileNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
//					Toast.makeText(mContext, "DisConnected", Toast.LENGTH_SHORT).show();
					if (networkDialog.isShow() == false) {
						networkDialog.show();
					}
				} else {
//					Toast.makeText(mContext, "Connected", Toast.LENGTH_SHORT).show();
					networkDialog.dismiss();
					Message message = new Message();
					message.what = CONNECT_SUCCESS;
					mHandler.sendMessage(message);
				}
			}
		}
	}
	
	public class MainHandle extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CONNECT_SUCCESS:
				unregisterReceiver();
				loadingDialog.show();
				onLeftRequestListener.onRequestStart();
				Log.i(TAG, "onLeftRequestListener.onRequestStart(null)");
				break;
			case REQUEST_FINISH:
				loadingDialog.dismiss();
				menu.showMenu();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		try {
			onLeftRequestListener = (OnLeftRequestListener) fragment;
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		try {
			onListRequestListener = (OnListRequestListener) fragment;
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		try {
			itemClickListener = (OnExListViewItemClickListener) fragment;
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see com.reservation.utils.ReservationListener.OnLeftRequestListener#onRequestStart()
	 * 
	 * @author 2014-2-9
	 */
	@Override
	public void onRequestStart() {
		
	}

	/**
	 * @see com.reservation.utils.ReservationListener.OnLeftRequestListener#onRequestFinish(java.lang.Object)
	 * 
	 * @author 2014-2-9
	 */
	@Override
	public void onRequestFinish(Object obj) {
		onListRequestListener.onRequestStart(obj);
	}

	/**
	 * @see com.reservation.utils.ReservationListener.OnListRequestListener#onRequestStart(java.lang.Object)
	 * 
	 * @author 2014-2-9
	 */
	@Override
	public void onRequestStart(Object object) {
		
	}

	/**
	 * @see com.reservation.utils.ReservationListener.OnListRequestListener#onRequestFinish()
	 * 
	 * @author 2014-2-9
	 */
	@Override
	public void onRequestFinish() {
		Message message = new Message();
		message.what = REQUEST_FINISH;
		mHandler.sendMessage(message);
	}

	/**
	 * 
	 * @see com.reservation.utils.ReservationListener.OnExListViewItemClickListener#onItemClick(int, int)
	 * 
	 * @author 2014-2-9
	 */
	@Override
	public void onItemClick(Object object) {
		itemClickListener.onItemClick(object);
	}
	


}
