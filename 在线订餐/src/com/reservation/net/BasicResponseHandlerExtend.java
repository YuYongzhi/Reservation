package com.reservation.net;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;

public class BasicResponseHandlerExtend extends BasicResponseHandler{

	@Override
	public String handleResponse(HttpResponse response)
			throws HttpResponseException, IOException {
		StringBuffer sb = new StringBuffer("");
		if(response.getStatusLine().getStatusCode() == 200) {
			sb.append(EntityUtils.toString(response.getEntity(), "UTF-8"));
		}
		return sb.toString();
	}
}
