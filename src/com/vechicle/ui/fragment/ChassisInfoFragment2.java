package com.vechicle.ui.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChassisInfoFragment2 extends Fragment {

	private View mView;
	private TextView tv_ltgg ;
	private TextView tv_qlj;
	private TextView tv_hlj;
	private TextView tv_wkcc;
	private TextView tv_zzl;
	private TextView tv_zbzl;
	private TextView tv_zqyzl ;
	private TextView tv_fdjxh;
	private TextView tv_pl;
	private TextView tv_gl;
	private TextView tv_sbdh;
	private TextView tv_pc;
	
	private String info;
	private int index;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.chassis_fragment2,(ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		tv_ltgg = (TextView)mView.findViewById(R.id.tv_ltgg);
		tv_qlj = (TextView)mView.findViewById(R.id.tv_qlj);
		tv_hlj = (TextView)mView.findViewById(R.id.tv_hlj);
		tv_wkcc = (TextView)mView.findViewById(R.id.tv_wkcc);
		tv_zzl = (TextView)mView.findViewById(R.id.tv_zzl);
		tv_zbzl = (TextView)mView.findViewById(R.id.tv_zbzl);
		tv_zqyzl  = (TextView)mView.findViewById(R.id.tv_zqyzl );
		tv_fdjxh = (TextView)mView.findViewById(R.id.tv_fdjxh);
		tv_pl = (TextView)mView.findViewById(R.id.tv_pl);
		tv_gl = (TextView)mView.findViewById(R.id.tv_gl);
		tv_sbdh = (TextView)mView.findViewById(R.id.tv_sbdh);
		tv_pc = (TextView)mView.findViewById(R.id.tv_pc);

		
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
		JSONArray jsonArray = new JSONObject(info).getJSONArray("data");
		JSONObject jsonObj = jsonArray.getJSONObject(index);
		tv_ltgg.setText(jsonObj.getString("LTGG"));
		tv_qlj.setText(jsonObj.getString("QLJ"));
		tv_wkcc.setText(jsonObj.getString("C")+","+jsonObj.getString("K")+","+jsonObj.getString("G"));
		tv_zzl.setText(jsonObj.getString("ZZL"));
		tv_zbzl.setText(jsonObj.getString("ZBZL"));
		tv_zqyzl.setText(jsonObj.getString("ZQYZL"));
		tv_fdjxh.setText(jsonObj.getString("FDJXH"));
		tv_pl.setText(jsonObj.getString("PL"));
		tv_gl.setText(jsonObj.getString("GL"));
		tv_sbdh.setText(jsonObj.getString("SBDH"));
		tv_pc.setText(jsonObj.getString("PC"));
	}
}
