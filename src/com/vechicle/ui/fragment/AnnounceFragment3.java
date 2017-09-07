package com.vechicle.ui.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AnnounceFragment3 extends Fragment {

	private View mView;
	
	private JSONObject gongGao;
	private TextView tv_qpzk;
	private TextView tv_pc;
	private TextView tv_fgbsqy;
	private TextView tv_fgbssb;
	private TextView tv_fgbsxh;
	private TextView tv_hbdbqk;
	private TextView tv_dpqyxh;

	private TextView tv_clmc;
	private TextView tv_sfmj;
	private TextView tv_cllx;
	private TextView tv_mjyxqz;
	
	public void setGongGao(JSONObject gongGao) {
		this.gongGao = gongGao;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.fragment_announce3, (ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		tv_qpzk = (TextView)mView.findViewById(R.id.tv_qpzk);
		tv_pc = (TextView)mView.findViewById(R.id.tv_pc);
		tv_fgbsqy = (TextView)mView.findViewById(R.id.tv_fgbsqy);
		tv_fgbssb = (TextView)mView.findViewById(R.id.tv_fgbssb);
		tv_fgbsxh = (TextView)mView.findViewById(R.id.tv_fgbsxh);
		tv_hbdbqk = (TextView)mView.findViewById(R.id.tv_hbdbqk);
		tv_dpqyxh = (TextView)mView.findViewById(R.id.tv_dpqyxh);
		
		tv_clmc = (TextView)mView.findViewById(R.id.tv_clmc);
		tv_sfmj = (TextView)mView.findViewById(R.id.tv_sfmj);
		tv_cllx = (TextView)mView.findViewById(R.id.tv_cllx);
		tv_mjyxqz = (TextView)mView.findViewById(R.id.tv_mjyxqz);
		try {
			initView(gongGao);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void initView(JSONObject gongGao) throws JSONException {
		if(gongGao == null )
			Log.i("initView3", "aaa");
		if(mView == null )
			Log.i("initView3", "mView");
		if(tv_qpzk == null )
			Log.i("initView3", "1111");
		tv_qpzk.setText(gongGao.getString("QPZK"));
		tv_pc.setText(gongGao.getString("PC"));
		tv_fgbsqy.setText(gongGao.getString("FGBSQY"));
		tv_fgbssb.setText(gongGao.getString("FGBSSB"));
		tv_fgbsxh.setText(gongGao.getString("FGBSXH"));
		tv_hbdbqk.setText(gongGao.getString("HBDBQK"));
		tv_dpqyxh.setText(gongGao.getString("DPQYXH"));
		
		tv_clmc.setText(gongGao.getString("CLMC"));
		tv_sfmj.setText(gongGao.getString("SFMJ"));
		tv_cllx.setText(gongGao.getString("CLLX"));
		tv_mjyxqz.setText(gongGao.getString("MJYXQZ"));		
		
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
