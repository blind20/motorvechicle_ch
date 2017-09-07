package com.vechicle.db;

import com.vechicle.domain.Vehicle;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class VehDao {
	
	private Context context;
	private DBHelper dbHelper;
	
	public VehDao(Context context, DBHelper dbHelper) {
		this.context = context;
		this.dbHelper = new DBHelper(context);
	}
	
	public long insert(Vehicle vehicle){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("licence", vehicle.getLicence());
		values.put("clsbdh", vehicle.getClsbdh());
		values.put("lictype", vehicle.getLictype());
		values.put("useProperties", vehicle.getUseProperties());
		values.put("insptype", vehicle.getInsptype());
		
		values.put("vehcode", vehicle.getVehcode());
		values.put("enginenum", vehicle.getEnginenum());
		values.put("brand",vehicle.getBrand());
		values.put("color", vehicle.getColor());
		values.put("isColorCheck", vehicle.getIsColorCheck());
		values.put("seats", vehicle.getSeats());
		values.put("isSeatsCheck", vehicle.getIsSeatsCheck());
		values.put("vehtype", vehicle.getVehtype());
		values.put("isVehtypeCheck", vehicle.getIsVehtypeCheck());
		values.put("aspect", vehicle.getAspect());
		values.put("tyrecondition", vehicle.getTyrecondition() );
		values.put("triangles", vehicle.getTriangles() );
		values.put("gabarite", vehicle.getGabarite());
		values.put("zbzl", vehicle.getZbzl());
		values.put("tyrespec", vehicle.getTyrespec());
		values.put("saftyguard",vehicle.getSaftyguard());
		values.put("reflectlog", vehicle.getReflectlog());
		values.put("extinguisher", vehicle.getExtinguisher());
		values.put("tachographs", vehicle.getTachographs());
		values.put("hammer",  vehicle.getHammer());
		values.put("alarmdevice", vehicle.getAlarmdevice());
		values.put("spraypaint", vehicle.getSpraypaint());
		values.put("certify", vehicle.getCertify());
		values.put("conclusion", vehicle.getConclusion());
		values.put("remark", vehicle.getRemark());
		long value = db.insert("vehicle", null, values);
		db.close();
		return value;
	}

}
