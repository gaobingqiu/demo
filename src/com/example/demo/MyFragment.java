package com.example.demo;

import java.util.List;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.entity.News;
import com.entity.Pager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import base.Contents;
import base.JsonUtil;
import base.MyAdapter;
import base.PageBean;

public class MyFragment extends Fragment {
	private PageBean pageBean;
	private String requestUrl;
	private final String KeyId = "bc22ec037f0ddacb73c341fcb187a432";
	private List<News> dataList;
	MyHandler handler;
	private String key = null;
	private ListView mListView;
	private MyAdapter adapter;
	
	static MyFragment newInstance(String s, String requestUrl) {
		MyFragment myFragment = new MyFragment(requestUrl);
		Bundle bundle = new Bundle();
		bundle.putString("key", s);
		myFragment.setArguments(bundle);
		return myFragment;

	}

	public MyFragment(String requestUrl) {
		// TODO Auto-generated constructor stub
		this.requestUrl = requestUrl;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = null;
		if (key == Contents.ZH) {
			view = inflater.inflate(R.layout.news_global, container, false);
		} else if (key == Contents.TE) {
			view = inflater.inflate(R.layout.news_pe, container, false);
		} else {
			view = inflater.inflate(R.layout.news_te, container, false);
		}
		mListView = (ListView) view.findViewById(R.id.news_list);
		adapter = new MyAdapter(dataList, getActivity());
		mListView.setAdapter(adapter);
		requestData();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		Bundle bundle = getArguments();
		key = bundle != null ? bundle.getString("key") : null;
		super.onCreate(savedInstanceState);
	}

	Pager pager = null;

	@SuppressWarnings("unchecked")
	public void requestData() {
		handler = new MyHandler();
		Parameters para = new Parameters();
		pageBean = new PageBean(1, 10);
		para.put("num", pageBean.getPageSize());
		para.put("page", pageBean.getPageNo());
		para.put("key", KeyId);
		ApiStoreSDK.execute(this.requestUrl, ApiStoreSDK.GET, para, new ApiCallBack() {
			@Override
			public void onSuccess(int status, String responseString) {
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("responseString", responseString);
				msg.setData(bundle);// bundle传值，耗时，效率低
				handler.sendMessage(msg);
			}

			@Override
			public void onError(int status, String responseString, Exception e) {
				Log.i("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
			}

		});
	}

	public void refreshList(List<News> news) {
		dataList = news;
		adapter.setData(news);
		adapter.notifyDataSetChanged();
		
	}

	class MyHandler extends Handler {
		// 接受message的信息
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String responseString = msg.getData().getString("responseString");
			Pager pager = JsonUtil.createJsonBean(responseString, Pager.class);
			dataList = pager.getNewslist();
			refreshList(dataList);
		}
	}
}
