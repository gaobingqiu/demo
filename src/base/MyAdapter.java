package base;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.entity.News;
import com.example.demo.R;
import com.http.BitmapCache;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	String tag = "MyAdapter->";
	private List<News> list;
	private Context context;
	private String imgUrl;
	Drawable drw1;
	String url;
	ViewHolder viewHolder;
	private RequestQueue queue;
	private ImageLoader imageLoader;

	public MyAdapter(List<News> news, Context context) {
		// TODO Auto-generated constructor stub
		this.list = news;
		this.context = context;
		queue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(queue, new BitmapCache());
	}

	public void setData(List<News> news) {
		this.list = news;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != list) {
			return list.size();
		}
		return 0;
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
			viewHolder.img = (NetworkImageView) convertView.findViewById(R.id.news_image);
			viewHolder.ctime = (TextView) convertView.findViewById(R.id.news_time);
			viewHolder.url = (TextView) convertView.findViewById(R.id.news_url);
			viewHolder.url.setVisibility(View.GONE); // 隐藏不参与布局（不占地方）
			viewHolder.description = (TextView) convertView.findViewById(R.id.news_description);
			viewHolder.title = (TextView) convertView.findViewById(R.id.new_title);
			// 通过setTag方式 使viewHolder和convertView绑定在一起
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder = (ViewHolder) convertView.getTag();
		News news = list.get(position);
		imgUrl = news.getPicUrl();
		viewHolder.img.setDefaultImageResId(R.drawable.nubia);
		viewHolder.img.setErrorImageResId(R.drawable.nubia);
		viewHolder.img.setImageUrl(imgUrl, imageLoader);

		viewHolder.ctime.setText(news.getCtime().substring(5));
		viewHolder.description.setText(news.getDescription());
		viewHolder.title.setText(news.getTitle());
		viewHolder.url.setText(news.getUrl());
		return convertView;
	}

	// 避免重复的findViewById
	class ViewHolder {
		NetworkImageView img;
		private TextView ctime;
		private TextView url;
		private TextView description;
		private TextView title;
	}

}
