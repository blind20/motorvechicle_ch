package com.vechicle.util;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class GpsUtils {

	private Location location = null;
	private LocationManager locationManager = null;
	private Context context = null;
	
	private String provider;
	
	private static GpsUtils gpsutils = null;
	
	private GpsUtils(Context ctx){
		 context=ctx;
		 locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		 location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		 Criteria criteria = new Criteria();
		 
		 criteria.setAccuracy(Criteria.ACCURACY_FINE);
         criteria.setAltitudeRequired(false);
         criteria.setBearingRequired(false);
         criteria.setCostAllowed(true);
         criteria.setPowerRequirement(Criteria.POWER_LOW);
         provider = locationManager.getBestProvider(criteria, true);
		 
         
//		 locationManager.setTestProviderEnabled(provider,true);
		 
		 Log.d("provider", provider);
		 Log.e("GpsUtils", location+"");
		 updateLocation();
	}
	
	public synchronized static GpsUtils getInstance(Context ctx){
		if(gpsutils == null){
			gpsutils = new GpsUtils(ctx);
		}
		return gpsutils;
	}
	
	
	public static final boolean isOPen(final Context context) {
		LocationManager locationManager  = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
//		boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//		if (gps || network) {
//			return true;
//		}
		Log.i("isOPen", "gps"+gps);
		if (gps) {
			return true;
		}

		return false;
	}
	
	

	public static final void openGPS(Context context) {
		Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
	}
	
	/*public static final void openGPS(Context context) {
		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}*/
	
/*
	private Location getLocation(Context con) {
		// 获取位置管理服务
		LocationManager locationManager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
//		Location local = null;
		// 查找到服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//			String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
//			local = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
			local = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}else{
			LocationListener locationListener = new LocationListener() {
				
				@Override
				public void onProviderEnabled(String provider) {
					
				}
				
				@Override
				public void onProviderDisabled(String provider) {
					
				}
				
				@Override
				public void onLocationChanged(Location location) {
					if (location != null) {   
						Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: "  + location.getLongitude());  
						local = location;
					}
				}

				@Override
				public void onStatusChanged(String provider, int status,Bundle extras) {
					
				}
			};
			
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 50,locationListener);   
			local = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);   
		}
//		updateToNewLocation(location);
		// 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
//		locationManager.requestLocationUpdates(provider, 100 * 1000, 500,locationListener);
		return local;
	}
*/
	
	public Location getLocation() {
		return location;
	}

	public void closeLocation() {
		Log.i("closeLocation", "closeLocation");
		if (locationManager != null) {
			
			if (locationListener != null) {
				locationManager.removeUpdates(locationListener);
				locationManager.setTestProviderEnabled(provider,false);
				locationListener = null;
			}
			locationManager = null;
		}
	}
	
	public void updateLocation() {
		Log.i("GpsUtils","updateLocation");
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
	}
	
	private LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location l) {
			Log.i("GpsUtils","onLocationChanged");
			if (l != null) {
				location = l;
			}
		}
		
		public void onProviderDisabled(String provider) {
			location = null;
		}

		public void onProviderEnabled(String provider) {
			Location l = locationManager.getLastKnownLocation(provider);
			if (l != null) {
				location = l;
			}
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};
	
	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {  
	    float[] results=new float[1];  
	    Location.distanceBetween(lat1, lon1, lat2, lon2, results);  
	    return results[0];  
	} 

}
