package com.vechicle.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;
import com.vechicle.control.PullCarAdapter;
import com.vechicle.util.ToolUtils;
import com.vechicle.util.WebserviceUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PullCarActivity extends Activity {

	protected static final int DIALOG = 0;
	private ListView listview;
	private List<Map<String,String>> mData= new ArrayList<Map<String,String>>();
	private String workers;
	private PullCarAdapter adapter;
	private Map<String,String> carInfo;
	private String ycyName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_list_pullcar);
		listview = (ListView) this.findViewById(R.id.lv_pullcar);
		
	    adapter = new PullCarAdapter(mData,this);
	
	    listview.setAdapter(adapter);
	    listview.setOnItemClickListener(listener);
	    new CarListInfoAsyn().execute();
	}

	private class CarListInfoAsyn extends AsyncTask<String, Void, String>{
		private ProgressDialog mProgress ;
		private String status = "400" ;
		@Override
		protected String doInBackground(String... params) {
			String cars = null;
			try {
				cars = WebserviceUtil.invokeWebservice(null, "getDetStatusList");
				workers = WebserviceUtil.invokeWebservice(null, "getDriversList");
				if(ToolUtils.isNotEmptyString(cars) && ToolUtils.isNotEmptyString(workers)){
					status = "200";
					Log.i("CarListInfoAsyn", "workers:"+workers);
					Log.i("CarListInfoAsyn", "cars:"+cars);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}  
			return cars;
		}
		
		@Override
        protected void onPostExecute (String cars) {
            super.onPostExecute(cars);
            if("200".equals(status)){
            	mData = getData(cars);
            	adapter.setList(mData);
            	adapter.notifyDataSetChanged();
            }
            mProgress.dismiss();
        }
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            mProgress = new ProgressDialog(PullCarActivity.this);
            mProgress.setTitle("请稍等");
    		mProgress.setMessage("读取车辆列表信息...");
    		mProgress.setCancelable(false);
            mProgress.show();
        }
	}
	
	public String[] getWorkers(String workers){
		String[] workList = null;
		try {
			JSONArray ja = new JSONArray(workers);
			workList = new String[ja.length()];
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = (JSONObject) ja.getJSONObject(i);
				workList[i] = jo.getString("DriverName");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return workList;
	}
	
	
	private List<Map<String,String>> getData(String cars) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			JSONArray ja = new JSONArray(cars);
			for (int i = 0; i < ja.length(); i++) {
				HashMap<String, String> hashmap = new HashMap<String, String>();
				JSONObject jo = (JSONObject) ja.getJSONObject(i);
				hashmap.put("vehCode", (String) jo.get("VehCode"));
				hashmap.put("vehCodeType", (String) jo.get("VehCodeType"));
				hashmap.put("vehUnit", (String) jo.get("VehUnit"));
				hashmap.put("registCode", (String) jo.get("regist_code"));
				hashmap.put("lineCalss", (String) jo.get("DetLineSelect"));
				list.add(hashmap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void backMain(View view){
		PullCarActivity.this.finish();
	}
	
	public void onRefresh(View view)throws Exception{
		new CarListInfoAsyn().execute();
	}
	
	
	OnItemClickListener listener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			carInfo = mData.get(position);
			
			showDialog(DIALOG);
		}
	};
	
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final String[] workerList = getWorkers(workers);
		switch (id) {
		case DIALOG:
			
			builder.setTitle(carInfo.get("vehCode"));
			builder.setSingleChoiceItems(workerList, 0,
					new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							ycyName = workerList[which];
						}
					});

			builder.setPositiveButton("引车上线",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							JSONObject jo= new JSONObject(carInfo);
							try {
								if(ycyName == null){
									ycyName = workerList[0];
								}
								jo.put("driver", ycyName);
								Log.i("onCreateDialog", "jo:"+jo.toString());
								new UpLineAsyn().execute(jo.toString());
								new CarListInfoAsyn().execute();
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
			dialog = builder.create();
			break;
		}
		return dialog;
	}
	
	private class UpLineAsyn extends AsyncTask<String, Void, String>{

		private ProgressDialog mProgress ;
		private String status = "400" ;
		
		protected String doInBackground(String... params) {
			String result = null;
			
			try {
				result = WebserviceUtil.invokeWebservice(params[0], "upLine");
			} catch (Exception e) {
				e.printStackTrace();
			}  
			return result;
		}
		
		@Override
        protected void onPostExecute (String param) {
            super.onPostExecute(param);
            if("200".equals(status)){
            }
            mProgress.dismiss();
        }
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            mProgress = new ProgressDialog(PullCarActivity.this);
            mProgress.setTitle("请稍等");
    		mProgress.setMessage("引车上线...");
    		mProgress.setCancelable(false);
            mProgress.show();
        }
	}
}
