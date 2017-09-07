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
		// ͨ��GPS���Ƕ�λ����λ������Ծ�ȷ���֣�ͨ��24�����Ƕ�λ��������Ϳտ��ĵط���λ׼ȷ���ٶȿ죩
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// ͨ��WLAN���ƶ�����(3G/2G)ȷ����λ�ã�Ҳ����AGPS������GPS��λ����Ҫ���������ڻ��ڸ������Ⱥ��ï�ܵ����ֵȣ��ܼ��ĵط���λ��
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
		// ��ȡλ�ù������
		LocationManager locationManager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
//		Location local = null;
		// ���ҵ�������Ϣ
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // �߾���
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // �͹���

		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//			String provider = locationManager.getBestProvider(criteria, true); // ��ȡGPS��Ϣ
//			local = locationManager.getLastKnownLocation(provider); // ͨ��GPS��ȡλ��
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
		// ���ü��������Զ����µ���Сʱ��Ϊ���N��(1��Ϊ1*1000������д��ҪΪ�˷���)����Сλ�Ʊ仯����N��
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
