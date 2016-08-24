package base.service;

import com.example.demo.MainActivity;
import com.example.demo.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Message extends Service {
	private final String tag = "Service";
	Context context;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = getApplicationContext();
	}

	// 创建通知
	public void CreateInform() {
		// 定义一个PendingIntent，当用户点击通知时，跳转到某个Activity(也可以发送广播等)
		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

		// 创建一个通知
		Notification notification = new Notification(R.drawable.register_success, "巴拉巴拉~~", System.currentTimeMillis());
		notification.setLatestEventInfo(context, "点击查看", "欢迎使用Nubia账户", pendingIntent);

		// 用NotificationManager的notify方法通知用户生成标题栏消息通知
		NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nManager.notify(100, notification);// id是应用中通知的唯一标识
		// 如果拥有相同id的通知已经被提交而且没有被移除，该方法会用更新的信息来替换之前的通知。
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stubm
		super.onStart(intent, startId);
		Log.d(tag, "开始服务");
		CreateInform();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		CreateInform();
		return null;
	}

	/* 当从新尝试绑定时执行 */
	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
	}
}
