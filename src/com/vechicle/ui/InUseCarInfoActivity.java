package com.vechicle.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;
import com.vechicle.control.MyViewPagerAdapter;
import com.vechicle.ui.fragment.InUseCarInfoFragment1;
import com.vechicle.ui.fragment.InUseCarInfoFragment2;
import com.vechicle.ui.fragment.InUseCarInfoFragment3;
import com.vechicle.util.HttpUtils;
import com.vechicle.util.ToolUtils;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class InUseCarInfoActivity extends FragmentActivity {

	private ViewPager viewpager;
	private List<Fragment> fragmentList;
	
	private ProgressDialog mProgress;
	private InUseCarInfoFragment1 fragment1;
	private InUseCarInfoFragment2 fragment2;
	private InUseCarInfoFragment3 fragment3;
	
	private String jsonString;
	
	MyViewPagerAdapter myViewPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inusecar);
		viewpager = (ViewPager) this.findViewById(R.id.viewpager);
		mProgress = new ProgressDialog(this);
		
		fragmentList = new ArrayList<Fragment>();
		fragment1 = new InUseCarInfoFragment1();
		fragment2 = new InUseCarInfoFragment2();
		fragment3 = new InUseCarInfoFragment3();
		fragmentList.add(fragment1);
		fragmentList.add(fragment2);
		fragmentList.add(fragment3);
		
		Intent intent = getIntent();
		String[] params = new String[2];
		params[0] = intent.getStringExtra("hpzl");
		params[1] = intent.getStringExtra("hphm");
		
		
		new InUseCarInfoAsyn().execute(params);
	}

	private class InUseCarInfoAsyn extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			if(params[0]!=null && !"".equals(params[0]) && 
					params[1]!=null && !"".equals(params[1])){
				
				String preUrl = ToolUtils.getServerURL()+"cms/baseManager!!multipleManager.action?mType=preCarRegisterManager&method=getCarInfoByCarNumberConvert";
				List<BasicNameValuePair> bnvs = new ArrayList<BasicNameValuePair>();
				bnvs.add(new BasicNameValuePair("hpzl", params[0])); 
				bnvs.add(new BasicNameValuePair("hphm", params[1])); 
				result = HttpUtils.postRequest(preUrl, bnvs);
			}
			return result;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgress.setTitle("请稍等");
    		mProgress.setMessage("读取在用车信息...");
    		mProgress.setCancelable(false);
            mProgress.show();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mProgress.dismiss();
			if (result != null && !"".equals(result)) {
				fragment1.setInfo(result);
				fragment2.setInfo(result);
				fragment3.setInfo(result);
				myViewPagerAdapter = new MyViewPagerAdapter(
						getSupportFragmentManager(), fragmentList);
				viewpager.setAdapter(myViewPagerAdapter);

				jsonString = result;
			} else {
				new AlertDialog.Builder(InUseCarInfoActivity.this)
						.setTitle("提示").setMessage("没有该车信息 或者 网络异常")
						.setPositiveButton("确定", null).create().show();
			}

		}
		
	}
	
	public void backReport(View view){
		back();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){    
				back();
	       }  
	    return false;  
	}
	
	public void back(){
		String clsbdh = null;
		String syxz = null;
		Intent data=new Intent();
		try {
			JSONObject jo = new JSONObject(jsonString);
			clsbdh = jo.getString("clsbdh");
			syxz = jo.getString("syxz");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.putExtra("clsbdh", clsbdh);
		data.putExtra("syxz", syxz);
		setResult(30, data);
		InUseCarInfoActivity.this.finish();
	}
	
}
