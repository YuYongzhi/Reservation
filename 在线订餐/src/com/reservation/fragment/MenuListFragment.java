package com.reservation.fragment;

import java.util.HashMap;
import java.util.Map;

import com.reservation.R;
import com.reservation.net.HttpAPI;
import com.reservation.view.FragmentListener;
import com.reservation.view.WaitLoadingWindow;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuListFragment extends Fragment implements FragmentListener.OnMenuItemClickListener{
	
	private static final String TAG = "MenuListFragment";

	private Context mContext;
	private SlidingMenu menu;
	private View parentView;
	private RelativeLayout mTitleLayout;
	private int currPage = 1;
	private final static int PAGE_SIZE = 5;
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case 1:
				String json = (String) msg.obj;
				Log.i(TAG, "JSON:" + json);
				break;
			}
		}
	};
	
	
	public MenuListFragment(SlidingMenu menu) {
		this.menu = menu;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "FragmentA-- onCreateView");
		return inflater.inflate(R.layout.menu_list_fragment_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "FragmentA-- onActivityCreated");
		mContext = getActivity();
		initViews();
	
	}
	
	private void initViews() {
		parentView = getView();
		mTitleLayout = (RelativeLayout) parentView.findViewById(R.id.menu_list_title_layout);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(TAG, "FragmentA-- onAttach");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "FragmentA-- onCreate");
		menu.setSlidingEnabled(true);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "FragmentA-- onDestroy");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "FragmentA-- onPause");
		menu.setSlidingEnabled(false);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "FragmentA-- onResume");
		menu.setSlidingEnabled(true);
	}

	@Override
	public void onItemClickListener(int parent, int child) {
		Log.i(TAG, "parent:" + parent + ",  child:" + child);
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("olid", parent);
		map.put("tlid", child);
		map.put("currPage", currPage);
		map.put("pageSize", PAGE_SIZE);
		new Thread(new Runnable() {
			@Override
			public void run() {
				String response = HttpAPI.getFoodsMenuJSON(map);
				Message message = new Message();
				message.what = 1;
				message.obj = response;
				mHandler.sendMessage(message);
			}
		}).start();
	}
}
