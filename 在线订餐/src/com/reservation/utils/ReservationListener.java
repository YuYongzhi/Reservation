package com.reservation.utils;

import android.graphics.Bitmap;
import android.view.View;

public class ReservationListener {

	public interface OnExListViewItemClickListener {
		public void onItemClick(Object object);
	}
	
	public interface OnLeftRequestListener {
		public void onRequestStart();
		public void onRequestFinish(Object obj);
	}
	
	public interface OnListRequestListener {
		public void onRequestStart(Object object);
		public void onRequestFinish();
	}

	public interface OnNetworkDialogClickListener {
		public void setPositiveButton(View view);
		public void setNegativeButton(View view);
	}
	
	public interface ImageLoadCallback {
		public void imageLoaded(Bitmap bitmap, String imgUrl);
	}
}
