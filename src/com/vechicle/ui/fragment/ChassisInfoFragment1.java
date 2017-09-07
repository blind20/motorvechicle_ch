package com.vechicle.ui.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChassisInfoFragment1 extends Fragment {

	private View mView;
	private TextView tv_bh ;
	private TextView tv_dpid;
	private TextView tv_zzcmc;
	private TextView tv_dpxh;
	private TextView tv_dplb;
	private TextView tv_cpmc;
	private TextView tv_cpsb ;
	private TextView tv_rlzl;
	private TextView tv_yjbz;
	private TextView tv_zxxs;
	private TextView tv_zs;
	private TextView tv_zj;
	private TextView tv_gbthps;
	private TextView tv_lts;
	
	private String info;
	private int index;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.chassis_fragment1,(ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		tv_bh = (TextView)mView.findViewById(R.id.tv_bh);
		tv_dpid = (TextView)mView.findViewById(R.id.tv_dpid);
		tv_zzcmc = (TextView)mView.findViewById(R.id.tv_zzcmc);
		tv_dpxh = (TextView)mView.findViewById(R.id.tv_dpxh);
		tv_dplb = (TextView)mView.findViewById(R.id.tv_dplb);
		tv_cpmc = (TextView)mView.findViewById(R.id.tv_cpmc);
		tv_cpsb = (TextView)mView.findViewById(R.id.tv_cpsb);
		tv_rlzl = (TextView)mView.findViewById(R.id.tv_rlzl);
		tv_yjbz = (TextView)mView.findViewById(R.id.tv_yjbz);
		tv_zxxs = (TextView)mView.findViewById(R.id.tv_zxxs);
		tv_zs = (TextView)mView.findViewById(R.id.tv_zs);
		tv_zj = (TextView)mView.findViewById(R.id.tv_zj);
		tv_gbthps = (TextView)mView.findViewById(R.id.tv_gbthps);
		tv_lts = (TextView)mView.findViewById(R.id.tv_lts);
		
		try {
			initView(info,index);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup viewgroup = (ViewGroup) mView.getParent();
		if(viewgroup != null){
			viewgroup.removeAllViewsInLayout();
		}
		return mView;
	}

	public void initView(String info,int index) throws JSONException {
		if(info == null){
			return;
		}
		Log.i("initView", "initView:"+index);
		JSONArray jsonArray = new JSONObject(info).getJSONArray("data");
		JSONObject jo = jsonArray.getJSONObject(index);
		tv_bh.setText(jo.getString("BH"));
		Log.i("initView2", "initView2:"+jo.getString("BH"));
		tv_dpid.setText(jo.getString("DPID"));
		tv_zzcmc.setText(jo.getString("ZZCMC"));
		tv_dpxh.setText(jo.getString("DPXH"));
		tv_dplb.setText(jo.getString("DPLB"));
		tv_cpmc.setText(jo.getString("CPMC"));
		tv_cpsb.setText(jo.getString("CPSB"));
		tv_rlzl.setText(jo.getString("RLZL"));
		tv_yjbz.setText(jo.getString("YJBZ"));
		tv_zxxs.setText(jo.getString("ZXXS"));
		tv_zs.setText(jo.getString("ZS"));
		tv_zj.setText(jo.getString("ZJ"));
		tv_gbthps.setText(jo.getString("GBTHPS"));
		tv_lts.setText(jo.getString("LTS"));

	}
}
