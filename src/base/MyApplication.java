package base;
import com.baidu.apistore.sdk.ApiStoreSDK;

import android.app.Application;

//请在AndroidManifest.xml中application标签下android:name中指定该类
public class MyApplication extends Application {
 
 @Override
 public void onCreate() {
 	// TODO 您的其他初始化流程
 	ApiStoreSDK.init(this, "213da79b2346384e512c1e557cfc10e8");
 	super.onCreate();
 }
}