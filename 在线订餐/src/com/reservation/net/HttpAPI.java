package com.reservation.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
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

import com.reservation.utils.LRUCache;
import static com.reservation.utils.URLUtils.*;

import static android.net.ConnectivityManager.*;
import android.util.Log;

public class HttpAPI {

	private static final String TAG = "HTTP_API_CLIENT";
//	private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
//			+ "/Android/data/com.reservation/";
//	private static final String CACHE_PATH = PATH + "cache/";
//	private static final String FILES_PATH = PATH + "files/";
	/**
	 * 缓存失效时间
	 */
//	private static final long CACHE_TIME = 24 * 60 * 60 * 1000;
	
	private static Cache<String, String> mCache = new LRUCache<String, String>(100);
	
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
				response = "Error Response:" +
						httpResponse.getStatusLine().toString();
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
		Log.i(TAG, "response=" + response);
		return response;
	}
	
	/**
	 * 公用POST方法
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return 返回JSON字串或者null
	 * 
	 * @author 2014-2-5
	 */
	private static String getResponse(String url, Map<String, Object> params) {
		String response = null;
		response = mCache.get(url, TYPE_WIFI);
		Log.i(TAG, "Read cache:" + response);
		if (response == null) {
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			if(params != null) {
				for(String name : params.keySet()) {
					Log.i(TAG, "Name:" + name + ", Value:" + String.valueOf(params.get(name)));
					postParameters.add(new BasicNameValuePair(name, String.valueOf(params.get(name))));
				}
			}
			response = _post(url, postParameters);
			Log.i(TAG, "post :" + response);
			if (response != null && !(response.toUpperCase()).contains("Exception".toUpperCase())) {
				mCache.put(url, response); 
				Log.i(TAG, "Save cache" + response);
			} 
		} 
		return response;
	}
	
	public static String getOneLevelTypeJSON() {
		String response = null;
		response = getResponse(URL_ONE_LEVEL, null);
		return response;
	}
	
	public static String getTwoLevelTypeJSON() {
		String response = null;
		response = getResponse(URL_TWO_LEVEL, null);
		return response;
	}

	public static String getFoodsMenuJSON(Map<String, Object> params) {
		String response = null;
		response = getResponse(URL_CAIXI, params);
		return response;
	}

	
}
