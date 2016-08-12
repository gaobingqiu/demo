package base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.loopj.android.http.RequestParams;

import android.util.Log;

public class Transformation {
	private static String tag = "Transformation";

	private static RequestParams params;

	/**
	 * 实体转化为http参数 gbq
	 * 
	 * @param object
	 * @return 2016年8月11日
	 */
	public static RequestParams setParams(Object object) {
		params = new RequestParams();
		Field[] fields = object.getClass().getDeclaredFields();
		// String[] fieldNames=new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			params.put(fields[i].getName(), getValueByName(fields[i].getName(), object));
		}
		return params;
	}

	/**
	 * 根据反射机制获取属性值 gbq
	 * 
	 * @param fieldName
	 * @param object
	 * @return 2016年8月11日
	 */
	private static Object getValueByName(String fieldName, Object object) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = object.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(object, new Object[] {});
			return value;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(tag, e.getMessage());
			return null;
		}
	}
}
