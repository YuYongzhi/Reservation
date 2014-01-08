package com.reservation.fragment;

import com.reservation.R;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabBFragment extends Fragment {
	
	private static final String TAG = "Fragment";

	private SlidingMenu menu;
	
	
	public TabBFragment(SlidingMenu menu) {
		this.menu = menu;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "FragmentB-- onCreateView");
		return inflater.inflate(R.layout.tab_b_fragment_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "FragmentB-- onActivityCreated");
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(TAG, "FragmentB-- onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "FragmentB-- onCreate");
		menu.setSlidingEnabled(false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "FragmentB-- onDestroy");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.i(TAG, "FragmentB-- onDestroyView");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.i(TAG, "FragmentB-- onDetach");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "FragmentB-- onPause");
		menu.setSlidingEnabled(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "FragmentB-- onResume");
		menu.setSlidingEnabled(false);
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "FragmentB-- onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i(TAG, "FragmentB-- onStop");
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.i(TAG, "FragmentB-- onViewCreated");
	}
}
