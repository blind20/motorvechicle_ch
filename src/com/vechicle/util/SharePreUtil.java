package com.vechicle.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreUtil {

	public final static String PREF_NAME = "sharepref_values";
	public static void editSharepref(String key,String value){
		SharedPreferences MyPreferences = ContextUtil.getInstance().getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = MyPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getSharepref(String key){
		SharedPreferences MyPreferences = ContextUtil.getInstance().getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		return MyPreferences.getString(key, "");
	}
	
}
