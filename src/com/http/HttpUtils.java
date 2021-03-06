package com.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtils {
	public static final String BASE_URL = "http://10.206.16.115:8080/";
	public static final String SERVER_IP = "http://10.206.16.115:8080";
	//private static final String BASE_URL = "http://192.168.1.104:8080/";
	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void setTimeout() {
		client.setTimeout(60000);
	}

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void download(String url, RequestParams params,
			FileAsyncHttpResponseHandler fileAsyncHttpResponseHandler) {
		client.get(getAbsoluteUrl(url), params, fileAsyncHttpResponseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
