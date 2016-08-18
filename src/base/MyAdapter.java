package base;

import java.util.List;

import org.apache.http.Header;

import com.entity.News;
import com.example.demo.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	String tag = "MyAdapter->";
	private List<News> list;
	private Context context;
	Drawable drw1;
	String url;
	ViewHolder viewHolder;

	public MyAdapter(List<News> news, Context context) {
		// TODO Auto-generated constructor stub
		this.list = news;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.news_list, null);
			viewHolder.picUrl = (ImageView) convertView.findViewById(R.id.news_image);
			viewHolder.ctime = (TextView) convertView.findViewById(R.id.news_time);
			viewHolder.url = (TextView) convertView.findViewById(R.id.news_url);
			viewHolder.url.setVisibility(View.GONE); // 隐藏不参与布局（不占地方）
			viewHolder.description = (TextView) convertView.findViewById(R.id.news_description);
			viewHolder.title = (TextView) convertView.findViewById(R.id.new_title);
			// 通过setTag方式 使viewHolder和convertView绑定在一起
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		News news = list.get(position);
		if (null == news.getPicUrl() || news.getPicUrl().isEmpty()) {
			Log.d(tag, "无图");
		} else {
			//加载网络图片begin
			url = news.getPicUrl();
			// 创建客户端对象
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(url, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					if (statusCode == 200) {
						BitmapFactory factory = new BitmapFactory();
						Bitmap bitmap = factory.decodeByteArray(responseBody, 0, responseBody.length);
						viewHolder.picUrl.setImageBitmap(bitmap);
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					error.printStackTrace();
				}
			});
			//加载网络图片end
		}
		viewHolder.ctime.setText(news.getCtime().substring(5));
		viewHolder.description.setText(news.getDescription());
		viewHolder.title.setText(news.getTitle());
		viewHolder.url.setText(news.getUrl());
		return convertView;
	}

	// 避免重复的findViewById
	class ViewHolder {
		public ImageView picUrl;
		private TextView ctime;
		private TextView url;
		private TextView description;
		private TextView title;
	}

}
