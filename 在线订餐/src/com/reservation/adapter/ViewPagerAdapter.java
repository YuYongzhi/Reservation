package com.reservation.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter{

	private ArrayList<View> views;
	
	public ViewPagerAdapter(ArrayList<View> views) {
		this.views = views;
	}
	
	/**
	 * 返回页卡的数量
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 * 
	 * @author 2013-12-27_Evan.Yu
	 */
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	/**
	 * 判断是否由对象生成的界面
	 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View, java.lang.Object)
	 * 
	 * @author 2013-12-27_Evan.Yu
	 */
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == object);
	}

	/**
	 * 删除页卡
	 * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.View, int, java.lang.Object)
	 * 
	 * @author 2013-12-27_Evan.Yu
	 */
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager)container).removeView(views.get(position));
	}
	
	/**
	 * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
	 * 
	 * @author 2013-12-27_Evan.Yu
	 */
	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}
	
	/**
	 * 初始化position位置的界面
	 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.View, int)
	 * 
	 * @author 2013-12-27_Evan.Yu
	 */
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager)container).addView(views.get(position));
		return views.get(position);
	}
}