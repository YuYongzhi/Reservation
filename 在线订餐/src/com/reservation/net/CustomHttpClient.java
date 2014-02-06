package com.reservation.net;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.reservation.utils.ToolUtils;

public class CustomHttpClient {
	
	private static HttpClient customHttpClient;

	/**
	 * Singleton instance
	 * 
	 * 
	 * @author 2014-2-5
	 */
	private CustomHttpClient() {
		
	}
	
	public static synchronized HttpClient getHttpClient() {
		if (customHttpClient == null) {
			HttpParams params = new BasicHttpParams();
			
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setUserAgent(params, ToolUtils.getAppAgent());
			
			ConnManagerParams.setTimeout(params, 1000);
			
			HttpConnectionParams.setConnectionTimeout(params, 2000);
			HttpConnectionParams.setSoTimeout(params, 4000);
			
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
			
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
			
			customHttpClient = new DefaultHttpClient(conMgr, params);
		}
		return customHttpClient;
	}

}
