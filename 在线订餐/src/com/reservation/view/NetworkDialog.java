package com.reservation.view;

import com.reservation.R;
import com.reservation.utils.ActivityUtils;
import com.reservation.utils.ReservationListener.OnNetworkDialogClickListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

public class NetworkDialog extends Dialog {

	private boolean isShow = false;
	private Context context;
	private Button mPositiveBtn;
	private Button mNegativeBtn;
	private OnNetworkDialogClickListener mListener;
	
	public NetworkDialog(Context context, OnNetworkDialogClickListener listener) {
		super(context, R.style.DialogTheme);
		this.context = context;
		this.mListener = listener;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setCancelable(false);
		this.setCancelable(false);
		this.setContentView(R.layout.network_dialog_content_layout);
		mPositiveBtn = (Button) findViewById(R.id.network_dialog_positive_button);
		mNegativeBtn = (Button) findViewById(R.id.network_dialog_negative_button);
		
		mPositiveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				context.startActivity(new Intent(Settings.ACTION_SETTINGS));
//				dismiss();
				mListener.setPositiveButton(v);
			}
		});
		
		mNegativeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				ActivityUtils.finishAll();
				mListener.setNegativeButton(v);
			}
		});
		
	}

	@Override
	public void show() {
		super.show();
		isShow = true;
	}
	
	@Override
	public void dismiss() {
		super.dismiss();
		isShow = false;
	}
	
	public boolean isShow() {
		return this.isShow;
	}
}
