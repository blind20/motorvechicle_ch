package com.vechicle.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.vechicle.R;
import com.vechicle.control.GridAdapter;
import com.vechicle.db.DBHelper;
import com.vechicle.db.ImageDao;
import com.vechicle.domain.CarPhotoEntity;
import com.vechicle.domain.ImageWrap;
import com.vechicle.domain.Vehicle;
import com.vechicle.util.DensityUtil;
import com.vechicle.util.PictureUtil;
import com.vechicle.util.SharePreUtil;
import com.vechicle.util.ToolUtils;
import com.vechicle.util.TransformatUtils;
import com.vechicle.util.WebserviceUtil;

public class TakePictureActivity extends Activity implements OnClickListener{

	private Button ivSend ;
	private String imgFileDir = Environment.getExternalStorageDirectory().toString() + "/vehicle/";
	private String imgfilepath;
	private File imgFile;
	
	private GridView gridView;
	private ArrayList<ImageWrap> aList;
	private GridAdapter adapter;
	private Context context;
//	private Bitmap bmp;
	private ImageWrap addImgButton;
	private long recordIndex;
	private Vehicle vehicle;
	String[] photos;
	String[] photocode;
	private ProgressDialog mProgress;
	private String[] images;
	private String[] imageScales;
	
	private String lsh;
	private String hphm;
	
	private String applyUnit;
	private String[] units;
	
	private HashMap<String, Object> carinfoMap ;
	private ArrayList<String> zpsList;
	private int nClickPosition;
	
	
	
	//拍照原图
	private final static int OriginType = 0;
	//处理过后的上传图
	private final static int UploadType = 1;
	//缩略图
	private final static int ThumbnaiType = 2;
		
	private final static int UploadQuality =80;
	private final static int ThumbnailQuality =30;
	public static final int REQ_CAMERA_DATA = 100;
	public static final int REQ_LONG_CLICK = 101;
	public static final String PHOTO_IS_MUST = "1";
	public static final String PHOTO_NOT_MUST = "0";
	
	private List<CarPhotoEntity> mInitList;
	private List<CarPhotoEntity> photosList;
	private int mPosition;
	private String timeStamp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context= TakePictureActivity.this;
		mInitList = new ArrayList<CarPhotoEntity>();
		photosList = new ArrayList<CarPhotoEntity>();
		Intent intent = this.getIntent();
		vehicle = (Vehicle)intent.getSerializableExtra("vehicle");
		lsh = intent.getExtras().getString("lsh");
		carinfoMap = (HashMap<String, Object>) intent.getExtras().get("carInfo");
		zpsList = (ArrayList<String>)carinfoMap.get("zps");
		recordIndex = vehicle.getVehicleid();
		Log.i("recordIndex", recordIndex+"");
		
		initView();
		
		photos = getResources().getStringArray(R.array.photoangles);
		photocode = getResources().getStringArray(R.array.photoangles_code);
		ivSend = (Button)findViewById(R.id.send);
		ivSend.setOnClickListener(this);
		
		
		applyUnit = SharePreUtil.getSharepref("applyunits");
		units = getResources().getStringArray(R.array.applyunits);
		
		
	}
	
	private void initView() {
		
		setContentView(R.layout.activity_report_camera);
		
		
		imgFile = null;
		
		Integer status = Integer.parseInt((String) carinfoMap.get("Status"));
		if(status == null){
			status = 0;
		}
		
		if(status ==2){
			zpsList = null;
		}
			
		
		gridView=(GridView) findViewById(R.id.grid);
		
		fullPhoto();
		
		mProgress = new ProgressDialog(this);
	}
	
	
	private void fullPhoto(){
		mInitList = initPhotoGrid();
		viewSetAdapter();
	}
	
	
	
	private void viewSetAdapter() {
		adapter=new GridAdapter(context, mInitList, 0);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(listenClick);
//		gridView.setOnItemLongClickListener(listenLongClick);
	}

	private List<CarPhotoEntity> initPhotoGrid() {
		List<CarPhotoEntity> list = initPhotoList(this);
		return list;
	}



	private OnItemClickListener listenClick = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			mPosition = position;
			
			if(!TextUtils.isEmpty(mInitList.get(position).getUploadPhotoFilePath())){
				//查看照片
				viewImgHasPhoto(position);
			}else{
				//拍摄照片
				if(getSdcarState()){
					timeStamp = getCurrentTimeStamp();
					startCaptureAty(timeStamp);
				}else{
					Toast.makeText(context, "", Toast.LENGTH_LONG).show();
				}
			}
		}
	};
	
	private void viewImgHasPhoto(int position) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://"+mInitList.get(position).getUploadPhotoFilePath()), "image/*");
		startActivity(intent);
	}
	
	private void startCaptureAty(String timeStamp) {
		Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String originPath = create3PhotoPathByType(timeStamp, OriginType);
		File photoFile = new File(originPath);
		Uri fileUri = Uri.fromFile(photoFile);
		photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
	    startActivityForResult(photoIntent, REQ_CAMERA_DATA);
	}
	
	

	@Override
	public void onClick(View v) {
		int msgType = 0;
		switch(v.getId()){
			case R.id.send:
				/*for(int i=0;i<aList.size()-1;i++){
					if(aList.get(i).bitmap == null){
						msgType =1;
						break;
					}
					if(ToolUtils.isEmptyString(aList.get(i).imageTypeCode)){
						msgType=2;
						break;
					}
				}
				if(msgType == 1){
					new AlertDialog.Builder(context).setTitle("提示").setMessage("有照片未拍摄")
	            	.setPositiveButton("确定", null).create().show();
				}else if(msgType == 2){
					new AlertDialog.Builder(context).setTitle("提示").setMessage("请长按照片选择类型")
	            	.setPositiveButton("确定", null).create().show();
				}else{
					DBHelper dbHelper = new DBHelper(context);
					ImageDao imgdao = new ImageDao(context, dbHelper);
					for(int i=0;i<aList.size()-1;i++){
						imgdao.insert(aList.get(i));
					}
					
					images =  TransformatUtils.imgEncode(aList);
					new UploadAsynTask().execute();
				}*/
				
				for(CarPhotoEntity carPhoto :mInitList){
					if(!TextUtils.isEmpty(carPhoto.getUploadPhotoFilePath())){
						photosList.add(carPhoto);
					}
				}
				
				if(photosList.size()>0){
					new UploadAsynTask().execute();
				}
				
				
	            break; 
	        default:
	        	break;
		}
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if( requestCode == REQ_CAMERA_DATA ) {
	    	
	    	String[] pathArray = createUploadAndThumbnailFile(timeStamp, UploadQuality);
	    	//没有拍摄,直接返回
	    	if(pathArray ==null){
	    		return;
	    	}
	        
	        CarPhotoEntity carPhoto = mInitList.get(mPosition);
	        carPhoto.setThumbnailBmp(BitmapFactory.decodeFile(pathArray[1]));
	        carPhoto.setUploadPhotoFilePath(pathArray[0]);
	        carPhoto.setThumbnailPhotoFilePath(pathArray[1]);
	        
	        adapter.setData(mInitList);
	        adapter.notifyDataSetChanged();
	    }
	}
	
	

	private class UploadAsynTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground (Void... params) {
        	String sUpload ="";
        	try {
        		JSONObject jo = TransformatUtils.vehicle2JSONObj(vehicle, photosList,lsh,carinfoMap,context);
        		sUpload = WebserviceUtil.uploadCheckItemsAndImg(jo);
			} catch (Exception e) {
				Log.e("exception", "ex",e);
			}
			return sUpload;
        }
        
        @Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);
            mProgress.dismiss();
            if(result.equals("1")){
            	new AlertDialog.Builder(context).setTitle("提示").setMessage("上传成功")
            	.setPositiveButton("确定", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(context,ReportActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						TakePictureActivity.this.finish();
					}
            	}).create().show();
            }else if("400".equals(result)){
            	new AlertDialog.Builder(context).setTitle("提示").setMessage("网络连接超时，请检查网络")
                .setPositiveButton("确定", null).create().show();
            }else{
            	new AlertDialog.Builder(context).setTitle("提示").setMessage("上传失败")
                .setPositiveButton("确定", null).create().show();
            }
        }
        
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            mProgress.setCancelable(false);
            mProgress.setTitle("请稍等");
			mProgress.setMessage("上报数据...");
			mProgress.show();
        }
    }
	
	
	/**
	 * 实现
	 * 1.删除相机拍摄的照片
	 * 2.生成压缩的图片文件
	 * @param timeStamp 生成的原图,需要删除
	 * @param compressQuality 需要生成上传的图片
	 * @param compressBmp
	 * @return 数组:三种图片路径
	 * 
	 */
	private String[] createUploadAndThumbnailFile(String timeStamp,int compressQuality) {
		
		String originFilepath = create3PhotoPathByType(timeStamp, OriginType);
		String uploadFilepath = create3PhotoPathByType(timeStamp, UploadType);
		String thumbnailFilepath = create3PhotoPathByType(timeStamp, ThumbnaiType);
		
		Bitmap uploadBmp = compressBitmapByInsample(originFilepath, 4);
		if(uploadBmp == null){
			return null;
		}
		
		int screenW = (context.getResources().getDisplayMetrics().widthPixels 
				- DensityUtil.dip2px(context,20))/4;
		Bitmap thumbnailBmp = ThumbnailUtils.extractThumbnail(uploadBmp, screenW, screenW);
		
		// 根据路径新建上传图片\缩略图,存在则先删除再新建
		PictureUtil.createFileIfNonOrDeleteIfExists(uploadFilepath);
		PictureUtil.createFileIfNonOrDeleteIfExists(thumbnailFilepath);
		
		//根据原图路径删除原图
//		File originFile = new File(originFilepath);
//		originFile.delete();
		PictureUtil.createFileIfNonOrDeleteIfExists(originFilepath);
		
		try {
			FileOutputStream uploadfos = new FileOutputStream(new File(uploadFilepath));
			FileOutputStream thumbnailfos = new FileOutputStream(new File(thumbnailFilepath));
			
			uploadBmp.compress(Bitmap.CompressFormat.JPEG, compressQuality, uploadfos);
			thumbnailBmp.compress(Bitmap.CompressFormat.JPEG, ThumbnailQuality, thumbnailfos);
			uploadfos.flush();
			thumbnailfos.flush();
			uploadfos.close();
			thumbnailfos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		PictureUtil.galleryAddPhoto(context,originFilepath);
		PictureUtil.galleryAddPhoto(context,uploadFilepath);
		PictureUtil.galleryAddPhoto(context,thumbnailFilepath);
		
		return new String[]{uploadFilepath,thumbnailFilepath};
	}
	
	/**
	 * 对原图进行压缩得到上传图片
	 * @param imagePath
	 * @param scale
	 * @return
	 */
	private Bitmap compressBitmapByInsample(String imagePath,int scale){
		
		//该路径不存在图片，返回null
		if(!new File(imagePath).exists()){
			return null;
		}
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, options);
		
		options.inSampleSize = scale;
		options.inPurgeable = true;
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
		
		return PictureUtil.drawTextToBitmap(bitmap, ToolUtils.getCurDate());
	}
	

	/**
	 * 根据时间戳得到原图、小图、图标三个路径
	 * @param timeStamp
	 */
	protected String create3PhotoPathByType(String timeStamp,int type) {
		
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
		String fileName = "";
		switch(type){
			case OriginType:
				fileName = "Origin_Veh_" + timeStamp + ".jpg";
				break;
			case UploadType:
				fileName = "Small_Veh_" + timeStamp + ".jpg";
				break;
			case ThumbnaiType:
				fileName = "Thumbnai_Veh_" + timeStamp + ".jpg";
				break;
		}
        
		return path + "/" + fileName;
    }
	
	
	private String getCurrentTimeStamp(){
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	}
	

	private boolean getSdcarState(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	public static List<CarPhotoEntity> initPhotoList(Context con){
		List<CarPhotoEntity> list = new ArrayList<CarPhotoEntity>();
		String[] photoNames=con.getResources().getStringArray(R.array.photoangles);
		String[] photoCodes=con.getResources().getStringArray(R.array.photoangles_code);
		for(int i=0;i<photoNames.length;i++){
			Bitmap bmp = BitmapFactory.decodeResource(con.getResources(), R.drawable.ic_photo_add);
			CarPhotoEntity carPhoto = new CarPhotoEntity(photoCodes[i],photoNames[i],bmp,"","",TakePictureActivity.PHOTO_NOT_MUST);
			list.add(carPhoto);
		}
		return list;
	}
	
}
