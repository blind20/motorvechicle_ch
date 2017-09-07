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

public class ChassisInfoFragment3 extends Fragment {

	private View mView;
	private TextView tv_ggrq ;
	private TextView tv_ggsxrq;
	private TextView tv_tzscrq;
	private TextView tv_bz;

	
	private JSONObject jsonObj;
	private String info;
	private int index;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.chassis_fragment3,(ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		tv_ggrq = (TextView)mView.findViewById(R.id.tv_ggrq);
		tv_ggsxrq = (TextView)mView.findViewById(R.id.tv_ggsxrq);
		tv_tzscrq = (TextView)mView.findViewById(R.id.tv_tzscrq);
		tv_bz = (TextView)mView.findViewById(R.id.tv_bz);
		
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
		tv_ggrq.setText(jsonObj.getString("GGRQ"));
		tv_ggsxrq.setText(jsonObj.getString("GGSXRQ"));
		tv_tzscrq.setText(jsonObj.getString("TZSCRQ"));
		tv_bz.setText(jsonObj.getString("BZ"));
	}
}
