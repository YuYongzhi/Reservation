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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MenuListFragment extends Fragment{
	
	private static final String TAG = "Fragment";

	private Context mContext;
	private SlidingMenu menu;
	private RelativeLayout mTitleLayout;
	private EditText mSearchEditText;
	
	private Animation mShowAnimation;
	private Animation mHiddenAnimation;
	
	
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
		View parent = getView();
		mTitleLayout = (RelativeLayout) parent.findViewById(R.id.menu_list_title_layout);
		mSearchEditText = (EditText) parent.findViewById(R.id.menu_list_search_edt);

		mShowAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, 
				Animation.RELATIVE_TO_SELF, 0.0f, 
				Animation.RELATIVE_TO_SELF, -1.0f, 
				Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAnimation.setDuration(500);
		mHiddenAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, 
				Animation.RELATIVE_TO_SELF, 0.0f, 
				Animation.RELATIVE_TO_SELF, 0.0f, 
				Animation.RELATIVE_TO_SELF, -1.0f);
		mHiddenAnimation.setDuration(3000);
		
		mSearchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				//mTitleLayout.startAnimation(mHiddenAnimation);
				//mTitleLayout.setVisibility(View.GONE);
				mSearchEditText.startAnimation(mHiddenAnimation);
			}
		});
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
