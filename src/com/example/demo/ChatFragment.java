package com.example.demo;

import java.util.List;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.entity.News;
import com.entity.Pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import base.JsonUtil;
import base.MyAdapter;

public class ChatFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		requestData();
		View chatView = inflater.inflate(R.layout.activity_tab_chat, container, false);
		return chatView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	Pager pager = null;
	
	@SuppressWarnings("unchecked")
	public void requestData(){
		Parameters para = new Parameters();
		para.put("num", "10");
		para.put("page", "1");
		if (pager != null) {
			setList(pager.getNewslist());
			return;
		}
		ApiStoreSDK.execute("http://apis.baidu.com/txapi/world/world", ApiStoreSDK.GET, para, new ApiCallBack() {
			@Override
			public void onSuccess(int status, String responseString) {
				Log.i("sdkdemo", responseString);
				Pager pager= JsonUtil.createJsonBean(responseString,Pager.class);
				// mTextView.setText(responseString);
				setList(pager.getNewslist());
			}

			@Override
			public void onError(int status, String responseString, Exception e) {
				Log.i("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
			}

		});
	}
	
	public void setList(List<News> news) {
		ListView linkList = (ListView) getActivity().findViewById(R.id.news_list);
		MyAdapter adapter = new MyAdapter(news,getActivity());
		linkList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		linkList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(getActivity(), "您选择了标题：", Toast.LENGTH_LONG).show();
			}
		});
	}

}
