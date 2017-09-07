package com.vechicle.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;
import com.vechicle.util.ContextUtil;
import com.vechicle.util.GpsUtils;
import com.vechicle.util.HttpUtils;
import com.vechicle.util.SharePreUtil;
import com.vechicle.util.ToolUtils;
import com.vechicle.util.WebserviceUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity implements OnClickListener{
	
	private TextView tv_checkstation;
	private ProgressDialog mProgress;
	
	private LinearLayout ll_location;
	private TextView tv_location;
	private TextView tv_imei;
	private LinearLayout ll_apply_units;
	private TextView tv_apply_units;
	
	private GpsUtils gpsInstance;
	
	private double longitude;
	private double latitude;
	private Context context;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		context = SettingActivity.this;
		mProgress = new ProgressDialog(this);
		LinearLayout server = (LinearLayout)findViewById(R.id.server_set);
		LinearLayout station_set = (LinearLayout)findViewById(R.id.station_set);
		ll_location =  (LinearLayout)findViewById(R.id.ll_location);
		tv_location = (TextView)findViewById(R.id.tv_location);
		tv_imei = (TextView)findViewById(R.id.tv_imei);
		ImageView ivBack = (ImageView)findViewById(R.id.iv_back);
		tv_checkstation = (TextView) findViewById(R.id.tv_checkstation);
		ll_apply_units = (LinearLayout)findViewById(R.id.ll_apply_units);
		tv_apply_units = (TextView) findViewById(R.id.tv_apply_units);
		
		tv_imei.setText(getImei());
		
		server.setOnClickListener(this);
		station_set.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		ll_location.setOnClickListener(this);
		ll_apply_units.setOnClickListener(this);
		
		String stationName = SharePreUtil.getSharepref("stationname");
		String applyUnit = SharePreUtil.getSharepref("applyunits");
		
		if(!"".equals(applyUnit)){
			tv_apply_units.setVisibility(View.VISIBLE);
			tv_apply_units.setText(applyUnit);
		}
		
		if(!"".equals(stationName)){
			tv_checkstation.setVisibility(View.VISIBLE);
			tv_checkstation.setText(stationName);
		}
		
		gpsInstance = GpsUtils.getInstance(getApplicationContext());
		if (android.os.Build.VERSION.SDK_INT > 9) {
			 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			 StrictMode.setThreadPolicy(policy);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.server_set:
			Intent intent = new Intent(this, SersettingPreference.class);
			startActivity(intent);
			break;
		case R.id.station_set:
			new JCZInfoAsyn().execute();
			break;
		case R.id.iv_back:
			finish();
			break;
		case R.id.ll_apply_units:
			selectApplyUnits();
		case R.id.ll_location:
			gpsLocation();
		default:
			break;
		}
	}
	
	private void selectApplyUnits(){
		final String[] units = getResources().getStringArray(R.array.applyunits);
		AlertDialog dlg = new AlertDialog.Builder(
				SettingActivity.this)
				.setTitle("请选择")
				.setItems(units,new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
								tv_apply_units.setVisibility(View.VISIBLE);
								tv_apply_units.setText(units[which]);
								SharePreUtil.editSharepref("applyunits", units[which]);
							}
						}).show();
	}
	
	private boolean gpsLocation(){
		Context con = SettingActivity.this;
		if(GpsUtils.isOPen(con)){
			
			if(gpsInstance.getLocation() == null){
				Toast.makeText(this, "请到室外重新定位", Toast.LENGTH_LONG).show();
				return false;
			}else{
				tv_location.setVisibility(View.VISIBLE);
				
				gpsInstance.updateLocation();
				
				longitude = gpsInstance.getLocation().getLongitude();
				latitude = gpsInstance.getLocation().getLatitude();
				
				SharePreUtil.editSharepref("longitude", longitude +"");
				SharePreUtil.editSharepref("latitude", latitude +"");
				
				tv_location.setText("经度："+ longitude + "\n" +"纬度：" + latitude);
				
//				double meters = GpsUtils.getDistance(33.451897, 120.29655978, latitude, longitude);
//				Toast.makeText(this, "距离=" + meters, Toast.LENGTH_LONG).show();
				return true;
			}
			
		}else{
			Toast.makeText(this, "请开启GPS设置", Toast.LENGTH_LONG).show();
			GpsUtils.openGPS(con);
		}
		return false;
	}
	
	
	
	
	public void initialDevice(View view) throws Exception{
		String stationcode = SharePreUtil.getSharepref("stationcode");
		stationcode = "320000080";
		
		if(ToolUtils.isEmptyString(stationcode)){
			ToolUtils.alertDlg(context,"提示", "请先选择检测站");
			return;
		}
		
		try {
			JSONObject jo = new JSONObject();
			String result = null;
			if(gpsLocation()){
				jo.put("stationCode", stationcode);
				jo.put("imei", getImei());
				jo.put("x", longitude);
				jo.put("y", latitude);
				result = WebserviceUtil.invokeWebservice(jo.toString(), "saveDevice");
			}
			
			jo = new JSONObject(result);
			Log.e("result", "jo:"+jo.toString());
			if(200 == jo.getInt("state")){
				ToolUtils.alertDlg(context,"提示", "初始化成功，请等待管理员授权");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ToolUtils.alertDlg(context,"提示", "初始化失败，请检查网络或联系管理员");
		}
		
//		String result = WebserviceUtil.invokeWebservice(getImei(), "getDevices");
	}
	
	public String getImei(){
		return ToolUtils.deviceImei();
	}
	
	
	private class JCZInfoAsyn extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			String result = null;
				
			String preUrl = ToolUtils.getServerURL()+"cms/baseManager!!multipleManager.action?mType=preCarRegisterManager&method=getInfoOfJCZ";
			Log.i("JCZInfo", "url"+preUrl);
			List<BasicNameValuePair> bnvs = new ArrayList<BasicNameValuePair>();
			result = HttpUtils.postRequest(preUrl, bnvs);
			Log.i("JCZInfo", "res"+result);
			return result;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgress.setTitle("请稍等");
    		mProgress.setMessage("获取检测站信息...");
    		mProgress.setCancelable(false);
            mProgress.show();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mProgress.dismiss();
			JSONObject jsonObj;
			JSONArray jsonArray;
			try {
				if (result != null && !"".equals(result)) {

					jsonObj = new JSONObject(result);
					jsonArray = jsonObj.getJSONArray("data");
					final String[] jczmcArray = new String[jsonArray.length()];
					final String[] jczcodeArray = new String[jsonArray.length()];
					for (int i = 0; i < jsonArray.length(); i++) {
						jczmcArray[i] = jsonArray.getJSONObject(i).getString("StationName");
						jczcodeArray[i] = jsonArray.getJSONObject(i).getString("StationCode");
					}

					AlertDialog dlg = new AlertDialog.Builder(
							SettingActivity.this)
							.setTitle("请选择")
							.setItems(jczmcArray,new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,int which) {
											tv_checkstation.setVisibility(View.VISIBLE);
											tv_checkstation.setText(jczmcArray[which]);
											SharePreUtil.editSharepref("stationname", jczmcArray[which]);
											SharePreUtil.editSharepref("stationcode", jczcodeArray[which]);
										}
									}).show();

				} else {
					new AlertDialog.Builder(SettingActivity.this).setTitle("提示")
					.setMessage("请检查网络").setPositiveButton("确定", null)
					.create().show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
//		gpsInstance.closeLocation();
		Log.i("gpsInstance", "onPause is null");
	}

	@Override
	protected void onResume() {
		super.onResume();
//		gpsInstance = GpsUtils.getInstance(SettingActivity.this);
	}
}
