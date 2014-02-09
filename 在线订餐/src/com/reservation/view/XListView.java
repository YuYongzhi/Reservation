/**
 * @file XListView.java
 * @package com.example.xlistview
 * @description An ListView support 
 * 		(a) Pull down to refresh ,
 * 		(b) Pull up to load more.
 * 			Implement IXLISTListener , and see stopRefresh()/stopLoadMore().
 */

package com.reservation.view;

import com.reservation.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class XListView extends ListView implements OnScrollListener{

	
	//save event Y
	private float mLastY = -1;
	
	//used for scroll back
	private Scroller mScroller;
	
	//user's scroll listener
	private OnScrollListener mScrollListener;
	
	//the interface to trigger refresh and load more
	private IXListViewListener mListViewListener;
	
	//--header view
	private XListViewHeader mHeaderView;
	
	//header view content, use it to calculate the Header's height.
	//And hide it when disable pull refresh.
	private RelativeLayout mHeaderViewContent;
	
	private TextView mHeaderTimeView;
	
	//header view's height
	private int mHeaderViewHeight;
	
	private boolean mEnablePullRefresh = true;
	
	//is refreshing
	private boolean mPullRefreshing = false;
	
	//--footer view
	private XListViewFooter mFooterView;
	private boolean mEnablePullLoad;
	private boolean mPullLoading;
	private boolean mIsFooterReady = false;
	
	//total list items, used to detect is at the bottom of listview
	private int mTotalItemCount;
	
	//for mScroll, scroll back from header or footer
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;
	
	//scroll back duration
	private final static int SCROLL_DURATION = 400;
	
	//when pull up >= 50px
	//at bottom, trigger
	//load more
	private final static int PULL_LOAD_MORE_DELTA = 50;
	
	//support IOS like pull
	//feature
	private final static float OFFSET_RADIO = 1.8f;
	
	
	public XListView(Context context) {
		super(context);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}
	
	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		
		//XListView need the scroll event, and it will dispatch the event to
		//user's listener(as a proxy)
		super.setOnScrollListener(this);
		
		//init header view
		mHeaderView = new XListViewHeader(context);
		mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
		mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
		addHeaderView(mHeaderView);
		
		//init footer view 
		mFooterView = new XListViewFooter(context);
		
		//init header height
		mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				mHeaderViewHeight = mHeaderViewContent.getHeight();
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		//make sure XListViewFooter is the last footer view, and only add once.
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}
	
	/**
	 * enable or disable pull down refresh feature
	 * 
	 * @param enable
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		
		//disable, hide the content
		if (!mEnablePullRefresh) {
			mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderViewContent.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * enable or disable pull up load more feature
	 * 
	 * @param enable
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
			
			//both "pull up" and "click" will invoke load more
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}
	
	/**
	 * stop refresh, reset header view.
	 * 
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
	}
	
	/**
	 * stop load more, reset footer view.
	 * 
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
		}
	}
	
	/**
	 * set last refresh time.
	 * 
	 * @param time
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public void setRefreshTime(String time) {
		mHeaderTimeView.setText(time);
	}
	
	private void invokeOnScrolling() {
		if (mScrollListener instanceof OnXScrollListener) {
			OnXScrollListener listener = (OnXScrollListener) mScrollListener;
			listener.onXScrolling(this);
		}
	}
	
	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
		
		//未处于刷新状态，更新箭头
		if (mEnablePullRefresh && !mPullRefreshing) {
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(XListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(XListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0);
	}
	
	/**
	 * reset header view's height
	 * 
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		
		//not visible
		if (height == 0) {
			return;
		}
		
		//refreshing and header isn't shown fully. do nothing
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		
		//default: scroll back to dismiss header
		int finalHeight = 0;
		
		//is refreshing, just scroll back to show all the header
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
		
		//trigger computeScroll
		invalidate();
	}
	
	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int)delta;
		if (mEnablePullLoad && !mPullLoading) {
			//height enough to invoke load more
			if (height > PULL_LOAD_MORE_DELTA) {
				mFooterView.setState(XListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);
		
		//scroll to bottom
//		setSelection(mTotalItemCount - 1);
	}
	
	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
			invalidate();
		}
	}
	
	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(XListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mLastY == -1) {
			mLastY = event.getRawY();
		}
		
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		{
			mLastY = event.getRawY();
			break;
		}
		case MotionEvent.ACTION_MOVE:
		{
			final float deltaY = event.getRawY() - mLastY;
			mLastY = event.getRawY();
			if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
				// the first item is showing, header has shown or pull down.
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				invokeOnScrolling();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1 && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
				//last item, already up or want to pull up.
				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}
			break;
		}
		default :
		{
			//reset
			mLastY = -1;
			if (getFirstVisiblePosition() == 0) {
				//invoke refresh 
				if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
					mPullRefreshing = true;
					mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						mListViewListener.onRefresh();
					}
				}
				resetHeaderHeight();
			} 
			if (getLastVisiblePosition() == mTotalItemCount - 1) {
				//invoke load more.
				if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
		}
		super.computeScroll();
	}
	
	@Override
	public void setOnScrollListener(OnScrollListener listener) {
		mScrollListener = listener;
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		//send to user's listener
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}
	
	public void setXListViewListener(IXListViewListener listener) {
		mListViewListener = listener;
	}
	
	/**
	 * you can listen ListView.OnScrollIstener or this one. it will invoke 
	 * onXScrolling when header/footer scroll back
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}
	
	
	/**
	 * implements this interface to get refresh/load more event
	 * 
	 * 
	 * @author 2013-12-4_Evan.Yu
	 */
	public interface IXListViewListener {
		
		public void onRefresh();
		
		public void onLoadMore();
	}

}
