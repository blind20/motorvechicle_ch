package com.vechicle;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.vechicle.ui.LoginActivity;
import com.vechicle.ui.MenuActivity;
import com.vechicle.ui.ReportActivity;
import com.vechicle.util.SharePreUtil;
import com.vechicle.util.ToolUtils;
import com.vechicle.util.WebserviceUtil;
import com.xdja.vpnservice.VPNSocket;

public class Splash extends Activity { 

	public boolean vpnConStat = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.activity_splash);
		
		TextView tv_titletext = (TextView)this.findViewById(R.id.tv_titletext);
		tv_titletext.setText("机动车智能查验终端");
//		if(ToolUtils.isVehManageDpt(Splash.this)){
//			tv_titletext.setText("盐城市车管所查验系统");
//    	}else{
//    		tv_titletext.setText("机动车查验系统");
//    	}
		
		new Handler().postDelayed(runnable, 2000);
		
	}

	Runnable runnable = new Runnable() {
        @Override
        public void run() {
        		skip();
        }
    };
    
	
	private void skip() {
		Intent intent = new Intent();
		
		//调试用
//		intent.setClass(this, ReportActivity.class);
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	
	
	private class DeviceAsyn extends AsyncTask<String,Void,String>{
		private ProgressDialog mProgressDlg ;
		@Override
		protected void onPreExecute() {
			mProgressDlg = new ProgressDialog(Splash.this);
			mProgressDlg.setTitle("连接网络");
			mProgressDlg.setMessage("正在连接服务器...");
			mProgressDlg.setCancelable(false);
			mProgressDlg.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			JSONObject jo = new JSONObject();
			String msg = null;
			try {
				jo.put("imei", params[0]);
				msg = WebserviceUtil.invokeWebservice(jo.toString(), "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPostExecute(String obj) {
			mProgressDlg.dismiss();
		}
		
	}
}