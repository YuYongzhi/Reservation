package com.reservation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reservation.R;
import com.reservation.adapter.XListViewAdapter;
import com.reservation.net.HttpAPI;
import com.reservation.pojo.Foods;
import com.reservation.utils.JSONUtils;
import com.reservation.utils.ReservationListener;
import com.reservation.utils.ReservationListener.OnExListViewItemClickListener;
import com.reservation.utils.ReservationListener.OnLeftRequestListener;
import com.reservation.utils.ReservationListener.OnListRequestListener;
import com.reservation.view.LoadingDialog;
import com.reservation.view.WaitLoadingWindow;
import com.reservation.view.XListView.IXListViewListener;
import com.reservation.view.XListView;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuListFragment extends Fragment 
	implements OnListRequestListener,
		IXListViewListener,
		OnExListViewItemClickListener{
	
	private static final String TAG = "MenuListFragment";

	private final static int MSG_FIRST_REQUEST = 0x01;
	private final static int MSG_UPDATE_LIST = 0x03;
	private final static int MSG_LOAD_FINISH = 0x04;
	private final static int MSG_NO_MORE = 0x05;
	private final static int MSG_NEW_LIST = 0x06;
	
	private final static int UPDATE_DELAY = 200;

	private Context mContext;
	private SlidingMenu menu;
	private View mParentView;
	private View mTitleView;
	private TextView mTitleTextView;
	private int parentId = 1;
	private int childId = -1;
	private int currPage = 1;
	private final static int PAGE_SIZE = 5;
	
	private Map<String, Object> mParams = new HashMap<String, Object>();
	private List<Foods> mFoodsList = new ArrayList<Foods>();
	private XListView mListView;
	private XListViewAdapter mAdapter;
	private LoadingDialog loadingDialog;
	
	private OnListRequestListener onListRequestListener;
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MSG_FIRST_REQUEST:
				final String foodFirstJSON = (String) msg.obj;
				new Thread(new Runnable() {
					@Override
					public void run() {
						Log.i(TAG, "foodJSON=" + foodFirstJSON);
						List<Foods> foods = JSONUtils.getFoodsList(foodFirstJSON);
						for(int i = 0; i < foods.size(); i ++) {
							Log.i(TAG, "mLoadFoodsThread  i=" + i);
							mFoodsList.add(foods.get(i));
							Message message = new Message();
							message.what = MSG_UPDATE_LIST;
							mHandler.sendMessage(message);
							try {
								Thread.sleep(UPDATE_DELAY);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						onListRequestListener.onRequestFinish();
					}
				}).start();
				break;
			case MSG_UPDATE_LIST:
				mAdapter.notifyDataSetChanged();
				mListView.smoothScrollToPosition(mListView.getCount() - 1);
				break;
			case MSG_LOAD_FINISH:
				onLoad();
				break;
			case MSG_NO_MORE:
				Toast.makeText(mContext, R.string.load_more_no_more, Toast.LENGTH_SHORT).show();
				break;
			case MSG_NEW_LIST:
				final String foodNewJSON = (String) msg.obj;
				new Thread(new Runnable() {
					@Override
					public void run() {
						Log.i(TAG, "foodJSON=" + foodNewJSON);
						List<Foods> foods = JSONUtils.getFoodsList(foodNewJSON);
						for(int i = 0; i < foods.size(); i ++) {
							Log.i(TAG, "mLoadFoodsThread  i=" + i);
							mFoodsList.add(foods.get(i));
							Message message = new Message();
							message.what = MSG_UPDATE_LIST;
							mHandler.sendMessage(message);
							try {
								Thread.sleep(UPDATE_DELAY);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						loadingDialog.dismiss();
					}
				}).start();
				break;
			}
		}
	};
		
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
		mParentView = getView();
		
		mTitleView = mParentView.findViewById(R.id.menu_list_title);
		mTitleTextView = (TextView) mTitleView.findViewById(R.id.title_textview);
		
		mListView = (XListView) mParentView.findViewById(R.id.foods_list);
		mListView.setPullLoadEnable(true);
		mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		
		mAdapter = new XListViewAdapter(mContext, mFoodsList, mListView);
		mListView.setAdapter(mAdapter);
		loadingDialog = new LoadingDialog(mContext);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(TAG, "FragmentA-- onAttach");
		try {
			onListRequestListener = (OnListRequestListener) activity;
		} catch (ClassCastException e) {
			Log.i(TAG, "ClassCastException:" + e.getMessage());
			e.printStackTrace();
		}
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
	public void onRequestStart(Object object) {
		currPage = 1;
		
		Map<String, Object> map = (HashMap<String, Object>) object;
		String title = (String) map.get("title");
		parentId = (Integer) map.get("parentId");
		childId = (Integer) map.get("childId");
		Toast.makeText(mContext, "Title:" + title + ", ParentId:" + parentId + ", ChildId=" + childId, Toast.LENGTH_SHORT).show();
		
		mTitleTextView.setText(title);
		mParams.put("olid", parentId);
		mParams.put("tlid", childId);
		mParams.put("currPage", currPage);
		mParams.put("pageSize", PAGE_SIZE);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				String foodJSON = HttpAPI.getFoodsMenuJSON(mParams);
				Log.i(TAG, "foodJSON=" + foodJSON);
				Message message = new Message();
				message.what = MSG_FIRST_REQUEST;
				message.obj = foodJSON;
				mHandler.sendMessage(message);
			}
		}).start();
	}
	
	@Override
	public void onRequestFinish() {
		
	}
	
	private void onLoad() {
		mListView.stopLoadMore();
	}
	
	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onLoadMore() {
		currPage ++;
		
		mParams.put("olid", parentId);
		mParams.put("tlid", childId);
		mParams.put("currPage", currPage);
		mParams.put("pageSize", PAGE_SIZE);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				String foodJSON = HttpAPI.getFoodsMenuJSON(mParams);
				Log.i(TAG, "foodJSON=" + foodJSON);
				List<Foods> foods = JSONUtils.getFoodsList(foodJSON);
				if (foods.size() == 0) {
					Message message = new Message();
					message.what = MSG_NO_MORE;
					mHandler.sendMessage(message);
				}
				for(int i = 0; i < foods.size(); i ++) {
					Log.i(TAG, "mLoadFoodsThread  i=" + i);
					mFoodsList.add(foods.get(i));
					Message message = new Message();
					message.what = MSG_UPDATE_LIST;
					mHandler.sendMessage(message);
					try {
						Thread.sleep(UPDATE_DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Message message = new Message();
				message.what = MSG_LOAD_FINISH;
				mHandler.sendMessage(message);
			}
		}).start();
	}

	@Override
	public void onItemClick(Object object) {
		currPage = 1;
		
		Map<String, Object> map = (HashMap<String, Object>) object;
		String title = (String) map.get("title");
		parentId = (Integer) map.get("parentId");
		childId = (Integer) map.get("childId");
		Toast.makeText(mContext, "Title:" + title + ", ParentId:" + parentId + ", ChildId=" + childId, Toast.LENGTH_SHORT).show();
		
		mTitleTextView.setText(title);
		mParams.put("olid", parentId);
		mParams.put("tlid", childId);
		mParams.put("currPage", currPage);
		mParams.put("pageSize", PAGE_SIZE);
		
		mFoodsList.clear();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String foodJSON = HttpAPI.getFoodsMenuJSON(mParams);
				Log.i(TAG, "foodJSON=" + foodJSON);
				Message message = new Message();
				message.what = MSG_NEW_LIST;
				message.obj = foodJSON;
				mHandler.sendMessage(message);
			}
		}).start();
		loadingDialog.show();
	}
}
