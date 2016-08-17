package base;

import java.util.List;

import com.entity.News;
import com.example.demo.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private List<News> list;
	private Context context;

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
		// TODO Auto-generated method stub
		// 文艺式
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.news_list, null);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_image);
			viewHolder.nickName = (TextView) convertView.findViewById(R.id.tv_nickName);
			viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content);
			// 通过setTag方式 使viewHolder和convertView绑定在一起
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		News news = list.get(position);
		// viewHolder.imageView.setBackgroundResource(u);
		// viewHolder.nickName.setText(myObject.getNickName());
		// viewHolder.content.setText(myObject.getContent());
		return convertView;
	}

	// 避免重复的findViewById
	class ViewHolder {
		public ImageView imageView;
		private TextView nickName;
		private TextView content;
	}

}
