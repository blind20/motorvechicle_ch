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

public class AnnounceFragment1 extends Fragment {

	private View mView;
	private JSONObject gongGao;
	private String fdjh;
	
	TextView tv_bh ;
	TextView tv_qyid;
	TextView tv_clpp1;
	TextView tv_clpp2;
	TextView tv_clxh;
	TextView tv_zzg;
	TextView tv_fdjxh ;
	TextView tv_sbdhxl;
	TextView tv_rlzl;
	TextView tv_zxxs;
	TextView tv_plgl;
	TextView tv_cwkc;
	TextView tv_fdjh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.fragment_announce1, (ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		tv_bh = (TextView)mView.findViewById(R.id.tv_bh);
		tv_qyid = (TextView)mView.findViewById(R.id.tv_qyid);
		tv_clpp1 = (TextView)mView.findViewById(R.id.tv_clpp1);
		tv_clpp2 = (TextView)mView.findViewById(R.id.tv_clpp2);
		tv_clxh = (TextView)mView.findViewById(R.id.tv_clxh);
		tv_zzg = (TextView)mView.findViewById(R.id.tv_zzg);
		tv_fdjxh = (TextView)mView.findViewById(R.id.tv_fdjxh);
		tv_sbdhxl = (TextView)mView.findViewById(R.id.tv_sbdhxl);
		tv_rlzl = (TextView)mView.findViewById(R.id.tv_rlzl);
		tv_zxxs = (TextView)mView.findViewById(R.id.tv_zxxs);
		tv_plgl = (TextView)mView.findViewById(R.id.tv_plgl);
		tv_cwkc = (TextView)mView.findViewById(R.id.tv_cwkc);
		tv_fdjh = (TextView)mView.findViewById(R.id.tv_fdjh);
		
		try {
			initView(gongGao);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	public void initView(JSONObject gongGao) throws JSONException {
		
		tv_bh.setText(gongGao.getString("BH"));
		tv_qyid.setText(gongGao.getString("QYID"));
		tv_clpp1.setText(gongGao.getString("CLPP1"));
		tv_clpp2.setText(gongGao.getString("CLPP2"));
		tv_clxh.setText(gongGao.getString("CLXH"));
		tv_zzg.setText(gongGao.getString("ZZG"));
		tv_fdjxh.setText(gongGao.getString("FDJXH"));
		tv_sbdhxl.setText(gongGao.getString("SBDHXL"));
		tv_rlzl.setText(gongGao.getString("RLZL"));
		tv_zxxs.setText(gongGao.getString("ZXXS"));
		if(gongGao.getString("PL")!=null && !"".equals(gongGao.getString("PL"))){
			tv_plgl.setText(gongGao.getString("PL")+" ml,"+gongGao.getString("GL")+" KW");
		}
		tv_cwkc.setText(gongGao.getString("CWKC")+"mm(³¤)  "+gongGao.getString("CWKK")+"mm(¿í)  "
				+gongGao.getString("CWKG")+"mm(¸ß)");
		tv_fdjh.setText(getFdjh());
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


	public void setGongGao(JSONObject gongGao) {
		this.gongGao = gongGao;
	}

	public String getFdjh() {
		return fdjh;
	}

	public void setFdjh(String fdjh) {
		this.fdjh = fdjh;
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	
}
