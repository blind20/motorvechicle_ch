package com.vechicle.ui;

import java.util.List;

import org.json.JSONObject;

import com.vechicle.R;
import com.vechicle.service.UpdateManager;
import com.vechicle.service.XmlParserService;
import com.vechicle.util.SharePreUtil;
import com.vechicle.util.ToolUtils;
import com.vechicle.util.WebserviceUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class LoginActivity extends Activity {

	private Button btn_show;
	private EditText passwordEdt;
	private EditText usrEdt;
	private String usrname;
	private String passwd;
	private TextView tv_user_scope;
	private String user_scope;
	
	private ProgressDialog mProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btn_show = (Button) findViewById(R.id.btn_showpwd);
		passwordEdt = (EditText)findViewById(R.id.password);
		usrEdt = (EditText)findViewById(R.id.username);
		tv_user_scope = (TextView)findViewById(R.id.tv_user_scope);
		mProgress = new ProgressDialog(this);
		
		usrname = SharePreUtil.getSharepref("usrname");
		passwd = SharePreUtil.getSharepref("passwd");
		user_scope = SharePreUtil.getSharepref("applyunits");
		
		if(ToolUtils.isNotEmptyString(passwd) && ToolUtils.isNotEmptyString(usrname)){
			usrEdt.setText(usrname);
			passwordEdt.setText(passwd);
		}
		
		tv_user_scope.setText(user_scope);
		//在线更新功能
		new UpdateManager(LoginActivity.this).checkUpdate();
		
	}

	@SuppressLint("ShowToast")
	public void setting(View view) {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
	}
	
	public void showpwd(View view){
		if(btn_show.getText().equals("显示")){
			passwordEdt.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
			btn_show.setText("隐藏");
		}else {
			passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			passwordEdt.setSelection(passwordEdt.getText().length());
			btn_show.setText("显示");
		}
	}
	
	public void enter(View view) {
		usrname = usrEdt.getText().toString();
		passwd = passwordEdt.getText().toString();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
		String ip = prefs.getString("ip_edit_preference", "127.0.0.1");
		String port = prefs.getString("port_edit_preference", "8088");
		if(usrname.equals("") || usrname==null || passwd.equals("") || passwd==null){
			Toast.makeText(this, "请输入账号和密码", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(ip)||"".equals(port)){
			Toast.makeText(this, "请设置服务器地址和端口", Toast.LENGTH_LONG).show();
			return;
		}
		new LoginAsynTask().execute();
	}
	
	public void selectUsr(View view) throws Exception{
		new LoadUsrAsynTask().execute();
	}

	private class LoadUsrAsynTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground (Void... params) {
        	List<String> usrList = null;
        	String stationcode = SharePreUtil.getSharepref("stationcode");
        	Log.e("stationcode", "code:"+stationcode);
        	if("".equals(stationcode) || stationcode==null){
        		return null;
        	}
        	
			try {
				JSONObject jo = new JSONObject();
				jo.put("methodCode", 32);
				jo.put("stationCode", stationcode);
				String sReturn = WebserviceUtil.getUsrList(jo.toString());
				usrList = XmlParserService.getUsrList(sReturn);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
        	return usrList;
        }
        
        @Override
        protected void onPostExecute (List<String> usrList) {
            super.onPostExecute(usrList);
            mProgress.dismiss();
            if(usrList != null){
            	final int size = usrList.size();
        		final String[] users = (String[]) usrList.toArray(new String[size]);
        		if(users.length >0){
	        		new AlertDialog.Builder(LoginActivity.this).setItems(users, new DialogInterface.OnClickListener(){
	        			@Override
	        			public void onClick(DialogInterface dialog, int which) {
	        				usrEdt.setText(users[which]);
	        				passwordEdt.setText("");
	        			}}).create().show();
        		}
            }else{
            	new AlertDialog.Builder(LoginActivity.this).setTitle("提示").setMessage("请检查网络")
                .setPositiveButton("确定", null).create().show();
            }
        }
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            mProgress.setTitle("请稍等");
    		mProgress.setMessage("读取用户...");
            mProgress.show();
            mProgress.setCancelable(false);
        }
    }
	
	public void preReg(View view) throws Exception{
		Context con = LoginActivity.this;
		/*if(GpsUtils.isOPen(con)){
			if(gpsInstance.getLocation() != null){
				Toast.makeText(this, "经度:"+gpsInstance.getLocation().getLongitude()+",纬度："+gpsInstance.getLocation().getLatitude(), Toast.LENGTH_LONG).show();
			}
		}else{
			Toast.makeText(this, "未开gps", Toast.LENGTH_LONG).show();
			GpsUtils.openGPS(con);
		}*/
	}
	
	
	private class LoginAsynTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground (Void... params) {
        	String sReturn = null;
        	String xml = "";
			try {
				sReturn = WebserviceUtil.getLogin(usrname, passwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	return sReturn;
        }
        
        @Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);
            mProgress.dismiss();
            if(result == null || result.equals("0")){
				Toast.makeText(LoginActivity.this, "登录不成功", Toast.LENGTH_LONG).show();
			}else if(result.equals("1")){
				SharePreUtil.editSharepref("usrname",usrname);
				SharePreUtil.editSharepref("passwd",passwd);
				finish();
				Intent intent = new Intent();;
				if(ToolUtils.isVehManageDpt(LoginActivity.this)){
					intent.setClass(LoginActivity.this, ReportActivity.class);
				}else{
					intent.setClass(LoginActivity.this, MenuActivity.class);
				}
				startActivity(intent);
			}
        }
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            mProgress.setTitle("请稍等");
    		mProgress.setMessage("正在登陆...");
    		mProgress.setCancelable(false);
    		mProgress.show();
        }
    }


	
}