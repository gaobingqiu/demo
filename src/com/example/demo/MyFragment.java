package com.example.demo;

import java.util.List;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.entity.News;
import com.entity.Pager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import base.Contents;
import base.JsonUtil;
import base.MyAdapter;
import base.PageBean;

@SuppressLint("HandlerLeak")
public class MyFragment extends Fragment {
	private PageBean pageBean;
	private String requestUrl;
	private final String KeyId = "bc22ec037f0ddacb73c341fcb187a432";
	private List<News> dataList;
	MyHandler handler;
	private String type;
	private String key = null;
	private ListView listView;
	private MyAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = null; 
		view = inflater.inflate(R.layout.activity_tab_chat, container, false);			
		requestData();
		return view;
	}

	static MyFragment newInstance(String s,String requestUrl){
		MyFragment myFragment = new MyFragment(requestUrl);
		Bundle bundle = new Bundle();
		bundle.putString("key", s);
		myFragment.setArguments(bundle);
		return myFragment;
		
	}
	
	public MyFragment( String requestUrl) {
		// TODO Auto-generated constructor stub
		this.requestUrl = requestUrl;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Bundle bundle = getArguments();
		key = bundle != null? bundle.getString("key") : null;
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

//	public void setList(List<News> news) {
//		ListView linkList = (ListView) getActivity().findViewById(R.id.news_list);
//		adapter = new MyAdapter(news, getActivity());
//		linkList.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
//		linkList.setOnScrollListener(new OnScrollListener() {
//			@Override
//			public void onScrollStateChanged(AbsListView arg0, int arg1) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onScroll(AbsListView arg0, int firstItem, int visibleItemCount, int totalItemCount) {
//				// TODO Auto-generated method stub
//				if (firstItem + visibleItemCount == totalItemCount) {
//					requestData();
//					adapter.notifyDataSetChanged();
//				}
//			}
//		});
//	}
	
	public void setList(List<News> news) {
	listView = (ListView)getActivity().findViewById(R.id.news_list_global);
	dataList = news;
	adapter = new MyAdapter(dataList,getActivity());
	listView.setAdapter(adapter);
	
	listView.setOnScrollListener(new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			
		}
		
		@Override
		public void onScroll(AbsListView arg0, int firstItem, int visibleItemCount, int totalItemCount) {
			if (firstItem + visibleItemCount == totalItemCount) {
				requestData();
				adapter.notifyDataSetChanged();
			}
		}
	});
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
			setList(dataList);
		}
	}
}
