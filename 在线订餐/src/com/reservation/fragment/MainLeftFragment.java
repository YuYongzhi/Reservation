package com.reservation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reservation.R;
import com.reservation.adapter.ExpandableListViewAdapter;

import static com.reservation.utils.JSONUtils.*;

import com.reservation.net.HttpAPI;
import com.reservation.utils.ReservationListener;
import com.reservation.utils.ReservationListener.OnExListViewItemClickListener;
import com.reservation.utils.ReservationListener.OnLeftRequestListener;
import com.reservation.utils.StringUtils.Manager;
//import com.reservation.view.ReservationListener.OnMenuItemClickListener;
import com.reservation.view.slidingmenu.buildin.BaseFragment;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;


public class MainLeftFragment extends BaseFragment implements OnLeftRequestListener{

	private final static String TAG = "MainLeftFragment";
	
	private final static int MSG_PARENT = 0x01;
	private final static int MSG_CHILD = 0x02;
	private final static int MSG_ALL = 0x03;
	private final static int MSG_ERROR = 0x04;
	
	private List<Map<String, Object>> parentList = new ArrayList<Map<String,Object>>();
	private List<List<Map<String, Object>>> childList = new ArrayList<List<Map<String,Object>>>();
	
	private ExpandableListView mExListView;
	private ExpandableListViewAdapter mExAdapter;
	
//	private LeftFragmentReceiver mReceiver;
	private SlidingMenu menu;
	private Context mContext;
	private LeftHandler mHandler;
	
	private String parentJSON = null;
	private String childJSON = null;
	private boolean isParentGet = false;
	private boolean isChildGet = false;
	
	private OnLeftRequestListener onRequestListener;
	private OnExListViewItemClickListener itemClickListener;
	
	
	
	public MainLeftFragment(SlidingMenu menu) {
		this.menu = menu;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_left_fragment_layout, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
//		registerReceiver();
		this.initViews();
	}
	
	private void initViews() {
		View parent = this.getView();
		mExListView = (ExpandableListView) parent.findViewById(R.id.left_content_expandablelistview);
		mExListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				int parentId = (Integer) parentList.get(groupPosition).get("parentId");
				int childCnt = childList.get(groupPosition).size();
				if (childCnt == 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("parentId", parentId);
					map.put("childId", parentId);
					map.put("title", parentList.get(groupPosition).get("parent"));
					itemClickListener.onItemClick(map);
					menu.toggle();
				}
				return false;
			}
		});
		mExListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				int parentId = (Integer) parentList.get(groupPosition).get("parentId");
				int childId = (Integer) childList.get(groupPosition).get(childPosition).get("childId");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("parentId", parentId);
				map.put("childId", childId);
				map.put("title", childList.get(groupPosition).get(childPosition).get("child"));
				itemClickListener.onItemClick(map);
				menu.toggle();
				return false;
			}
		});
		
		mHandler = new LeftHandler();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			onRequestListener = (OnLeftRequestListener) activity;
		} catch (ClassCastException e) {
			Log.i(TAG, "ClassCastException:" + e.getMessage());
			e.printStackTrace();
		}
		try {
			itemClickListener = (OnExListViewItemClickListener) activity;
		} catch (ClassCastException e) {
			Log.i(TAG, "ClassCastException:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public class LeftHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_PARENT:
				Log.i(TAG, "MSG_PARENT");
				parentJSON = (String) msg.obj;
				isParentGet = true;
				if (isParentGet && isChildGet) {
					Message message = new Message();
					message.what = MSG_ALL;
					mHandler.sendMessage(message);
				}
				break;
			case MSG_CHILD:
				Log.i(TAG, "MSG_CHILD");
				childJSON = (String) msg.obj;
				isChildGet = true;
				if (isParentGet && isChildGet) {
					Message message = new Message();
					message.what = MSG_ALL;
					mHandler.sendMessage(message);
				}
				break;
			case MSG_ALL:
				Log.i(TAG, "MSG_ALL");
				parentList = getParentList(parentJSON); 
				childList = getChildList(parentJSON, childJSON);
				mExAdapter = new ExpandableListViewAdapter(context, parentList, childList);
				mExListView.setAdapter(mExAdapter);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("parentId", parentList.get(0).get("parentId"));
				map.put("title", parentList.get(0).get("parent"));
				map.put("childId", -1);
				onRequestListener.onRequestFinish(map);
				
				break;

			default:
				break;
			}
		}
	}
	
	public void getDataRequest() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String parentJSON = HttpAPI.getOneLevelTypeJSON();
				Message message = new Message();
				message.what = MSG_PARENT;
				message.obj = parentJSON;
				mHandler.sendMessage(message);
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				String childJSON = HttpAPI.getTwoLevelTypeJSON();
				Message message = new Message();
				message.what = MSG_CHILD;
				message.obj = childJSON;
				mHandler.sendMessage(message);
			}
		}).start();
	}

	@Override
	public void onRequestStart() {
		Log.i(TAG, "onRequestStart");
		getDataRequest();
	}

	@Override
	public void onRequestFinish(Object obj) {
		
	}
}
