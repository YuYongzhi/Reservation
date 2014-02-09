package com.reservation.net;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import com.reservation.utils.Cache;
import com.reservation.utils.ToolUtils;

import com.reservation.utils.LRUCache;
import static com.reservation.utils.URLUtils.*;

import static android.net.ConnectivityManager.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.util.Log;

public class HttpAPI {

	private static final String TAG = "HTTP_API_CLIENT";
	
//	public static Context mContext;
	
	/**
	 * SD卡缓存路径
	 */
	private static final String PATH =
			Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/Android/data/com.reservation/";
	private static final String CACHE_PATH = PATH + "cache/";
	
	/**
	 * 缓存失效时间
	 */
	private static final long IMAGE_CACHE_TIME = 24 * 60 * 60 * 1000;

	/**
	 * 数据内存缓存
	 */
	private static Cache<String, Object> mCache = new LRUCache<String, Object>(500);

	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * 
	 * @author 2014-2-5
	 * @throws Exception
	 */
	private static String _post(String url, List<NameValuePair> params) {
		HttpClient httpClient = null;
		HttpPost httpRequest = null;

		httpRequest = new HttpPost(url);
		String response = "doPostError";

		try {
			httpClient = CustomHttpClient.getHttpClient();
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				response = EntityUtils.toString(httpResponse.getEntity());
			} else {
				response = "Error Response: "
						+ httpResponse.getStatusLine().toString();
			}
		} catch (UnsupportedEncodingException e) {
			response = "UnsupportedEncodingException:" + e.getMessage();
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			response = "ClientProtocolException:" + e.getMessage();
			e.printStackTrace();
		} catch (ParseException e) {
			response = "ParseException:" + e.getMessage();
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			response = "SocketTimeoutException:" + e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			response = "IOException:" + e.getMessage();
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 向服务器请求图片
	 * @param url
	 * @return
	 * 
	 * @author 2014-2-9
	 */
	public static Bitmap _getBitmap(String url) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		Bitmap bitmap = null;
		InputStream inStream = null;
		
		try {
			httpClient = CustomHttpClient.getHttpClient();
			httpPost = new HttpPost(url);
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new HttpException();
			}
			inStream = response.getEntity().getContent();
			bitmap = BitmapFactory.decodeStream(inStream);
			inStream.close();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 获取网络图片
	 * 		先读取缓存，如果没有缓存或缓存失效，则向服务器请求图片
	 * @param url
	 * @return
	 * 
	 * @author 2014-2-9
	 */
	public static Bitmap getNetBitmap(String url) {
		String fileName = url.substring(url.lastIndexOf("/"), url.lastIndexOf("."));
		Bitmap bitmap = null;
		int retryCnt = 0;
		
		bitmap = readBitmapCache(fileName);
		if (bitmap == null) {
			do {
				bitmap = _getBitmap(url);
				if (bitmap != null) {
					bitmap = ThumbnailUtils.extractThumbnail(bitmap, 144, 144);
					saveBitmapCache(bitmap, fileName);
					break;
				}
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				retryCnt ++;
			} while (retryCnt < 5);
		}
		Log.i(TAG, "fileName=" + fileName + ", RetryCnt=" + retryCnt + ", Bitmap=" + bitmap);
		return bitmap;
	}
	
	public static Bitmap readBitmapCache(String fileName) {
		if (ToolUtils.isSDCardExist() == false) {
			return null;
		}
		
		Bitmap bitmap = null;
		File file = new File(CACHE_PATH + fileName);
		if (file.exists() == false) {
			return null;
		} 
		
		if (System.currentTimeMillis() - file.lastModified() > IMAGE_CACHE_TIME) {
			file.delete();
			return null;
		}
		
		bitmap = BitmapFactory.decodeFile(CACHE_PATH + fileName);
		return bitmap;
	}
	
	/**
	 * 将图片缓存到SD卡中
	 * @param bitmap
	 * @param fileName
	 * 
	 * @author 2014-2-9
	 */
	public static void saveBitmapCache(Bitmap bitmap, String fileName) {
		if (bitmap == null) {
			return ;
		}
		 
		if (ToolUtils.isSDCardExist() == false) {
			return ;
		}
		
		if (ToolUtils.getSDCardAvailableSize() < 1024) {
			return ;
		}
		
		try {
			File folder = new File(CACHE_PATH);
			File file = new File(CACHE_PATH + fileName);
			if (folder.exists() == false) {
				folder.mkdirs();
			}
			if (file.exists() == false) {
				file.createNewFile();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 公用POST方法
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return 返回JSON字串或者null
	 * 
	 * @author 2014-2-5
	 */
	private static String getResponse(String url, Map<String, Object> params) {
		String response = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		if (params != null) {
			for (String name : params.keySet()) {
				Log.i(TAG, "Name:" + name + ", Value:" + String.valueOf(params.get(name)));
				postParameters.add(new BasicNameValuePair(name, String.valueOf(params.get(name))));
			}
		}
		response = _post(url, postParameters);
		int retryCnt = 0;
		do {
			response = _post(url, postParameters);
			retryCnt ++;
			if (((response.toUpperCase()).contains("Exception".toUpperCase())) 
					|| ((response.toUpperCase()).contains("Error".toUpperCase()))) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			} else {
				break;
			}
		} while (retryCnt < 3);
		Log.i(TAG, "retryCnt=" + retryCnt);
		Log.i(TAG, "post=" + response);
		return response;
	}
	

	public static String getOneLevelTypeJSON() {
		Log.i(TAG, "getOneLevelTypeJSON");
		String response = null;
		response = (String) mCache.get(URL_ONE_LEVEL, TYPE_WIFI);
		Log.i(TAG, "Get cache=" + response);
		if (response == null) {
			response = getResponse(URL_ONE_LEVEL, null);
			if ((response != null) && 
					(!(response.toUpperCase()).contains("Exception".toUpperCase())) && 
					(!(response.toUpperCase()).contains("Error".toUpperCase()))) {
				mCache.put(URL_ONE_LEVEL, response);
				Log.i(TAG, "Save cache=" + response);
			}
		}
		return response;
	}

	public static String getTwoLevelTypeJSON() {
		Log.i(TAG, "getTwoLevelTypeJSON");
		String response = null;
		response = (String) mCache.get(URL_TWO_LEVEL, TYPE_WIFI);
		Log.i(TAG, "Get cache" + response);
		if (response == null) {
			response = getResponse(URL_TWO_LEVEL, null);
			if ((response != null) && 
					(!(response.toUpperCase()).contains("Exception".toUpperCase())) && 
					(!(response.toUpperCase()).contains("Error".toUpperCase()))) {
				mCache.put(URL_TWO_LEVEL, response);
				Log.i(TAG, "Save cache" + response);
			}
		}
		return response;
	}

	public static String getFoodsMenuJSON(Map<String, Object> params) {
		String response = null;
		response = getResponse(URL_CAIXI, params);
		return response;
	}

}
