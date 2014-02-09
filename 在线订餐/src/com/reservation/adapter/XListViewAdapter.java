package com.reservation.adapter;

import java.util.ArrayList;
import java.util.List;

import com.reservation.R;
import com.reservation.pojo.Foods;
import com.reservation.utils.AsyncImageLoader;
import com.reservation.utils.ToolUtils;
import com.reservation.utils.ReservationListener.ImageLoadCallback;
import com.reservation.view.XListView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class XListViewAdapter extends BaseAdapter{

	private final static String TAG = "XListViewAdapter";
	private Context mContext;
	private List<Foods> items = new ArrayList<Foods>();
	private LayoutInflater mInflater;
	private AsyncImageLoader imageLoader;
	private XListView listView;
	
	public XListViewAdapter(Context context, List<Foods> items, XListView listView) {
		this.mContext = context;
		this.items = items;
		mInflater = LayoutInflater.from(mContext);
		imageLoader = new AsyncImageLoader();
		this.listView = listView;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.i(TAG, "position=" + position + ",  getView");
		String imgUrl = items.get(position).getfImage();
		// A ViewHolder keeps references to children views to avoid unneccessary calls
		// to findViewById() on each row.
		final ViewHolder holder;
		
		//When convertView is not null, we can reuse it directly, there is no need
		// to re inflate it. We only inflate a new View when the convertView supplied 
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.xlistview_list_item, null);
			
			// Creates a ViewHolder and store references to the children views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.xlist_image);
			holder.title = (TextView) convertView.findViewById(R.id.xlistview_title);
			holder.text = (TextView) convertView.findViewById(R.id.xlistview_text);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView 
			// and the ImageView
			holder = (ViewHolder) convertView.getTag();
		}

		holder.image.setTag(imgUrl);
		imageLoader.loadDrawable(imgUrl, new ImageLoadCallback() {
			@Override
			public void imageLoaded(Bitmap bitmap, String imgUrl) {
				ImageView imageView = (ImageView) listView.findViewWithTag(imgUrl);
				if (imageView != null) {
					if (bitmap != null) {
						bitmap = ToolUtils.getRoundBitmap(mContext, bitmap, 20);
						imageView.setImageBitmap(bitmap);
					} else {
						imageView.setImageResource(R.drawable.default_list_icon);
					}
					Log.i(TAG, "Position=" + position + "," + "imageLoaded");
				}
			}
		});
		
		holder.title.setText(items.get(position).getfName());
		holder.text.setText(items.get(position).getfImage());
		return convertView;
	}
	
	public static class ViewHolder {
		public ImageView image;
		public TextView title;
		public TextView text;
	}

}
