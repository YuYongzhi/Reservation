package com.reservation.act;

import com.reservation.R;
import com.reservation.fragment.MainContentFragment;
import com.reservation.fragment.MainLeftFragment;
import com.reservation.fragment.MainRightFragment;
import com.reservation.utils.ActivityUtils;
import com.reservation.view.FragmentListener;
import com.reservation.view.FragmentListener.OnMenuItemClickListener;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainFragmentActivity extends FragmentActivity implements FragmentListener.OnMenuItemClickListener{

	private final static String TAG = "MainFragmentActivity";
	private SlidingMenu menu;
	private OnMenuItemClickListener itemClickListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActivityUtils.addAct(MainFragmentActivity.this);
		
		setContentView(R.layout.main_fragment_activity_layout);

		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		menu.setFadeDegree(0.35f);
		menu.setBehindOffset((int)(dm.widthPixels * (1 - 0.7)));
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		
		menu.setMode(SlidingMenu.LEFT);
		
		menu.setContent(R.layout.slidingmenu_content);
		
		menu.setMenu(R.layout.slidingmenu_menu_left);
		menu.setShadowDrawable(R.drawable.slidingmenu_shadow_left);
		
//		menu.setSecondaryMenu(R.layout.slidingmenu_menu_right);
//		menu.setSecondaryShadowDrawable(R.drawable.slidingmenu_shadow_right);
		
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.slidingmenu_content, new MainContentFragment(menu))
			.commit();
		
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.slidingmenu_menu_left, new MainLeftFragment(menu))
			.commit();
	
//		getSupportFragmentManager()
//			.beginTransaction()
//			.replace(R.id.slidingmenu_menu_right, new MainRightFragment(menu))
//			.commit();
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		try {
			itemClickListener = (OnMenuItemClickListener) fragment;
		} catch (ClassCastException e) {
			Log.i(TAG, "ClassCastException:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void onItemClickListener(int parent, int child) {
		itemClickListener.onItemClickListener(parent, child);
	}

}
