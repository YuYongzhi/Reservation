package com.reservation.fragment;

import java.util.ArrayList;
import java.util.List;

import com.reservation.R;
import com.reservation.adapter.FragmentTabAdapter;
import com.reservation.adapter.FragmentTabAdapter.OnRgsCheckedChangedListener;
import com.reservation.view.slidingmenu.buildin.BaseFragment;
import com.reservation.view.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainContentFragment extends BaseFragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup contain, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_content_fragment_layout, null);
	}
	
	private SlidingMenu menu;
	private RadioGroup rgs;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	
	public MainContentFragment(SlidingMenu menu) {
		this.menu = menu;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.initViews();
	}
	
	private void initViews() {
		View parent = this.getView();
		fragments.add(new TabAFragment(menu));
		fragments.add(new TabBFragment(menu));
		fragments.add(new TabCFragment(menu));
		
		rgs = (RadioGroup) parent.findViewById(R.id.tab_rbtn_group);
		
		FragmentTabAdapter tabAdapter = 
				new FragmentTabAdapter(getActivity(), fragments, R.id.tab_content, rgs);
		tabAdapter.setOnRgsCheckedChangedListener(new OnRgsCheckedChangedListener(){
			@Override
			public void OnRgsCheckedChanged(RadioGroup radioGroup,
					int checkdId, int index) {
//				super.OnRgsExtraCheckedChanged(radioGroup, checkdId, index);
				Toast.makeText(getActivity(), "You clicked. , Index=" + index, Toast.LENGTH_LONG).show();
			}
			
		});
	}
}
