package com.reservation.fragment;

import com.reservation.R;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabAFragment extends Fragment{
	
	private static final String TAG = "Fragment";

	private Context mContext;
	private SlidingMenu menu;
	
	public TabAFragment(SlidingMenu menu) {
		this.menu = menu;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "FragmentA-- onCreateView");
		return inflater.inflate(R.layout.tab_a_fragment_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "FragmentA-- onActivityCreated");
	
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
	public void onDestroyView() {
		super.onDestroyView();
		Log.i(TAG, "FragmentA-- onDestroyView");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.i(TAG, "FragmentA-- onDetach");
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
	public void onStart() {
		super.onStart();
		Log.i(TAG, "FragmentA-- onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i(TAG, "FragmentA-- onStop");
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.i(TAG, "FragmentA-- onViewCreated");
	}
}
