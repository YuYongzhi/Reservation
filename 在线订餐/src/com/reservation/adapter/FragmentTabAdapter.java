package com.reservation.adapter;

import java.util.List;

import com.reservation.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

public class FragmentTabAdapter implements RadioGroup.OnCheckedChangeListener {

	private List<Fragment> fragments;
	private RadioGroup rgs;
	private FragmentActivity fragmentActivity;
	private int fragmentContentId;
	
	private int currentTab;
	
	private OnRgsCheckedChangedListener onRgsCheckedChangedListener;
	
	public FragmentTabAdapter(FragmentActivity fragmentActivity, List<Fragment> fragments, int fragmentContentId, RadioGroup rgs) {
		this.fragmentActivity = fragmentActivity;
		this.fragments = fragments;
		this.fragmentContentId = fragmentContentId;
		this.rgs = rgs;
		
		FragmentTransaction transaction = this.fragmentActivity.getSupportFragmentManager().beginTransaction();
		transaction.add(this.fragmentContentId, fragments.get(0));
		transaction.commit();
		
		this.rgs.setOnCheckedChangeListener(this);
	}
	
	private void showTab(int index) {
		for(int i = 0; i < fragments.size(); i ++) {
			Fragment fragment = fragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(index);
			if (index == i) {
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			ft.commit();
		}
		currentTab = index;
	}
	
	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = this.fragmentActivity.getSupportFragmentManager().beginTransaction();
		if (index > currentTab) {
			ft.setCustomAnimations(R.anim.tab_slide_left_in, R.anim.tab_slide_left_out);
		} else {
			ft.setCustomAnimations(R.anim.tab_slide_right_in, R.anim.tab_slide_right_out);
		}
		return ft;
	}
	
	public int getCurrentTab() {
		return currentTab;
	}
	
	public Fragment getCurrentFragment() {
		return this.fragments.get(currentTab);
	}
	
	public OnRgsCheckedChangedListener getOnRgsCheckedChangedListener() {
		return onRgsCheckedChangedListener;
	}
	
	public void setOnRgsCheckedChangedListener(OnRgsCheckedChangedListener onRgsCheckedChangedListener) {
		this.onRgsCheckedChangedListener = onRgsCheckedChangedListener;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		for(int i = 0; i < rgs.getChildCount(); i ++) {
			if (rgs.getChildAt(i).getId() == checkedId) {
				Fragment fragment = this.fragments.get(i);
				FragmentTransaction ft = obtainFragmentTransaction(i);
				
				getCurrentFragment().onPause();
				if (fragment.isAdded()) {
					fragment.onResume();
				} else {
					ft.add(fragmentContentId, fragment);
				}
				showTab(i);
				ft.commit();
				
				if (null != onRgsCheckedChangedListener) {
					onRgsCheckedChangedListener.OnRgsCheckedChanged(group, checkedId, i);
				}
			}
		}
	}
	
	public static class OnRgsCheckedChangedListener {
		public void OnRgsCheckedChanged(RadioGroup radioGroup, int checkdId, int index){};
	}
}
