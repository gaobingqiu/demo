package com.example.demo.news;

import java.util.List;

import org.apache.http.Header;

import com.entity.News;
import com.entity.Pager;
import com.example.demo.R;
import com.http.HttpUtils;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Activity;
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

public class MyFragment extends Fragment {
	private final String action_news_global = "NewsInterface/getGlobalNews.do";
	private final String action_news_pe = "NewsInterface/getPElNews.do";
	private final String action_news_te = "NewsInterface/getTeNews.do";
	private List<News> dataList;
	MyHandler handler;
	private String key = null;
	private ListView mListView;
	private MyAdapter adapter;
	private String requestUrl;

	static MyFragment newInstance(String s) {
		MyFragment myFragment = new MyFragment();
		Bundle bundle = new Bundle();
		bundle.putString("key", s);
		myFragment.setArguments(bundle);
		return myFragment;

	}

	public MyFragment() {
		// TODO Auto-generated constructor stub
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

	public void requestData() {
		 handler = new MyHandler();
		if (key == Contents.ZH) {
			requestUrl = action_news_global;
		} else if (key == Contents.TE) {
			requestUrl = action_news_te;
		} else {
			requestUrl = action_news_pe;
		}
		HttpUtils.post(requestUrl, null, responseHandler);


	}

	private TextHttpResponseHandler responseHandler = new TextHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, String response) {
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("responseString", response);
			msg.setData(bundle);// bundle传值，耗时，效率低
			handler.sendMessage(msg);
		}

		@Override
		public void onFailure(int i, Header[] aheader, String s, Throwable throwable) {
			// TODO Auto-generated method stub
			Log.i("sdkdemo", "errMsg: " + s);
		}
	};

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

	public void getNews() {
		requestData();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Log.d("Fragment 1", "onAttach");
		// if(count>1){
		// requestData();}
		// count++;
	}
}
