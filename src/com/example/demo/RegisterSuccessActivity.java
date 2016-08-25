package com.example.demo;

import org.apache.http.Header;

import com.entity.GoPersonVo;
import com.http.HttpUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import base.Transformation;

public class RegisterSuccessActivity extends Activity {
	private TextView userNameView,passwordView;
	private LinearLayout quickPartView;
	private String url_getUser = "loginInterface/getUser.do";
    private String tag = "RegisterSuccessActivity->";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_success);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (null != bundle) {
			String userName = bundle.getString("userName");
			String password = bundle.getString("password");
			findIds();
			if(null==password||password.isEmpty()){
				quickPartView.setVisibility(View.GONE);
			}
			else {
				passwordView.setText(password);
			}
			userNameView.setText(userName);
		}
	}

	public void findIds() {
		userNameView = (TextView) findViewById(R.id.userName);
		passwordView = (TextView) findViewById(R.id.quick_password);
		quickPartView = (LinearLayout)findViewById(R.id.quick_part);
	}
	public void toPersonal(View view) {
		GoPersonVo goPersonVo = new GoPersonVo();
		goPersonVo.setUserName( userNameView.getText().toString());
		RequestParams params = Transformation.setParams(goPersonVo);
		HttpUtils.post(url_getUser, params, responseHandler);
		Intent intent = new Intent();
		intent.setClass(RegisterSuccessActivity.this, PersonalActivity.class);
		startActivity(intent);
	}
	private TextHttpResponseHandler responseHandler = new TextHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, String response) {
			if (null == response||response.isEmpty()) {
				Log.d(tag, "连接异常");
			} else {
				try {
					Intent intent = new Intent();
					intent.setClass(RegisterSuccessActivity.this, PersonalActivity.class);
					intent.putExtra("user", response);
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(RegisterSuccessActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
					Log.e(tag, response);
				}
				
			}
		}

		@Override
		public void onFailure(int i, Header[] aheader, String s, Throwable throwable) {
			// TODO Auto-generated method stub
			Toast.makeText(RegisterSuccessActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
			Log.e(tag, "网络连接异常");
		}
	};
}
