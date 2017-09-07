package com.vechicle.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.vechicle.R;
import com.vechicle.domain.CarPhotoEntity;
import com.vechicle.domain.ImageWrap;
import com.vechicle.domain.Vehicle;
import com.vechicle.ui.TakePictureActivity;
import com.zf.myzxing.decode.Intents.Share;

public class TransformatUtils {

	
	
	public static JSONObject vehicle2JSONObj(Vehicle vehicle,List<CarPhotoEntity> list ,String lsh,
			HashMap<String,Object> carinfoMap,Context con){
		
		String sInpOrderNo = SharePreUtil.getSharepref("inpOrderNo");
		String ursname = SharePreUtil.getSharepref("usrname");
		
		JSONObject jsonObj =new JSONObject();
//		String[] images =  TransformatUtils.imgEncode(list);
		String[] images = TransformatUtils.imgsEncodeNew(list);
		
		try {
			jsonObj.put("inpOrderNo", sInpOrderNo);
			jsonObj.put("licType", vehicle.getLictype());
			jsonObj.put("licTypeYC", vehicle.getLictype());
			
			jsonObj.put("useType", vehicle.getUseProperties());
			
			String ywlxcode = vehicle.getInsptype();
			String ywlx = ToolUtils.getLableByCode(ywlxcode, R.array.insptype, R.array.insptype_code);
			jsonObj.put("bussinessTypeCode", ywlxcode);
			jsonObj.put("bussinessType", ywlx);
			
			jsonObj.put("checker", ursname);
			jsonObj.put("items", "");
			jsonObj.put("remarks", vehicle.getRemark());
			jsonObj.put("checkVehType",vehicle.getVehtype());
			jsonObj.put("checkVehTypeCode", vehicle.getVehtype());
			
			jsonObj.put("rst1", vehicle.getVehcode());
			jsonObj.put("rst2", vehicle.getEnginenum());
			jsonObj.put("rst3", vehicle.getBrand());
			jsonObj.put("rst4", (vehicle.getColor()==null?"":vehicle.getColor()) +" "+ vehicle.getIsColorCheck());
			jsonObj.put("rst5", (vehicle.getSeats()==null?"":vehicle.getSeats())+" "+ vehicle.getIsSeatsCheck());
			
			Log.i("车身颜色", vehicle.getColor()+" "+ vehicle.getIsColorCheck());
			Log.i("getClsbdh", vehicle.getClsbdh()+","+ vehicle.getLicence());
			Log.i("getUseProperties", vehicle.getUseProperties()+",");
//			Log.i("车辆类型", "cllx:"+vehicle.getVehtype()+",hpzl:"+vehicle.getLictype()+",ywlx:"+ywlxcode);
			
			jsonObj.put("rst6", (vehicle.getVehtype()==null?"":vehicle.getVehtype()) +" "+ vehicle.getIsVehtypeCheck());
			jsonObj.put("rst7", vehicle.getAspect());
			jsonObj.put("rst8", vehicle.getTyrecondition());
			jsonObj.put("rst9", vehicle.getTriangles());
			jsonObj.put("rst10", vehicle.getGabarite());
			
			jsonObj.put("rst11", vehicle.getZbzl());
			
			jsonObj.put("rst12", vehicle.getTyrespec());
			jsonObj.put("rst13", vehicle.getSaftyguard());
			jsonObj.put("rst14", vehicle.getReflectlog());
			jsonObj.put("rst15", vehicle.getExtinguisher());
			jsonObj.put("rst16", vehicle.getTachographs());
			jsonObj.put("rst17", vehicle.getHammer());
			jsonObj.put("rst18", vehicle.getSpraypaint());
			jsonObj.put("rst19", vehicle.getAlarmdevice());
			jsonObj.put("rst20", vehicle.getCertify());
			jsonObj.put("rstTotal", vehicle.getConclusion());
			jsonObj.put("lsh", lsh);
			
			Log.i("Target", jsonObj.toString());
			
			if("A".equals(ywlxcode)){
				jsonObj.put("license", vehicle.getClsbdh());
			}else{
				jsonObj.put("license", vehicle.getLicence());
			}
			
			jsonObj.put("hphm", vehicle.getLicence());
			jsonObj.put("vin", vehicle.getClsbdh());
			
			JSONArray ja = new JSONArray();
    		int i=0;
    		for(String sImage:images){
    			JSONObject jo =new JSONObject();
    			if("A".equals(ywlxcode)){
    				jo.put("lic", vehicle.getClsbdh());
    			}else{
    				jo.put("lic", vehicle.getLicence());
    			}
    			jo.put("licType", vehicle.getLictype());
    			jo.put("type", list.get(i).getPhotoTypeCode());
    			jo.put("inpOrderNo", sInpOrderNo);
    			jo.put("sCurrentUser", ursname);
    			jo.put("jycs", (String)carinfoMap.get("jycs"));
    			jo.put("jcxdh", (String)carinfoMap.get("jcxdh"));
    			jo.put("clsbdh", (String)carinfoMap.get("clsbdh"));
    			jo.put("jyxm", "F1");
    			jo.put("image", sImage);
    			ja.put(jo);
    			i++;
    		}
    		jsonObj.put("photos", ja);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObj;		
	}
	
	
	
	public static String[] imgEncode(ArrayList<ImageWrap> list){
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			for (int i=0;i<list.size()-1;i++) {
				byte[] bytes = Bitmap2Bytes(list.get(i).bitmap);
				String imgBuffer = Base64.encodeToString(bytes,Base64.NO_WRAP);
				arrayList.add(imgBuffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] images = (String[])arrayList.toArray(new String[arrayList.size()]); 
		return images;
	}
	
	
	
	
	public static String[] imgsEncodeNew(List<CarPhotoEntity> list){
	
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			for (int i=0;i<list.size();i++) {
				String path = list.get(i).getUploadPhotoFilePath();
				Bitmap bm = BitmapFactory.decodeFile(list.get(i).getUploadPhotoFilePath());
				byte[] bytes = Bitmap2Bytes(BitmapFactory.decodeFile(list.get(i).getUploadPhotoFilePath()));
				Log.i("bytes", String.valueOf(bytes.length/1024));
				String imgBuffer = Base64.encodeToString(bytes,Base64.NO_WRAP);
				Log.i("imgBuffer", String.valueOf(imgBuffer.getBytes().length/1024));
				arrayList.add(imgBuffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] images = (String[])arrayList.toArray(new String[arrayList.size()]); 
		return images;
	}
	

	/**
	 * compress函数能再压缩一次
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}
}
