package com.reservation.fragment;

import java.util.ArrayList;
import java.util.List;

import com.reservation.R;
import com.reservation.adapter.FragmentTabAdapter;
import com.reservation.adapter.FragmentTabAdapter.OnRgsCheckedChangedListener;
import com.reservation.net.HttpAPI;
import com.reservation.utils.ActivityUtils;
import com.reservation.utils.StringUtils.Manager;
import com.reservation.view.WaitLoadingWindow;
import com.reservation.view.slidingmenu.buildin.BaseFragment;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.app.Dialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.reservation.utils.ToolUtils.*;

public class MainContentFragment extends BaseFragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup contain, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_content_fragment_layout, null);
	}
	
	private Context mContext;
	private SlidingMenu menu;
	private View parent;
	private RadioGroup rgs;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private ConnectionReceiver connectionReceiver;
	private NetworkDialog networkDialog;
	private WaitLoadingWindow loadingWindow;
	
	private boolean isGetOne = false;
	private boolean isGetTwo = false;
	private String oneJSON = null;
	private String twoJSON = null;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case 1:
				oneJSON = (String) msg.obj;
				isGetOne = true;
				if (isGetOne && isGetTwo) {
					Message message = new Message();
					message.what = 3;
					mHandler.sendMessage(message);
				}
				break;
			case 2:
				twoJSON = (String) msg.obj;
				isGetTwo = true;
				if (isGetOne && isGetTwo) {
					Message message = new Message();
					message.what = 3;
					mHandler.sendMessage(message);
				}
				break;
			case 3:
				Intent intent = new Intent();
				intent.setAction(Manager.LEFT_FRAGMENT_ACTION);
				intent.putExtra("oneJSON", oneJSON);
				intent.putExtra("twoJSON", twoJSON);
				mContext.sendBroadcast(intent);
				
				Intent loadingIntent = new Intent();
				loadingIntent.setAction(Manager.WAIT_LOADING_ACTION);
				loadingIntent.putExtra("flag", WaitLoadingWindow.FLAG_DISMISS);
				mContext.sendBroadcast(loadingIntent);
				break;
			}
		};
	};
	
	public MainContentFragment(SlidingMenu menu) {
		this.menu = menu;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		this.initViews();
		initNetworkDialog();
		registerReceiver();
	}
	
	private void requestData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String json = HttpAPI.getOneLevelTypeJSON();
				Message message = new Message();
				message.what = 1;
				message.obj = json;
				mHandler.sendMessage(message);
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				String json = HttpAPI.getTwoLevelTypeJSON();
				Message message = new Message();
				message.what = 2;
				message.obj = json;
				mHandler.sendMessage(message);
			}
		}).start();
	}
	
	private void registerReceiver() {
		Log.i(TAG, "registerReceiver");
		connectionReceiver = new ConnectionReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mContext.registerReceiver(connectionReceiver, filter);
	}
	
	private void unregisterReceiver() {
		Log.i(TAG, "unregisterReceiver");
		if (connectionReceiver != null) {
			mContext.unregisterReceiver(connectionReceiver);
		}
	}
	
	private void initViews() {
		parent = this.getView();
		fragments.add(new MenuListFragment(menu));
		fragments.add(new TabBFragment(menu));
		fragments.add(new TabCFragment(menu));
		
		rgs = (RadioGroup) parent.findViewById(R.id.tab_rbtn_group);
		
		FragmentTabAdapter tabAdapter = 
				new FragmentTabAdapter((FragmentActivity) mContext, fragments, R.id.tab_content, rgs);
		tabAdapter.setOnRgsCheckedChangedListener(new OnRgsCheckedChangedListener(){
			@Override
			public void OnRgsCheckedChanged(RadioGroup radioGroup,
					int checkdId, int index) {
//				super.OnRgsExtraCheckedChanged(radioGroup, checkdId, index);
				Toast.makeText(mContext, "You clicked. , Index=" + index, Toast.LENGTH_LONG).show();
			}
		});
		
		initLoadingWindow();
	}
	
	private void initLoadingWindow() {
		if (loadingWindow == null) {
			loadingWindow = new WaitLoadingWindow(mContext);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
		if (isNetworkConnected(mContext) == false) {
			if (networkDialog.isShow() == false) {
				networkDialog.show();
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
		unregisterReceiver();
	}
	
	public class ConnectionReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			/**
			 * Action: ConnectivityManager.CONNECTIVITY_ACTION
			 */
			if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
				ConnectivityManager manager = 
						(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mobNetInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				NetworkInfo wifiNetInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (mobNetInfo.isConnected() || wifiNetInfo.isConnected()) {
					// connect network
					Toast.makeText(context, "connect", Toast.LENGTH_SHORT).show();
					if (networkDialog.isShow() == true) {
						networkDialog.dismiss();
					}
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							loadingWindow.show(parent);
							requestData();
						}
					}, 100);
				} else {
					// unconnect network
					Toast.makeText(context, "un connect", Toast.LENGTH_SHORT).show();
					if (networkDialog.isShow() == false) {
						networkDialog.show();
					}
				}
			}
		}
	}
	
	private void initNetworkDialog() {
		if (networkDialog == null) {
			networkDialog = new NetworkDialog(mContext);
		}
//		networkDialog.
	}
	
	public class NetworkDialog extends Dialog {

		private boolean isShow = false;
		private Context context;
		private Button mPositiveBtn;
		private Button mNegativeBtn;
		
		public NetworkDialog(Context context) {
			super(context, R.style.DialogTheme);
			this.context = context;
		}
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			this.setCancelable(false);
			this.setCancelable(false);
			this.setContentView(R.layout.network_dialog_content_layout);
			mPositiveBtn = (Button) findViewById(R.id.network_dialog_positive_button);
			mNegativeBtn = (Button) findViewById(R.id.network_dialog_negative_button);
			
			mPositiveBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					context.startActivity(new Intent(Settings.ACTION_SETTINGS));
					dismiss();
				}
			});
			
			mNegativeBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ActivityUtils.finishAll();
				}
			});
		}
		
		@Override
		public void show() {
			super.show();
			isShow = true;
		}
		
		@Override
		public void dismiss() {
			super.dismiss();
			isShow = false;
		}
		
		public boolean isShow() {
			return this.isShow;
		}
	}
	
}
