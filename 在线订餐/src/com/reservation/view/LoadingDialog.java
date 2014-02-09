package com.reservation.view;

import com.reservation.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

public class LoadingDialog extends ProgressDialog {

	public LoadingDialog(Context context) {
		super(context, R.style.LoadingDialogTheme);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_dialog_layout);
		
		this.setCancelable(false);
	}

}
