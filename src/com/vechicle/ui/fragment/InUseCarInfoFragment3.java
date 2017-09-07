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

public class InUseCarInfoFragment3 extends Fragment {

	private View mView;
	private TextView tv_zbzl ;
	private TextView tv_hdzzl;
	private TextView tv_hdzk;
	private TextView tv_zqyzl;
	private TextView tv_qpzk;
	private TextView tv_hpzk;
	private TextView tv_hbdbqk ;
	private TextView tv_ccrq;
	private TextView tv_djrq;

	
	private JSONObject jsonObj;
	private String info;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.inusecar_fragment3,(ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		tv_zbzl = (TextView)mView.findViewById(R.id.tv_zbzl);
		tv_hdzzl = (TextView)mView.findViewById(R.id.tv_hdzzl);
		tv_hdzk = (TextView)mView.findViewById(R.id.tv_hdzk);
		tv_zqyzl = (TextView)mView.findViewById(R.id.tv_zqyzl);
		tv_qpzk = (TextView)mView.findViewById(R.id.tv_qpzk);
		tv_hpzk = (TextView)mView.findViewById(R.id.tv_hpzk);
		tv_hbdbqk = (TextView)mView.findViewById(R.id.tv_hbdbqk);
		tv_ccrq = (TextView)mView.findViewById(R.id.tv_ccrq);
		tv_djrq = (TextView)mView.findViewById(R.id.tv_djrq);
		
		try {
			initView(info);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void initView(String info) throws JSONException {

		if (info == null) {
			return;
		}
		JSONObject jsonObj = new JSONObject(info);
		tv_zbzl.setText(jsonObj.getString("zbzl"));
		tv_hdzzl.setText(jsonObj.getString("hdzzl"));
		tv_hdzk.setText(jsonObj.getString("hdzk"));
		tv_zqyzl.setText(jsonObj.getString("zqyzl"));
		tv_qpzk.setText(jsonObj.getString("qpzk"));
		tv_hpzk.setText(jsonObj.getString("hpzk"));
		tv_hbdbqk.setText(jsonObj.getString("hbdbqk"));
		tv_ccrq.setText(jsonObj.getString("ccrq"));
		tv_djrq.setText(jsonObj.getString("djrq"));
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

	
}
