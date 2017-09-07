package com.vechicle.ui.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;
import com.vechicle.domain.GongGaoFull;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AnnounceFragment2 extends Fragment {

	private View mView;
	
	private JSONObject gongGao;
	
	private TextView tv_hxnbcd ;
	private TextView tv_gbthps ;
	private TextView tv_zs ;
	private TextView tv_zj ;
	private TextView tv_qlj;
	private TextView tv_lts ;
	private TextView tv_ltgg ;
	private TextView tv_zzl ;
	private TextView tv_zbzl;
	private TextView tv_hdzzl ;
	private TextView tv_zqyzl;
	private TextView tv_hdzk;
	
	public void setGongGao(JSONObject gongGao) {
		this.gongGao = gongGao;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.fragment_announce2, (ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		
		tv_hxnbcd = (TextView)mView.findViewById(R.id.tv_hxnbcd);
		tv_gbthps = (TextView)mView.findViewById(R.id.tv_gbthps);
		tv_zs = (TextView)mView.findViewById(R.id.tv_zs);
		tv_zj = (TextView)mView.findViewById(R.id.tv_zj);
		tv_qlj = (TextView)mView.findViewById(R.id.tv_qlj);
		tv_lts = (TextView)mView.findViewById(R.id.tv_lts);
		tv_ltgg = (TextView)mView.findViewById(R.id.tv_ltgg);
		tv_zzl = (TextView)mView.findViewById(R.id.tv_zzl);
		tv_zbzl = (TextView)mView.findViewById(R.id.tv_zbzl);
		tv_hdzzl = (TextView)mView.findViewById(R.id.tv_hdzzl);
		tv_zqyzl = (TextView)mView.findViewById(R.id.tv_zqyzl);
		tv_hdzk = (TextView)mView.findViewById(R.id.tv_hdzk);
		try {
			initView(gongGao);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void initView(JSONObject gongGao) throws JSONException {
		
		
		if(gongGao.getString("HXNBCD")!=null && !"".equals(gongGao.getString("HXNBCD"))){
			tv_hxnbcd.setText(gongGao.getString("HXNBCD")+" mm(长),"+gongGao.getString("HXNBKD")
					+" mm(宽),"+gongGao.getString("HXNBGD")+" mm(高)");
		}
		if(gongGao.getString("GBTHPS")!=null && !"".equals(gongGao.getString("GBTHPS")))
			tv_gbthps.setText(gongGao.getString("GBTHPS")+" 片");
		tv_zs.setText(gongGao.getString("ZS"));
		tv_zj.setText(gongGao.getString("ZJ"));
		
		if(gongGao.getString("QLJ")!=null && !"".equals(gongGao.getString("QLJ")))
			tv_qlj.setText(gongGao.getString("QLJ")+" mm(前) "+gongGao.getString("HLJ")+" mm(后)");
		tv_lts.setText(gongGao.getString("LTS"));
		tv_ltgg.setText(gongGao.getString("LTGG"));
		tv_zzl.setText(gongGao.getString("ZZL"));
		tv_zbzl.setText(gongGao.getString("ZBZL"));
		tv_hdzzl.setText(gongGao.getString("HDZZL"));
		tv_zqyzl.setText(gongGao.getString("ZQYZL"));
		tv_hdzk.setText(gongGao.getString("HDZK"));
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
