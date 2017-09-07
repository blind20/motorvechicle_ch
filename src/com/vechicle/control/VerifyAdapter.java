package com.vechicle.control;

import java.util.HashMap;
import java.util.List;

import com.vechicle.R;
import com.vechicle.domain.Vehicle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VerifyAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<HashMap<String,String>> list;
	private Context context;
	
	public VerifyAdapter(List<HashMap<String,String>> list, Context context) {
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	
	
	public List<HashMap<String, String>> getList() {
		return list;
	}



	public void setList(List<HashMap<String, String>> list) {
		this.list = list;
	}



	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_item, null);
			viewHolder.add_car_number = (TextView) convertView.findViewById(R.id.add_car_number);
			viewHolder.tvLictype = (TextView) convertView.findViewById(R.id.tvLictype);
			viewHolder.tvInsptype = (TextView) convertView.findViewById(R.id.tvInsptype);
			viewHolder.tv_disqualify = (TextView) convertView.findViewById(R.id.tv_disqualify);
			viewHolder.tv_verifydate = (TextView) convertView.findViewById(R.id.tv_verifydate);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.add_car_number.setText(list.get(position).get("carnumber"));
		viewHolder.tvLictype.setText(list.get(position).get("lictype"));
		viewHolder.tvInsptype.setText(list.get(position).get("insptype"));
		viewHolder.tv_disqualify.setText(list.get(position).get("disqualify"));
		viewHolder.tv_verifydate.setText(list.get(position).get("verifydate"));
		
		return convertView;
	}
	
	private final class ViewHolder{
		public TextView add_car_number;
		public TextView tvLictype;
		public TextView tvInsptype;
		public TextView tv_disqualify;
		public TextView tv_verifydate;
	}

}
