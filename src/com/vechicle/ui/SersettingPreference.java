package com.vechicle.ui;


import com.vechicle.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.Window;

public class SersettingPreference extends PreferenceActivity implements
OnSharedPreferenceChangeListener {

	public static final String KEY_IP_PREFERENCE = "ip_edit_preference";
	public static final String KEY_PORT_PREFERENCE = "port_edit_preference";

	private EditTextPreference ipEditPreference;
	private EditTextPreference portEditPreference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		addPreferencesFromResource(R.xml.serverpreference);
		ipEditPreference = (EditTextPreference) findPreference(KEY_IP_PREFERENCE);
		portEditPreference = (EditTextPreference) findPreference(KEY_PORT_PREFERENCE);
		ipEditPreference.setSummary(ipEditPreference.getText());
		portEditPreference.setSummary(portEditPreference.getText());
	}

	
	
	@Override
	protected void onResume() {
		super.onResume();
		ipEditPreference.setSummary(ipEditPreference.getText());
		portEditPreference.setSummary(portEditPreference.getText());
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		 Preference pref = findPreference(key);  
	        if (key.equals(KEY_IP_PREFERENCE)) {  
	            EditTextPreference ip_etp = (EditTextPreference) pref;  
	            pref.setSummary(ip_etp.getText());  
	        }else{
	        	EditTextPreference port_etp = (EditTextPreference) pref;  
	            pref.setSummary(port_etp.getText());
	        }
	}

}
