package com.example.demo;

import org.apache.http.Header;

import com.entity.LoginVo;
import com.entity.User;
import com.http.HttpUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import base.JsonUtil;
import base.Transformation;

public class MainActivity extends Activity {
	String action = "com.example.demo.RegisterActivity";
	String action_login = "com.example.demo.PersonalActivity";
	String action_interface_login = "loginInterface/login.do";
	EditText userNameView;
	EditText passwordView;
	CheckBox saveUser;
	String username;
	String password;
	String tag = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findIds();
		getUserInfo();
		if (null != username && !username.isEmpty()) {
			saveUser.setChecked(true);
			userNameView.setText(username);
		}
		if (null != password && !password.isEmpty()) {
			passwordView.setText(password);
		}
		userNameView.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});

		saveUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
				} else {
					clearLoginInfo(MainActivity.this);
					userNameView.setText("");
					passwordView.setText("");
				}
			}
		});
	}

	public void userLogin(View view) {
		findIds();
		username = userNameView.getText().toString();
		password = passwordView.getText().toString();
		if (null == username || username.isEmpty()) {
			Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (null == password || password.length() < 5) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (saveUser.isChecked()) {
			saveLoginInfo(this, username, password);
		}
		LoginVo loginVo = new LoginVo();
		loginVo.setPassword(password);
		loginVo.setUserName(username);
		RequestParams params = Transformation.setParams(loginVo);
		HttpUtils.post(action_interface_login, params, responseHandler);
	}

	private TextHttpResponseHandler responseHandler = new TextHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, String response) {
			if (null == response||response.isEmpty()) {
				Log.d(tag, "连接异常");
			} else {
				User user = new User();
				try {
					user = JsonUtil.createJsonBean(response, User.class);
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, PersonalActivity.class);
					intent.putExtra("userName", user.getUserName());
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(MainActivity.this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
					Log.e(tag, response);
				}
				
			}
		}

		@Override
		public void onFailure(int i, Header[] aheader, String s, Throwable throwable) {
			// TODO Auto-generated method stub

		}
	};

	public void toRegister(View view) {
		startActivity(new Intent(action));
	}

	public void findIds() {
		saveUser = (CheckBox) findViewById(R.id.check);
		userNameView = (EditText) findViewById(R.id.userName);
		passwordView = (EditText) findViewById(R.id.Password);
	}

	public static void saveLoginInfo(Context context, String username, String password) {
		// 获取SharedPreferences对象
		SharedPreferences sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);
		// 获取Editor对象
		Editor editor = sharedPre.edit();
		// 设置参数
		editor.putString("username", username);
		editor.putString("password", password);
		// 提交
		editor.commit();
	}

	public static void clearLoginInfo(Context context) {
		// 获取SharedPreferences对象
		SharedPreferences sharedPre = context.getSharedPreferences("config", context.MODE_PRIVATE);
		// 获取Editor对象
		Editor editor = sharedPre.edit();
		// 设置参数
		editor.putString("username", null);
		editor.putString("password", null);
		// 提交
		editor.commit();
	}

	public void getUserInfo() {
		SharedPreferences sharedPre = getSharedPreferences("config", MODE_PRIVATE);
		username = sharedPre.getString("username", "");
		password = sharedPre.getString("password", "");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
