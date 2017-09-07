package com.vechicle.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;
import com.vechicle.control.VerifyAdapter;
import com.vechicle.domain.Vehicle;
import com.vechicle.util.WebserviceUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class VerifyListActivity extends Activity {

	private ListView listview;
	private ProgressDialog mProgress;
	private String status;
	List<HashMap<String,String>> vehicleList;
	VerifyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mProgress = new ProgressDialog(this);
		setContentView(R.layout.listview_verify);
		listview = (ListView) this.findViewById(R.id.lv_verify);
		vehicleList = new ArrayList<HashMap<String,String>>();
		adapter = new VerifyAdapter(vehicleList,this);
		
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listener);
		new VerifyInfoAsyn().execute();
	}
	
	private List<HashMap<String,String>> getData(String sUnqulify) {
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		JSONArray ja = null;
		try {
			if(!"".equals(sUnqulify)){
				ja = new JSONArray(sUnqulify);
				for(int i=0;i<ja.length();i++){
					HashMap<String, String> hashmap = new HashMap<String, String>();
					JSONObject jo = (JSONObject) ja.getJSONObject(i);
					hashmap.put("carnumber", (String) jo.get("License"));
					
					String hpzl = jo.getString("LicType");
					int hpzlIndex = Integer.parseInt(hpzl)-1;
	            	String sLictype = getResources().getStringArray(R.array.lictype)[hpzlIndex];
					hashmap.put("lictype", sLictype);
					hashmap.put("insptype", (String) jo.get("BussinessType"));
					hashmap.put("disqualify", (String) jo.get("unqualified"));
					hashmap.put("verifydate", (String) jo.get("Time"));
					hashmap.put("ycid", (String) jo.get("YCID"));
					
					list.add(hashmap);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void backReport(View view){
		VerifyListActivity.this.finish();
	}
	
	public void onRefresh(View view)throws Exception{
		new VerifyInfoAsyn().execute();
	}
	
	OnItemClickListener listener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			
			final String ycid = vehicleList.get(arg2).get("ycid");
			if(ycid == null){
				return;
			}
			new AlertDialog.Builder(VerifyListActivity.this).setTitle("授权").setMessage("是否确定授权?").setNegativeButton("取消", null)
        	.setPositiveButton("确定", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String sPosition = String.valueOf(arg2);
					new AuthorizationAsyn().execute(ycid,sPosition);
				}
        	}).create().show();
		}
	};
	
	private class VerifyInfoAsyn extends AsyncTask<String, Void, String>{
		@Override
		protected String doInBackground(String... params) {
			status = "400";
			String sUnqulify = null;
			try {
				sUnqulify = WebserviceUtil.getUnqualified();
				if(sUnqulify != null)
					status = "200";
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return sUnqulify;
		}
		
		@Override
        protected void onPostExecute (String unqulify) {
            super.onPostExecute(unqulify);
            mProgress.dismiss();
            if("200".equals(status)&& unqulify!= null){
            	vehicleList = getData(unqulify);
            	adapter.setList(vehicleList);
            	adapter.notifyDataSetChanged();
            }
            else{
            	new AlertDialog.Builder(VerifyListActivity.this).setTitle("提示").setMessage("网络异常，请检查网络")
              .setPositiveButton("确定", null).create().show();
            }
        }
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            mProgress.setTitle("请稍等");
    		mProgress.setMessage("读取待授权信息...");
    		mProgress.setCancelable(false);
            mProgress.show();
        }
	}
	
	private class AuthorizationAsyn extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			String ycid = params[0];
			int positon = Integer.parseInt(params[1]);
			status = "400";
			String sAuthorization = null;
			try {
				sAuthorization = WebserviceUtil.updatePower(ycid);
				if(sAuthorization != null && "1".equals(sAuthorization)){
					status = "200";
					vehicleList.remove(positon);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return status;
		}
		@Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);
            mProgress.dismiss();
            if("200".equals(result)){
				adapter.setList(vehicleList);
            	adapter.notifyDataSetChanged();
            }
            else{
            	new AlertDialog.Builder(VerifyListActivity.this).setTitle("提示").setMessage("网络异常，请检查网络")
              .setPositiveButton("确定", null).create().show();
            }
        }
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            mProgress.setTitle("授权");
    		mProgress.setMessage("正在授权...");
    		mProgress.setCancelable(false);
            mProgress.show();
        }
	}
}
