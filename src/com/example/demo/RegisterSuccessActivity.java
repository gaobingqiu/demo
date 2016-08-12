package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class RegisterSuccessActivity extends Activity {
	TextView userNameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_success);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (null != bundle) {
			String userName = bundle.getString("userName");
			userNameView = (TextView) findViewById(R.id.userName);
			userNameView.setText(userName);
		}
	}

	public void toPersonal(View view) {
		Intent intent = new Intent();
		intent.setClass(RegisterSuccessActivity.this, PersonalActivity.class);
		startActivity(intent);
	}
}
