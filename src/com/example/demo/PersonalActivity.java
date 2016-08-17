package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalActivity extends Activity {
	TextView userNameView;
	String tag = "personal";
	ListView linkList;
	private int[] images = { R.drawable.mus_cai_icon03, R.drawable.mus_cai_icon04, R.drawable.mus_cai_icon05,
			R.drawable.mus_cai_icon06, R.drawable.mus_cai_icon07 };
	private String[] mListTitle = { "实名认证", "手机绑定", "邮箱绑定", "登录密码管理", "常见问题" };
	ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (null != bundle) {
			String userName = bundle.getString("userName");
			userNameView = (TextView) findViewById(R.id.personalName);
			userNameView.setText(userName);
		}
		setList();
	}

	public void toOut(View view) {
		Log.d(tag, "userOut");
		Intent intent = new Intent();
		intent.setClass(PersonalActivity.this, MainActivity.class);
		startActivity(intent);
	}
	
	public void toNews(View view) {
		Intent intent = new Intent();
		intent.setClass(PersonalActivity.this, NewsMainActivity.class);
		startActivity(intent);
	}
	
	public void changeImg(View view) {
		Log.d(tag, "userOut");
		Intent intent = new Intent();
		intent.setClass(PersonalActivity.this, PhotoActivity.class);
		startActivity(intent);
	}

	public void setList() {
		linkList = (ListView) findViewById(R.id.lv_link);
		int lengh = mListTitle.length;
		for (int i = 0; i < lengh; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("image", images[i]);
			item.put("title", mListTitle[i]);
			mData.add(item);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, mData, R.layout.list_item, new String[] { "image", "title" },
				new int[] { R.id.image, R.id.link_name });
		linkList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		linkList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(PersonalActivity.this, "您选择了标题：" + mListTitle[arg2], Toast.LENGTH_LONG).show();
			}
		});
	}
}
