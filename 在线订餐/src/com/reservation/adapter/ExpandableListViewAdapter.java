package com.reservation.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.reservation.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private List<Map<String, Object>> parentList = new ArrayList<Map<String,Object>>();
	private List<List<Map<String, Object>>> childList = new ArrayList<List<Map<String,Object>>>();
	
	public ExpandableListViewAdapter(Context context, List<Map<String, Object>> parentList, List<List<Map<String, Object>>> childList) {
		this.mContext = context;
		this.parentList = parentList;
		this.childList = childList;
		
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getGroupCount() {
		return parentList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return parentList.get(groupPosition).get("parent");
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition).get("child");
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.left_content_expandablelist_parent_item, null);
			AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
			layoutParams.height = 100; 
			convertView.setLayoutParams(layoutParams);
			holder = new GroupHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.left_content_expandablelistview_parent_item_icon);
			holder.textView = (TextView) convertView.findViewById(R.id.left_content_expandablelistview_parent_item_text);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}
		
		holder.textView.setText(parentList.get(groupPosition).get("parent").toString());
		if (getChildrenCount(groupPosition) > 0) {
			if (isExpanded) {
				holder.imageView.setImageResource(R.drawable.down_arrow_expanded);
			} else {
				holder.imageView.setImageResource(R.drawable.right_arrow_collapse);
			}
		} else {
			holder.imageView.setImageResource(R.drawable.empty_arrow);
		}
		return convertView;
	}
 
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.left_content_expandablelist_child_item, null);
			AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
			layoutParams.height = 90; 
			convertView.setLayoutParams(layoutParams);
			holder = new ChildHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.left_content_expandablelistview_child_item_icon);
			holder.textView = (TextView) convertView.findViewById(R.id.left_content_expandablelistview_child_item_text);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		
		holder.imageView.setImageResource(R.drawable.empty_arrow);
		holder.textView.setText(childList.get(groupPosition).get(childPosition).get("child").toString());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	class GroupHolder {
		ImageView imageView;
		TextView textView;
	}
	
	class ChildHolder {
		ImageView imageView;
		TextView textView;
	}
}
