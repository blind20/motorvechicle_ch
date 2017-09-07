package com.vechicle.ui.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InUseCarInfoFragment2 extends Fragment {

	private View mView;
	private TextView tv_rlzl ;
	private TextView tv_pl;
	private TextView tv_gl;
	private TextView tv_zxxs;
	private TextView tv_wkcc;
	private TextView tv_hxnbcc;
	private TextView tv_gbthps ;
	private TextView tv_zs;
	private TextView tv_zj;
	private TextView tv_qlj;
	private TextView tv_hlj;
	private TextView tv_ltgg;
	private TextView tv_lts;
	private TextView tv_zzl;
	
	private JSONObject jsonObj;
	private String info;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.inusecar_fragment2,(ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		tv_rlzl = (TextView)mView.findViewById(R.id.tv_rlzl);
		tv_pl = (TextView)mView.findViewById(R.id.tv_pl);
		tv_gl = (TextView)mView.findViewById(R.id.tv_gl);
		tv_zxxs = (TextView)mView.findViewById(R.id.tv_zxxs);
		tv_wkcc = (TextView)mView.findViewById(R.id.tv_wkcc);
		tv_hxnbcc = (TextView)mView.findViewById(R.id.tv_hxnbcc);
		tv_gbthps = (TextView)mView.findViewById(R.id.tv_gbthps);
		tv_zs = (TextView)mView.findViewById(R.id.tv_zs);
		tv_zj = (TextView)mView.findViewById(R.id.tv_zj);
		tv_qlj = (TextView)mView.findViewById(R.id.tv_qlj);
		tv_hlj = (TextView)mView.findViewById(R.id.tv_hlj);
		tv_ltgg = (TextView)mView.findViewById(R.id.tv_ltgg);
		tv_lts = (TextView)mView.findViewById(R.id.tv_lts);
		tv_zzl = (TextView)mView.findViewById(R.id.tv_zzl);
		
		try {
			initView(info);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setInfo(String info) {
		this.info = info;
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

	public void initView(String info) throws JSONException {
		
		if(info == null){
			return;
		}
		JSONObject jsonObj = new JSONObject(info);
		tv_rlzl.setText(jsonObj.getString("rlzl"));
		tv_pl.setText(jsonObj.getString("pl"));
		tv_gl.setText(jsonObj.getString("gl"));
		tv_zxxs.setText(jsonObj.getString("zxxs"));
		
		tv_wkcc.setText(jsonObj.getString("cwkc")+",(长)"+jsonObj.getString("cwkk")+",(宽)"+jsonObj.getString("cwkg")+",(高)");
		tv_hxnbcc.setText(jsonObj.getString("hxnbcd")+",(长)"+jsonObj.getString("hxnbkd")+",(宽)"+jsonObj.getString("hxnbgd")+",(高)");
		
		tv_gbthps.setText(jsonObj.getString("gbthps"));
		tv_zs.setText(jsonObj.getString("zs"));
		tv_zj.setText(jsonObj.getString("zj"));
		tv_qlj.setText(jsonObj.getString("qlj"));
		tv_hlj.setText(jsonObj.getString("hlj"));
		tv_ltgg.setText(jsonObj.getString("ltgg"));
		tv_lts.setText(jsonObj.getString("lts"));
		tv_zzl.setText(jsonObj.getString("zzl"));
	}
}
