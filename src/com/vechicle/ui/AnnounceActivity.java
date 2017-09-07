/**
 * 
 */
package com.vechicle.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;
import com.vechicle.control.MyViewPagerAdapter;
import com.vechicle.ui.fragment.AnnounceFragment1;
import com.vechicle.ui.fragment.AnnounceFragment2;
import com.vechicle.ui.fragment.AnnounceFragment3;
import com.vechicle.ui.fragment.AnnounceFragment4;
import com.vechicle.ui.fragment.AnnounceFragment5;
import com.vechicle.util.HttpUtils;
import com.vechicle.util.ToolUtils;
import com.vechicle.util.WebserviceUtil;

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


public class AnnounceActivity extends FragmentActivity {

	private ViewPager viewpager;
	private List<Fragment> fragmentList;
	private ArrayList<String> titleList;
	
	private AnnounceFragment1 fragment1;
	private AnnounceFragment2 fragment2;
	private AnnounceFragment3 fragment3;
	private AnnounceFragment4 fragment4;
	private AnnounceFragment5 fragment5;
	private ProgressDialog mProgress;
	
	private JSONObject gongGao;
	private String fdjh;
	private String qlj;
	private String hlj;
	private String zj;
	private String ggbh;
	
	private String status;
	private JSONArray ja;
	private String[] ggrqs;
	
	private String hphm;
	private String hpzl;
	
	MyViewPagerAdapter myViewPagerAdapter;
	
	private boolean isSelectDate = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announce);
		Intent intent = getIntent();
		ggbh = intent.getStringExtra("ggbh");
		fdjh = intent.getStringExtra("fdjh");
		qlj = intent.getStringExtra("qlj");
		hlj = intent.getStringExtra("hlj");
		zj = intent.getStringExtra("zj");
		hphm = intent.getStringExtra("hphm");
		hpzl = intent.getStringExtra("hpzl");
		initView();
	}
	
	public void initView() {
		viewpager = (ViewPager) this.findViewById(R.id.viewpager);
		mProgress = new ProgressDialog(this);
		
//		pagerTabStrip = (PagerTabStrip) this.findViewById(R.id.pagertab);
//		pagerTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.background_light)); 
//		pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.title_blue));
//		titleList  = new ArrayList<String>();
//		titleList.add("公告信息（一） ");
//	    titleList.add("公告信息（二）");
//	    titleList.add("公告信息（三） ");
		
		new NewGongGaoAsyn().execute();
	}

	
	
	public void backReport(View view){
		AnnounceActivity.this.finish();
	}
	
	public void selectMoreDate(View view){
		try {
			if (gongGao != null) {
				ja = gongGao.getJSONArray("GGLB");
				ggrqs = new String[ja.length()];
				
				if(ja==null || ja.length()==0){
					Toast.makeText(AnnounceActivity.this,"没有其他公告可供选择", Toast.LENGTH_LONG).show();
					return;
				}
				for(int i=0;i<ja.length();i++){
					ggrqs[i] = ja.getJSONObject(i).getString("ggrq");
				}
				new AlertDialog.Builder(AnnounceActivity.this).setItems(ggrqs, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							ggbh = ja.getJSONObject(which).getString("bh");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						isSelectDate = true;
						new NewGongGaoAsyn().execute();
					}
				}).create().show();
			}
		} catch (Exception e) {
		}
		
	}
	
	private class NewGongGaoAsyn extends AsyncTask<Void, Void, JSONObject>{

		@Override
		protected JSONObject doInBackground(Void... params) {
			status = "400";
			try {
				if (ToolUtils.isNotEmptyString(ggbh)) {
					/*String ggInfo = WebserviceUtil.getGongGaoByBhNew(ggbh);
					if (ggInfo != null) {
						status = "200";
						if (!isSelectDate) {
							gongGao = new JSONObject(ggInfo);
							return gongGao;
						} else {
							return new JSONObject(ggInfo);
						}
					}*/
					return getGongGaoViaGgbh(ggbh);

				}

				if (ToolUtils.isNotEmptyString(hphm) || ToolUtils.isNotEmptyString(hpzl)) {
					String preUrl = ToolUtils.getServerURL()
							+ "cms/baseManager!!multipleManager.action?mType=preCarRegisterManager&method=getCarInfoByCarNumberConvert";
					List<BasicNameValuePair> bnvs = new ArrayList<BasicNameValuePair>();
					bnvs.add(new BasicNameValuePair("hpzl", hpzl));
					bnvs.add(new BasicNameValuePair("hphm", hphm));
					String info = HttpUtils.postRequest(preUrl, bnvs);
					Log.i("aaaa",info);
					
					if("".equals(info)){
						status = "201";
					}

					if (ToolUtils.isNotEmptyString(info)) {
						info = new JSONObject(info).getString("ggbh");
						/*String ggInfo = WebserviceUtil.getGongGaoByBhNew(info);
						if (ggInfo != null) {
							status = "200";
							if (!isSelectDate) {
								gongGao = new JSONObject(ggInfo);
								return gongGao;
							} else {
								return new JSONObject(ggInfo);
							}
						}*/
						return getGongGaoViaGgbh(info);
					}
				}
				
				if(ToolUtils.isEmptyString(ggbh) && ToolUtils.isEmptyString(hphm)){
					status = "201";
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				status = "400";
			} 
			
			return null;
		}
		
		@Override
        protected void onPostExecute (JSONObject gongGao) {
			super.onPostExecute(gongGao);
			mProgress.dismiss();
			
			if ("200".equals(status)) {
				
				fragment1 = new AnnounceFragment1();
				fragment2 = new AnnounceFragment2();
				fragment3 = new AnnounceFragment3();
				fragment4 = new AnnounceFragment4();
				fragment5 = new AnnounceFragment5();
				fragmentList = new ArrayList<Fragment>();
				fragmentList.add(fragment1);
				fragmentList.add(fragment2);
				fragmentList.add(fragment3);
				fragmentList.add(fragment4);
				fragmentList.add(fragment5);
				
				fragment1.setGongGao(gongGao);
				fragment2.setGongGao(gongGao);
				fragment3.setGongGao(gongGao);
				fragment4.setGongGao(gongGao);
				fragment5.setGongGao(gongGao);
				
				fragment1.setFdjh(fdjh);
				fragment4.setQlj(qlj);
				fragment4.setHlj(hlj);
				fragment4.setZj(zj);
				
				if(isSelectDate){
					viewpager.removeAllViews();
					viewpager.removeAllViewsInLayout();
					myViewPagerAdapter.destroyFragments();
					
				} 
				myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList);
				viewpager.setAdapter(myViewPagerAdapter);
			   
			} else if ("201".equals(status)) {
				new AlertDialog.Builder(AnnounceActivity.this).setTitle("提示")
						.setMessage("没有公告信息 或者 网络异常").setPositiveButton("确定", null)
						.create().show();
			} else {
				new AlertDialog.Builder(AnnounceActivity.this).setTitle("提示")
						.setMessage("网络异常，请检查网络").setPositiveButton("确定", null)
						.create().show();
			}
        }
		
		
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            mProgress.setTitle("请稍等");
    		mProgress.setMessage("读取公告信息...");
            mProgress.show();
            
        }
		
	}
	
	public JSONObject getGongGaoViaGgbh(String ggbh){
		try {
			String ggInfo = WebserviceUtil.getGongGaoByBhNew(ggbh);
			if (ToolUtils.isNotEmptyString(ggInfo)) {
				status = "200";
				if (!isSelectDate) {
					gongGao = new JSONObject(ggInfo);
				}
				return new JSONObject(ggInfo);
			}else{
				status = "201";
			}
			
		} catch (Exception e) {
			Log.e("getGongGaoViaGgbh", "网络异常", e);
			status = "400";
		}
		return null;
	}
	
	
}
