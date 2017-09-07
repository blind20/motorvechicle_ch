package com.vechicle.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.vechicle.Constant;
import com.vechicle.R;
import com.vechicle.control.PullCarAdapter;
import com.vechicle.control.SelectCarAdapter;
import com.vechicle.util.ToolUtils;
import com.vechicle.util.WebserviceUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WaitSelectCarActivity extends Activity {

	protected static final int DIALOG = 0;
	private ListView listview;
	private List<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();
	private SelectCarAdapter adapter;
	private Map<String,String> carInfo;
	private TextView tv_car_title;
	private LinearLayout ll_spinner;
	private Spinner spinner;
	private String[] spinnerItems;
	private int mSpinnerPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_pullcar);
		tv_car_title = (TextView) this.findViewById(R.id.tv_car_title);
		tv_car_title.setText("请选择查验车辆");
		
		ll_spinner = (LinearLayout) this.findViewById(R.id.ll_spinner);
		ll_spinner.setVisibility(View.VISIBLE);
		
		spinner = (Spinner) this.findViewById(R.id.spinner);
		spinnerItems = getBefore7Date(new Date());
		ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerItems);
		spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
		spinner.setAdapter(spinnerAdapter);
		
		listview = (ListView) this.findViewById(R.id.lv_pullcar);
		
	    adapter = new SelectCarAdapter(mData,this);
	
	    listview.setAdapter(adapter);
	    
		listview.setOnItemClickListener(listener);
	    
	    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mSpinnerPosition = position;
				new CarListInfoAsyn().execute(spinnerItems[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			HashMap<String, Object> hashmap = (HashMap<String, Object>) mData.get(position);;
			
			Intent data = new Intent();
			data.putExtra("carinfo", hashmap);
			setResult(33, data);
			WaitSelectCarActivity.this.finish();
		}
	};

	private class CarListInfoAsyn extends AsyncTask<String, Void, String>{
		private ProgressDialog mProgress ;
		private int nStatus = Constant.NO_RESPONSE;
		@Override
		protected String doInBackground(String... params) {
			String cars = null;
			try {
				Log.w("getCheckLoginInfo", "getCheckLoginInfo:");
				cars = WebserviceUtil.invokeWebservice(params[0], "getCheckLoginInfo2");
				Log.w("getCheckLoginInfo", "cars:"+params[0]);
//				cars="ces";
				if(ToolUtils.isNotEmptyString(cars)){
					nStatus = Constant.STATAS_OK;
					Log.w("CarListInfoAsyn", "cars:"+cars);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}  
			return cars;
		}
		
		@Override
        protected void onPostExecute (String cars) {
            super.onPostExecute(cars);
            
            if(nStatus == Constant.STATAS_OK){
            	mData = getData(cars);
            	adapter.setList(mData);
            	adapter.notifyDataSetChanged();
            }
            mProgress.dismiss();
        }
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            mProgress = new ProgressDialog(WaitSelectCarActivity.this);
    		mProgress.setMessage("读取车辆列表信息...");
    		mProgress.setCancelable(false);
            mProgress.show();
        }
	}
	
	
	
	private List<Map<String,Object>> getData(String cars) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			JSONArray ja = new JSONArray(cars);
			for (int i = 0; i < ja.length(); i++) {
				HashMap<String, Object> hashmap = new HashMap<String, Object>();
				JSONObject jo = (JSONObject) ja.getJSONObject(i);
				hashmap.put("hphm", (String) jo.get("hphm"));
				hashmap.put("hpzl", (String) jo.get("hpzl"));
				hashmap.put("clsbdh", (String) jo.get("clsbdh"));
				hashmap.put("jcxdh", (String) jo.get("jcxdh"));
				hashmap.put("jycs", (String) jo.get("jycs"));
				hashmap.put("jylsh", (String) jo.get("jylsh"));
				hashmap.put("Status", (String) jo.get("Status"));
				
				
				if(ToolUtils.isNotEmptyString(jo.getString("XML"))){
					JSONObject xml2json = XML.toJSONObject(jo.getString("XML")).getJSONObject("root").getJSONObject("head");
//					String[] cyzps = xml2json.getString("cyzp").split(",");
					String[] wgjyzps = xml2json.getString("wgjyzp").split(",");
//					ArrayList<String> zps =  (ArrayList<String>) deReplication(cyzps,wgjyzps);
					ArrayList<String> zps = new ArrayList<String> (Arrays.asList(wgjyzps));
					hashmap.put("zps", zps);
					
					Log.w("xml2json", "xml:"+xml2json.getString("cyzp")+";zpsLen="+zps.size());
				}
				list.add(hashmap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 数组去重复
	 * @param cyzp
	 * @param wgjyzp
	 * @return
	 */
	private List<String> deReplication(String[] cyzps, String[] wgjyzps) {
		
		//通过Arrays.asList方法得到的List是只读,所以不能直接使用
		List<String> zps = new ArrayList<String> (Arrays.asList(wgjyzps));
		boolean addFlag;
		for(String cyzp: cyzps ){
			addFlag = true;
			for(String wgjyzp : wgjyzps){
				if(wgjyzp.equals(cyzp)){
					addFlag = false;
					break;
				}
			}
			if(addFlag){
				zps.add(cyzp);
			}
		}
		return zps;
	}
	
	

	public void backMain(View view){
		WaitSelectCarActivity.this.finish();
	}
	
	public void onRefresh(View view)throws Exception{
		new CarListInfoAsyn().execute(spinnerItems[mSpinnerPosition]);
	}
	
	private String[] getBefore7Date(Date dNow){
		String[] sevenDates = new String[30];
		sevenDates[0] = ToolUtils.dateToString(dNow);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dNow);
		for(int i =1; i<30;i++){
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date dBefore = calendar.getTime();
			sevenDates[i] = ToolUtils.dateToString(dBefore);
		}
		
		return sevenDates;
	}
	
	
}
