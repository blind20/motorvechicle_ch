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

public class InUseCarInfoFragment1 extends Fragment {

	private View mView;
	private TextView tv_hpzl ;
	private TextView tv_hphm;
	private TextView tv_clpp1;
	private TextView tv_clpp2;
	private TextView tv_gcjk;
	private TextView tv_zzg;
	private TextView tv_zzcmc ;
	private TextView tv_clxh;
	private TextView tv_clsbdh;
	private TextView tv_fdjh;
	private TextView tv_cllx;
	private TextView tv_csys;
	private TextView tv_syxz;
	private TextView tv_fdjxh;
	
	private JSONObject jsonObj;
	private String info;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.inusecar_fragment1,(ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		tv_hpzl = (TextView)mView.findViewById(R.id.tv_hpzl);
		tv_hphm = (TextView)mView.findViewById(R.id.tv_hphm);
		tv_clpp1 = (TextView)mView.findViewById(R.id.tv_clpp1);
		tv_clpp2 = (TextView)mView.findViewById(R.id.tv_clpp2);
		tv_gcjk = (TextView)mView.findViewById(R.id.tv_gcjk);
		tv_zzg = (TextView)mView.findViewById(R.id.tv_zzg);
		tv_zzcmc = (TextView)mView.findViewById(R.id.tv_zzcmc);
		tv_clxh = (TextView)mView.findViewById(R.id.tv_clxh);
		tv_clsbdh = (TextView)mView.findViewById(R.id.tv_clsbdh);
		tv_fdjh = (TextView)mView.findViewById(R.id.tv_fdjh);
		tv_cllx = (TextView)mView.findViewById(R.id.tv_cllx);
		tv_csys = (TextView)mView.findViewById(R.id.tv_csys);
		tv_syxz = (TextView)mView.findViewById(R.id.tv_syxz);
		tv_fdjxh = (TextView)mView.findViewById(R.id.tv_fdjxh);
		
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
		tv_hpzl.setText(jsonObj.getString("hpzl"));
		tv_hphm.setText(jsonObj.getString("hphm"));
		tv_clpp1.setText(jsonObj.getString("clpp1"));
		tv_clpp2.setText(jsonObj.getString("clpp2"));
		tv_gcjk.setText(jsonObj.getString("gcjk"));
		tv_zzg.setText(jsonObj.getString("zzg"));
		tv_zzcmc.setText(jsonObj.getString("zzcmc"));
		tv_clxh.setText(jsonObj.getString("clxh"));
		tv_clsbdh.setText(jsonObj.getString("clsbdh"));
		tv_fdjh.setText(jsonObj.getString("fdjh"));
		tv_cllx.setText(jsonObj.getString("cllx"));
		tv_csys.setText(jsonObj.getString("csys"));
		tv_syxz.setText(jsonObj.getString("syxz"));
		tv_fdjxh.setText(jsonObj.getString("fdjxh"));
	}
}
