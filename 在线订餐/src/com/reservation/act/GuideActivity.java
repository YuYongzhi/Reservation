package com.reservation.act;

import java.util.ArrayList;

import com.reservation.R;
import com.reservation.adapter.ViewPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import static com.reservation.utils.StringUtils.*;

public class GuideActivity extends Activity{

	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	private ViewPager mPager;
	private ViewPagerAdapter mAdapter;
	
	private ArrayList<View> views;
	
	private ImageView[] points;
	
	private int currentIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initFullScreen();
		setContentView(R.layout.activity_guide_layout);
		
		initView();
		initData();
	}
	
	private void initFullScreen() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFormat(PixelFormat.TRANSPARENT);
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
	}
	
	private void initView() {
		views = new ArrayList<View>();
		mPager = (ViewPager) findViewById(R.id.guide_view_pager);
		mAdapter = new ViewPagerAdapter(views);
	}
	
	private void initData() {
		LayoutInflater mInflater = LayoutInflater.from(GuideActivity.this);
		View view1 = mInflater.inflate(R.layout.guide_layout_1, null);
		View view2 = mInflater.inflate(R.layout.guide_layout_2, null);
		View view3 = mInflater.inflate(R.layout.guide_layout_3, null);
		View view4 = mInflater.inflate(R.layout.guide_layout_4, null);
		View view5 = mInflater.inflate(R.layout.guide_layout_5, null);
		
		final CheckBox checkBox = (CheckBox) view5.findViewById(R.id.guide_notice_checkbox);
		Button button = (Button) view5.findViewById(R.id.guide_notice_enter_btn);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isFirstGuide = checkBox.isChecked();
				SharedPreferences preferences = getSharedPreferences(PREFS_GUIDE_STRING, MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putBoolean(PrefsGuideItem.IS_FIRST_GUIDE, !isFirstGuide);
				editor.commit();
				Intent intent = new Intent();
				intent.setClass(GuideActivity.this, MainFragmentActivity.class);
				startActivity(intent);
				GuideActivity.this.finish();
			}
		});
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		views.add(view5);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(new OnViewPagerChangeListener());
		initPoints();
	}
	
	private void initPoints() {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.guide_viewpager_points);
		LinearLayout.LayoutParams mLayoutParams = 
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT, 
						LinearLayout.LayoutParams.WRAP_CONTENT);
		mLayoutParams.setMargins(10, 10, 10, 10);
		points = new ImageView[views.size()];
		
		for(int i = 0; i < views.size(); i ++) {
			points[i] = new ImageView(GuideActivity.this);
			points[i].setLayoutParams(mLayoutParams);
			points[i].setImageResource(R.drawable.guide_points_selector);
			points[i].setEnabled(true);
			points[i].setOnClickListener(new OnPointsClickListener());
			points[i].setTag(i);
			linearLayout.addView(points[i]);
		}
		
		currentIndex = 0;
		points[currentIndex].setEnabled(false);
	}
	
	class OnViewPagerChangeListener implements ViewPager.OnPageChangeListener {

		/**
		 * ����״̬�ı�ʱ����
		 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
		 * 
		 * @author 2014-1-2_Evan.Yu
		 */
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		/**
		 * ��ǰ���汻����ʱ����
		 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
		 * 
		 * @author 2014-1-2_Evan.Yu
		 */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		/**
		 * ���µ�ҳ�汻ѡ��ʱ����
		 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
		 * 
		 * @author 2014-1-2_Evan.Yu
		 */
		@Override
		public void onPageSelected(int position) {
			setCurrentDot(position);
		}
	}
	
	class OnPointsClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			setCurrentView(position);
			setCurrentDot(position);
		}
	}
	
	private void setCurrentDot(int position) {
		if (position < 0 || position > (views.size() - 1) || currentIndex == position) {
			return ;
		}
		points[position].setEnabled(false);
		points[currentIndex].setEnabled(true);
		currentIndex = position;
	}
	
	private void setCurrentView(int position) {
		if (position < 0 || position > views.size()) {
			return ;
		}
		mPager.setCurrentItem(position);
	}
}
