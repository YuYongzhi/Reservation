package com.reservation.view;

import com.reservation.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XListViewFooter extends LinearLayout{

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	
	private Context mContext;
	
	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;
	
	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	
	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView) moreView.findViewById(R.id.xlistview_footer_hint_textview);
	}
	
	public void setState(int state) {
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
		mHintView.setVisibility(View.INVISIBLE);
		if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_ready);
		} else if (state == STATE_LOADING) {
			mProgressBar.setVisibility(View.VISIBLE);
		} else {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_normal);
		}
	}
	
	public void setBottomMargin(int height) {
		if (height < 0) {
			return;
		}
		LinearLayout.LayoutParams params = 
				(LayoutParams) mContentView.getLayoutParams();
		params.bottomMargin = height;
		mContentView.setLayoutParams(params);
	}
	
	public int getBottomMargin() {
		LinearLayout.LayoutParams params = (LayoutParams) mContentView.getLayoutParams();
		return params.bottomMargin;
	}
	
	/**
	 * normal state
	 * 
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}
	
	/**
	 * loading state
	 * 
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public void loading() {
		LinearLayout.LayoutParams params = 
				(LayoutParams) mContentView.getLayoutParams();
		params.height = 0;
		mContentView.setLayoutParams(params);
	}
	
	/**
	 * hide footer when disable pull load more
	 * 
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public void hide() {
		LinearLayout.LayoutParams params = 
				(LayoutParams) mContentView.getLayoutParams();
		params.height = 0;
		mContentView.setLayoutParams(params);
	}
	
	/**
	 * show footer
	 * 
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public void show() {
		LinearLayout.LayoutParams params = 
				(LayoutParams) mContentView.getLayoutParams();
		params.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(params);
	}
}
