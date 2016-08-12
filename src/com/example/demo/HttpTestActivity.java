package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import org.apache.http.Header;

import com.http.HttpUtils;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HttpTestActivity extends Activity {

	private final static String tag = "MainActivity-->";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		Button getBtn = (Button) this.findViewById(R.id.getBtn);
		getBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				testGet();
			}
		});
		Button postBtn = (Button) this.findViewById(R.id.postBtn);
		postBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				testPost();
			}
		});

		Button upLoadBtn = (Button) this.findViewById(R.id.upLoadBtn);
		upLoadBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				testUploadFile();
			}
		});

		Button downloadBtn = (Button) this.findViewById(R.id.downloadBtn);
		downloadBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				testDownloadFile();
			}
		});
	}

	private void testGet() {
		HttpUtils.get("test/android.php", getParames(), responseHandler);
	}

	private RequestParams getParames() {
		RequestParams params = new RequestParams();
		params.put("user", "penngo");
		params.put("psw", "penngo");
		return params;
	}

	private TextHttpResponseHandler responseHandler = new TextHttpResponseHandler() {
		@Override
		public void onStart() {
			Log.e(tag, "onStart====");
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, String response) {
			Log.e(tag, "onSuccess====");
			StringBuilder builder = new StringBuilder();
			for (Header h : headers) {
				String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
				builder.append(_h);
				builder.append("\n");
			}
			Log.e(tag, "statusCode:" + statusCode + " headers:" + builder.toString() + " response:" + response);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
			Log.e(tag, "onFailure====");
			StringBuilder builder = new StringBuilder();
			for (Header h : headers) {
				String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
				builder.append(_h);
				builder.append("\n");
			}
			Log.e(tag, "statusCode:" + statusCode + " headers:" + builder.toString(), e);
		}

		@Override
		public void onRetry(int retryNo) {
			// called when request is retried
		}
	};

	private void testPost() {
		HttpUtils.post("test/android.php", getParames(), responseHandler);
	}

	private void testUploadFile() {
		RequestParams params = new RequestParams();
		try {
			// InputStream is = this.getAssets().open("png/launcher.png");
			String png = this.getExternalCacheDir().getAbsolutePath() + "/launcher.png";
			File myFile = new File(png);
			Log.e(tag, "png====" + png);
			this.copyToSD(png, "png/launcher.png");
			params.put("pngFile", myFile, RequestParams.APPLICATION_OCTET_STREAM);
		} catch (Exception e) {
			Log.e(tag, "上传失败", e);
		}
		HttpUtils.post("test/android.php", params, responseHandler);
	}

	private void testDownloadFile() {
		String mp3 = this.getExternalCacheDir().getAbsolutePath() + "/fa.mp3";
		File mp3File = new File(mp3);
		FileAsyncHttpResponseHandler fileHandler = new FileAsyncHttpResponseHandler(mp3File) {
			public void onSuccess(int statusCode, Header[] headers, File file) {
				Log.e(tag, "onSuccess====");
				StringBuilder builder = new StringBuilder();
				for (Header h : headers) {
					String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
					builder.append(_h);
					builder.append("\n");
				}
				Log.e(tag, "statusCode:" + statusCode + " headers:" + builder.toString() + " file:"
						+ file.getAbsolutePath());
			}

			public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {

			}
		};
		HttpUtils.download("test/fa.mp3", null, fileHandler);
	}

	/**
	 * 复制文件到sdcard
	 */
	private void copyToSD(String strOut, String srcInput) throws IOException {
		InputStream myInput;
		OutputStream myOutput = new FileOutputStream(strOut);
		myInput = this.getAssets().open(srcInput);
		byte[] buffer = new byte[1024];
		int length = myInput.read(buffer);
		while (length > 0) {
			myOutput.write(buffer, 0, length);
			length = myInput.read(buffer);
		}

		myOutput.flush();
		myInput.close();
		myOutput.close();
	}

}
