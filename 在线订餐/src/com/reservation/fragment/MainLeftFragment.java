package com.reservation.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.reservation.R;
import com.reservation.adapter.ExpandableListViewAdapter;

import static com.reservation.utils.JSONUtils.*;

import com.reservation.utils.StringUtils.Manager;
import com.reservation.view.FragmentListener;
import com.reservation.view.FragmentListener.OnMenuItemClickListener;
import com.reservation.view.slidingmenu.buildin.BaseFragment;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


public class MainLeftFragment extends BaseFragment {

	private List<Map<String, Object>> parentList = new ArrayList<Map<String,Object>>();
	private List<List<Map<String, Object>>> childList = new ArrayList<List<Map<String,Object>>>();
	
	private ExpandableListView mExListView;
	private ExpandableListViewAdapter mExAdapter;
	
	private LeftFragmentReceiver mReceiver;
	private SlidingMenu menu;
	private Context mContext;
	private OnMenuItemClickListener itemClickListener;
	
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
		registerReceiver();
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
					itemClickListener.onItemClickListener(parentId, -1);
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
				itemClickListener.onItemClickListener(parentId, childId);
				menu.toggle();
				return false;
			}
		});
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			itemClickListener = (OnMenuItemClickListener) activity;
		} catch (ClassCastException e) {
			Log.i(TAG, "ClassCastException:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver();
	}

	public void registerReceiver() {
		mReceiver = new LeftFragmentReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Manager.LEFT_FRAGMENT_ACTION);
		getActivity().registerReceiver(mReceiver, filter);
	}
	
	public void unregisterReceiver() {
		if (mReceiver != null) {
			getActivity().unregisterReceiver(mReceiver);
		}
	}
	
	public class LeftFragmentReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.i(TAG, "Action:" + action);
			if (Manager.LEFT_FRAGMENT_ACTION.equals(action)) {
				String parentJSON = intent.getStringExtra("oneJSON");
				String childJSON = intent.getStringExtra("twoJSON");
				Log.i(TAG, "parentJSON=" + parentJSON);
				Log.i(TAG, "childJSON=" + childJSON);
				parentList = getParentList(parentJSON); 
				childList = getChildList(parentJSON, childJSON);
				mExAdapter = new ExpandableListViewAdapter(context, parentList, childList);
				mExListView.setAdapter(mExAdapter);
			}
		}
	}
}
