package com.reservation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reservation.R;
import com.reservation.adapter.ExpandableListViewAdapter;
import com.reservation.view.slidingmenu.buildin.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


public class MainLeftFragment extends BaseFragment {

	private List<Map<String, Object>> parentList = new ArrayList<Map<String,Object>>();
	private List<List<Map<String, Object>>> childList = new ArrayList<List<Map<String,Object>>>();
	
	private ExpandableListView mExListView;
	private ExpandableListViewAdapter mExAdapter;
	
	private String[] parentStrs = new String[]{
			"一组",
			"二组",
			"三组",
			"四组",
			"五组"
	};
	
	private String[][] childStrs = new String[][]{
			{"1-1", "1-2", "1-3"},
			{"2-1", "2-2", "2-3", "2-4"},
			{"3-1", "3-2", "3-3", "3-4", "3-5"},
			{"4-1", "4-2", "4-3", "4-4"},
			{"5-1", "5-5", "5-6", "5-7", "5-8", "5-9", "5-10"}
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_left_fragment_layout, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.initViews();
	}
	
	private void initViews() {
		View parent = this.getView();
		
		parentList = getParentData();
		childList = getChildData();
		
		mExListView = (ExpandableListView) parent.findViewById(R.id.left_content_expandablelistview);
		mExAdapter = new ExpandableListViewAdapter(context, parentList, childList);
		mExListView.setAdapter(mExAdapter);
	}
	
	public List<Map<String, Object>> getParentData() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < parentStrs.length; i ++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parent", parentStrs[i]);
			list.add(map);
		}
		return list;
	}
	
	public List<List<Map<String, Object>>> getChildData() {
		List<List<Map<String, Object>>> list = new ArrayList<List<Map<String,Object>>>();
		int parentLength = parentStrs.length;
		int childLength = childStrs.length;
		if (parentLength != childLength) {
			return list;
		}
		for(int parent = 0; parent < parentLength; parent ++) {
			List<Map<String, Object>> children = new ArrayList<Map<String,Object>>();
			for(int child = 0; child < childStrs[parent].length; child ++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("child", childStrs[parent][child]);
				children.add(map);
			}
			if (parent == 2) {
				children.clear();
			}
			list.add(children);
		}
		return list;
	}
}
