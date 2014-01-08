package com.reservation.fragment;

import com.reservation.R;
import com.reservation.view.slidingmenu.buildin.BaseFragment;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainRightFragment extends BaseFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_right_fragment_layout, null);
	}
	
	private SlidingMenu menu;
	
	public MainRightFragment(SlidingMenu menu) {
		this.menu = menu;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
	}
	
	private void initViews() {
		View parent = this.getView();
	}
}
