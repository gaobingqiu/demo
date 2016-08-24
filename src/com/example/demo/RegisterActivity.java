package com.example.demo;

import java.util.Locale;

import org.apache.http.Header;

import com.entity.RegisterRequestVo;
import com.entity.RegisterResponseVo;
import com.http.HttpUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import base.JsonUtil;
import base.Transformation;

public class RegisterActivity extends Activity {
	private String tag = "http";
	private String action = "com.example.demo.RegisterSuccessActivity";
	private String action_interface_register = "interfaces/register.do";
	private String url_register_request = "loginInterface/register.do";
	private String url_getCode = "loginInterface/getCode.do";
	private EditText loginNameView, telView, codeView, passwordView;
	private String loginName,tel,code,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 清除标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		findIds();
	}
	
	public void findIds(){
		loginNameView = (EditText) findViewById(R.id.loginName);
		telView = (EditText) findViewById(R.id.tel);
		codeView = (EditText) findViewById(R.id.code);
		passwordView = (EditText) findViewById(R.id.password);
	}

	public void sumbitRegister(View view) {
		loginName = loginNameView.getText().toString();
		password = passwordView.getText().toString();
		code = codeView.getText().toString();
		tel = telView.getText().toString();
		if (null == loginName || loginName.isEmpty()) {
			Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (null == password || password.isEmpty()) {
			Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (null == code || code.isEmpty()) {
			Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (null == tel || tel.isEmpty()) {
			Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		RegisterRequestVo registerRequestVo = new RegisterRequestVo();
		registerRequestVo.setUserName(loginNameView.getText().toString());
		registerRequestVo.setPassword(passwordView.getText().toString());
		registerRequestVo.setVerifyCode(codeView.getText().toString());
		registerRequestVo.setTel(telView.getText().toString());
		RequestParams params = Transformation.setParams(registerRequestVo);
		HttpUtils.post(url_register_request, params, responseHandler);
		startActivity(new Intent(action));
	}

	public void quickRegister(View view) {
		Log.d(tag, "POST request");
		String name = "gbq";
		String webName = "nubia";
		String verifyCode = "gbq123456";
		if (null == name || name.isEmpty()) {
			Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		RequestParams params = new RequestParams();
		params.put("userName", name);
		params.put("webName", webName);
		params.put("verifyCode", verifyCode);
		HttpUtils.post(action_interface_register, params, responseHandler);
	}

	public void fetchCode(View view) {
		telView = (EditText) findViewById(R.id.tel);
		RequestParams params = new RequestParams();
		params.put("tel", telView.getText().toString());
		HttpUtils.post(url_getCode, params, codeResponseHandler);
	}

	private TextHttpResponseHandler responseHandler = new TextHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, String response) {
			StringBuilder builder = new StringBuilder();
			for (Header h : headers) {
				String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
				builder.append(_h);
				builder.append("\n");
			}
			RegisterResponseVo registerVo = new RegisterResponseVo();
			registerVo = JsonUtil.createJsonBean(response, RegisterResponseVo.class);
			Intent intent = new Intent();
			intent.setClass(RegisterActivity.this, RegisterSuccessActivity.class);
			intent.putExtra("userName", registerVo.getUserName());
			startActivity(intent);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
			Log.d(tag, "网络异常");
		}
	};

	private TextHttpResponseHandler codeResponseHandler = new TextHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, String response) {
			StringBuilder builder = new StringBuilder();
			for (Header h : headers) {
				String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
				builder.append(_h);
				builder.append("\n");
			}
			String verifyCode = response;
			Toast.makeText(RegisterActivity.this, verifyCode, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
			Log.d(tag, "网络异常");
		}
	};
}
