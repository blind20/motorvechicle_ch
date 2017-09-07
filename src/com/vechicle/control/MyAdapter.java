package com.vechicle.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vechicle.R;
import com.vechicle.domain.ImageWrap;
import com.vechicle.util.DensityUtil;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private ArrayList<ImageWrap>arrayList;
	private Context context;
	private int screenW;
	private Map<String,String> map;
	private String[] photos;
	private String[] photocode;
	//被选择车辆信息的状态：status=0初次登陆;status=2需要补拍照片;
	private int status;
	public MyAdapter(Context context,ArrayList<ImageWrap>list,int status){
		this.context= context;
		this.arrayList= list;
		this.status = status;
		screenW = (context.getResources().getDisplayMetrics().widthPixels 
				- DensityUtil.dip2px(context,20))/3;
		photos = context.getResources().getStringArray(R.array.photoangles);
		photocode = context.getResources().getStringArray(R.array.photoangles_code); 
		map = new HashMap<String, String>();
		for(int i=0; i<photos.length; i++){
			map.put( photocode[i],photos[i]);
		}
	}
	
	@Override
	public int getCount() {
		return arrayList.size();
	}
	
	public void setData(ArrayList<ImageWrap> list){
		this.arrayList= list;
	}
	
	@Override
	public Object getItem(int position) {
		return null;
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.gridview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ivPhoto);
			viewHolder.iv_del = (ImageView) convertView.findViewById(R.id.iv_del);
			viewHolder.textview = (TextView) convertView.findViewById(R.id.tv_note);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.imageView.setScaleType(ScaleType.CENTER);
		viewHolder.imageView.setLayoutParams(new FrameLayout.LayoutParams(screenW, screenW));
		
//		viewHolder.textview.setText(arrayList.get(position).imageTypeCode);
//		viewHolder.iv_del.setVisibility(View.GONE);
//		viewHolder.imageView.setImageResource(R.drawable.add1);
		
		if (position < getCount() - 1) {
			viewHolder.textview.setText(map.get(arrayList.get(position).imageTypeCode));
			
			viewHolder.iv_del.setVisibility(View.GONE);
			viewHolder.iv_del.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					arrayList.remove(position);
					setData(arrayList);
					notifyDataSetChanged();
				}
				
			});
			if(arrayList.get(position).bitmap == null){
				viewHolder.imageView.setImageResource(R.drawable.add1);
			}else{
				viewHolder.imageView.setImageBitmap(arrayList.get(position).bitmap);
			}
		} else {
			if(status ==0){
				viewHolder.textview.setText("增拍");
			}else if(status == 2){
				viewHolder.textview.setText("补拍");
			}
			
			viewHolder.iv_del.setVisibility(View.GONE);
			viewHolder.imageView.setImageResource(R.drawable.add1);
		}
		return convertView;
	}
	
	class ViewHolder{
		ImageView imageView;
		ImageView iv_del;
		TextView textview;
	}
}
