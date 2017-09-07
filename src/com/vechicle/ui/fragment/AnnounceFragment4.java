package com.vechicle.ui.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AnnounceFragment4 extends Fragment {

	private View mView;
	
	private JSONObject gongGao;
	private String qlj;
	private String hlj;
	private String zj;
	
	private TextView tv_clggbh;
	private TextView tv_zps ;
	private TextView tv_ggrq ;
	private TextView tv_ggsxrq ;
	private TextView tv_tzscrq;
	private TextView tv_bz ;
	private TextView tv_hgz_lj;
	private TextView tv_hgz_zj ;
	

	public void setGongGao(JSONObject gongGao) {
		this.gongGao = gongGao;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.fragment_announce4, (ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		tv_clggbh = (TextView)mView.findViewById(R.id.tv_clggbh);
		tv_zps = (TextView)mView.findViewById(R.id.tv_zps);
		tv_ggrq = (TextView)mView.findViewById(R.id.tv_ggrq);
		tv_ggsxrq = (TextView)mView.findViewById(R.id.tv_ggsxrq);
		tv_tzscrq = (TextView)mView.findViewById(R.id.tv_tzscrq);
		tv_bz = (TextView)mView.findViewById(R.id.tv_bz);
		tv_bz.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_hgz_lj = (TextView)mView.findViewById(R.id.tv_hgz_lj);
		tv_hgz_zj = (TextView)mView.findViewById(R.id.tv_hgz_zj);
		try {
			initView(gongGao);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void initView(JSONObject gongGao) throws JSONException {
		tv_clggbh.setText(gongGao.getString("CLGGBH"));
		tv_zps.setText(gongGao.getString("ZPS"));
		tv_ggrq.setText(gongGao.getString("GGRQ"));
		tv_ggsxrq.setText(gongGao.getString("GGSXRQ"));
		tv_tzscrq.setText(gongGao.getString("TZSCRQ"));
		tv_bz.setText(gongGao.getString("BZ"));
		tv_hgz_lj.setText(getQlj()+"," +getHlj());
		tv_hgz_zj.setText(getZj());
	}
	

	public String getQlj() {
		return qlj;
	}

	public void setQlj(String qlj) {
		this.qlj = qlj;
	}

	public String getHlj() {
		return hlj;
	}

	public void setHlj(String hlj) {
		this.hlj = hlj;
	}

	public String getZj() {
		return zj;
	}

	public void setZj(String zj) {
		this.zj = zj;
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
