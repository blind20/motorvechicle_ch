package com.vechicle.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;
import com.vechicle.control.MyViewPagerAdapter;
import com.vechicle.ui.fragment.ChassisInfoFragment1;
import com.vechicle.ui.fragment.ChassisInfoFragment2;
import com.vechicle.ui.fragment.ChassisInfoFragment3;
import com.vechicle.util.HttpUtils;
import com.vechicle.util.ToolUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ChassisInfoActivity extends FragmentActivity {

	private ViewPager viewpager;
	private List<Fragment> fragmentList;
	
	private ProgressDialog mProgress;
	private ChassisInfoFragment1 fragment1;
	private ChassisInfoFragment2 fragment2;
	private ChassisInfoFragment3 fragment3;
	private String sDpgg;
	
	MyViewPagerAdapter myViewPagerAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chassisinfo);
		viewpager = (ViewPager) this.findViewById(R.id.viewpager);
		mProgress = new ProgressDialog(this);
		
		fragmentList = new ArrayList<Fragment>();
		fragment1 = new ChassisInfoFragment1();
		fragment2 = new ChassisInfoFragment2();
		fragment3 = new ChassisInfoFragment3();
		fragmentList.add(fragment1);
		fragmentList.add(fragment2);
		fragmentList.add(fragment3);
		
		Intent intent = getIntent();
		String[] params = new String[3]; 
		params[0] = intent.getStringExtra("dpid");
		params[1] = intent.getStringExtra("hpzl");
		params[2] = intent.getStringExtra("hphm");
		
		new ChassisInfoAsyn().execute(params);
	}
	
	private class ChassisInfoAsyn extends AsyncTask<String, Void, String>{
		
		private String sDpid;
		@Override
		protected String doInBackground(String... params) {
			String result = null;
			sDpid = params[0];
			List<BasicNameValuePair> bnvs = new ArrayList<BasicNameValuePair>();
			
			if(params[0]!=null && !"".equals(params[0])){
				
				String url = ToolUtils.getServerURL()+"cms/baseManager!!multipleManager.action?mType=trafficDBManager&method=getListOfDPGG";
				bnvs.clear();
				bnvs.add(new BasicNameValuePair("dpid", params[0])); 
				result = HttpUtils.postRequest(url, bnvs);
				
			}else if(params[1]!=null && !"".equals(params[1]) && params[2]!=null && !"".equals(params[2])){
				
				String url = ToolUtils.getServerURL()+"cms/baseManager!!multipleManager.action?mType=preCarRegisterManager&method=getCarInfoByCarNumberConvert";
				
				String bh = null;
				bnvs.clear();
				bnvs.add(new BasicNameValuePair("hpzl", params[1])); 
				bnvs.add(new BasicNameValuePair("hphm", params[2])); 
				result = HttpUtils.postRequest(url, bnvs);

				try {
					bh = new JSONObject(result).getString("dphgzbh");
//					bh = "SC060926002945";
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				if(bh == null || "".equals(bh)){
					result = null;
				}else{
					url = ToolUtils.getServerURL()+"cms/baseManager!!multipleManager.action?mType=trafficDBManager&method=getDPGG";
					bnvs.clear();
					bnvs.add(new BasicNameValuePair("bh", bh));
					result = HttpUtils.postRequest(url, bnvs);
				}
			}
			return result;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgress.setTitle("请稍等");
    		mProgress.setMessage("读取底盘公告信息...");
    		mProgress.setCancelable(false);
            mProgress.show();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mProgress.dismiss();
			
			if(result != null && !"".equals(result)){
				sDpgg = result;
				fragment1.setInfo(result);
				fragment2.setInfo(result);
				fragment3.setInfo(result);
				myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList);
				viewpager.setAdapter(myViewPagerAdapter);
				
				if(sDpid!=null && !"".equals(sDpid)){
					fragment1.setIndex(0);
					fragment2.setIndex(0);
					fragment3.setIndex(0);
				}else{
					fragment1.setIndex(0);
					fragment2.setIndex(0);
					fragment3.setIndex(0);
				}
			}else{
				new AlertDialog.Builder(ChassisInfoActivity.this).setTitle("提示")
				.setMessage("没有底盘公告信息").setPositiveButton("确定", null)
				.create().show();
			}
		}
		
	}
	
	public void backReport(View view){
		ChassisInfoActivity.this.finish();
	}
	
	public void moreDpxh(View view){
		try {
			Log.i("moreDpxh", "moreDpxh:"+sDpgg);
			if(sDpgg!=null && !"".equals(sDpgg)){
				final JSONArray jsonArray = new JSONObject(sDpgg).getJSONArray("data");
				if(jsonArray==null || jsonArray.length()==0){
					toastInfo("没有底盘信息");
					return;
				}
				String[] ggrqs = new String[jsonArray.length()];
				for(int i=0;i<jsonArray.length();i++){
					ggrqs[i] = jsonArray.getJSONObject(i).getString("GGRQ");
				}
				new AlertDialog.Builder(ChassisInfoActivity.this).setItems(ggrqs, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						fragment1 = new ChassisInfoFragment1();
						fragment2 = new ChassisInfoFragment2();
						fragment3 = new ChassisInfoFragment3();
						fragmentList = new ArrayList<Fragment>();
						fragmentList.add(fragment1);
						fragmentList.add(fragment2);
						fragmentList.add(fragment3);
						fragment1.setInfo(sDpgg);
						fragment2.setInfo(sDpgg);
						fragment3.setInfo(sDpgg);
						
						fragment1.setIndex(which);
						fragment2.setIndex(which);
						fragment3.setIndex(which);
						
						viewpager.removeAllViews();
						viewpager.removeAllViewsInLayout();
						myViewPagerAdapter.destroyFragments();
						myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList);
						viewpager.setAdapter(myViewPagerAdapter);
						
					}
				}).create().show();
				
			}else{
				toastInfo("没有底盘信息");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void toastInfo(String str){
		Toast.makeText(getApplicationContext(), str,Toast.LENGTH_LONG).show();
	}
	
}
