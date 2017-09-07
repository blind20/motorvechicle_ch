package com.vechicle.control;

import java.util.List;
import java.util.Map;

import com.vechicle.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PullCarAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Map<String,String>> list;
	private Context context;
	
	public PullCarAdapter(List<Map<String,String>> list, Context context) {
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	
	
	public List<Map<String, String>> getList() {
		return list;
	}



	public void setList(List<Map<String, String>> list) {
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
			convertView = mInflater.inflate(R.layout.listview_pullcar_item, null);
			viewHolder.tv_hphm = (TextView) convertView.findViewById(R.id.tv_hphm);
			viewHolder.tv_hpzl = (TextView) convertView.findViewById(R.id.tv_hpzl);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_hphm.setText(list.get(position).get("vehCode"));
		viewHolder.tv_hpzl.setText(list.get(position).get("vehCodeType"));
		viewHolder.tv_name.setText(list.get(position).get("vehUnit"));
		
		return convertView;
	}
	
	private final class ViewHolder{
		public TextView tv_hphm;
		public TextView tv_hpzl;
		public TextView tv_name;
	}

}
