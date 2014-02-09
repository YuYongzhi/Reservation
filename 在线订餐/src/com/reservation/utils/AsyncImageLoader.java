package com.reservation.utils;

import com.reservation.net.HttpAPI;
import com.reservation.utils.ReservationListener.ImageLoadCallback;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {

	public AsyncImageLoader() {
		
	}
	
	public void loadDrawable(final String imgUrl, final ImageLoadCallback callback) {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				callback.imageLoaded((Bitmap)msg.obj, imgUrl);
			}
		};
		
		new Thread() {
			@Override
			public void run() {
				super.run();
				Bitmap bitmap = HttpAPI.getNetBitmap(imgUrl);
				Message message = handler.obtainMessage(0, bitmap);
				handler.sendMessage(message);
			}
		}.start();
	}
}
