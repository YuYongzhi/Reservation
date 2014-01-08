package com.reservation.act;

import com.reservation.R;
import com.reservation.fragment.MainContentFragment;
import com.reservation.fragment.MainLeftFragment;
import com.reservation.fragment.MainRightFragment;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;

public class MainFragmentActivity extends FragmentActivity{

	private SlidingMenu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			.replace(R.id.slidingmenu_menu_left, new MainLeftFragment())
			.commit();
	
//		getSupportFragmentManager()
//			.beginTransaction()
//			.replace(R.id.slidingmenu_menu_right, new MainRightFragment(menu))
//			.commit();
	}
}
