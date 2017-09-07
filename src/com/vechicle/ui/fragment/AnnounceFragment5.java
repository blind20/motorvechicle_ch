package com.vechicle.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vechicle.R;
import com.vechicle.domain.GongGaoFull;
import com.vechicle.service.ImageService;
import com.vechicle.util.ToolUtils;
import com.vechicle.util.WebserviceUtil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnnounceFragment5 extends Fragment {

	private View mView;
	private LinearLayout ll_hor_sv;
	private JSONObject gongGao;
	private List<Bitmap> bmps;
	private Context context;
	private ProgressDialog progressDialog;
	
	private String returnState;
	
	private int imgCount=0;
	
	String[] urls;
	
	public void setGongGao(JSONObject gongGao) {
		this.gongGao = gongGao;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mView = inflater.inflate(R.layout.fragment_announce5, (ViewGroup) getActivity().findViewById(R.id.viewpager),false);
		ll_hor_sv = (LinearLayout) mView.findViewById(R.id.ll_hor_sv);
		context = getActivity();
		progressDialog = new ProgressDialog(context);
		try {
			downloadImg();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void downloadImg() throws JSONException{
		String prefix = ToolUtils.getServerURL();
		JSONArray ja = gongGao.getJSONArray("ZP");
		if(ja==null||ja.length()==0)
			return;
		urls = new String[ja.length()];
		for(int i=0;i<ja.length();i++){
			urls[i] = prefix + ja.get(i).toString();
			Log.i("urls", urls[i]);
			
		}
		for(String url:urls){
			new LoadImgAsynTask().execute(url);
		}
		
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
		Log.i("onResume", "onResume");
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		progressDialog.dismiss();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStart() {
		Log.i("onStart", "onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	
	private class LoadImgAsynTask extends AsyncTask<String, Void, List<Bitmap>>{

		@Override
		protected List<Bitmap> doInBackground(String... params) {
			Log.i("LoadImgAsynTask", "msg:"+params.length);
			List<Bitmap> bmps = new ArrayList<Bitmap>();
			
			try {
				Log.i("LoadImgAsynTask", "msg:"+params[0]);
				byte[] data = ImageService.getImage(params[0]);
				Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
					bmps.add(bmp);
			} catch (Exception e) {
				Log.e("网络异常", "加载公告图片异常", e);
				returnState="400";
				return null;
			}
			returnState="200";
			return bmps;
		}

		@Override
		protected void onPostExecute(List<Bitmap> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			
			if("400".equals(returnState)){
				new AlertDialog.Builder(context).setTitle("警告").setMessage("网络异常，请检查网络！")
                .setPositiveButton("确定", null).create().show();
				return;
			}
			if("200".equals(returnState)){
				if(result.size()==0){
					new AlertDialog.Builder(context).setTitle("提示").setMessage("该公告不存在照片信息")
	                .setPositiveButton("确定", null).create().show();
				}else{
					for(Bitmap bmp : result){
						setImageView(bmp);
					}
					imgCount++;
				}
			}
			
			if(imgCount==urls.length){
				progressDialog.dismiss();
			}
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setTitle("请稍等");
			progressDialog.setMessage("加载照片...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
	}

	private View setImageView(Bitmap bmp){
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams( new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setPadding(0, 5, 0, 5);
        imageView.setImageBitmap(bmp);
        ll_hor_sv.addView(imageView);
          
        return ll_hor_sv;
	}

	public List<Bitmap> getBmps() {
		return bmps;
	}

	public void setBmps(List<Bitmap> bmps) {
		this.bmps = bmps;
	}
	
	
}
