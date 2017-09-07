package com.vechicle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	public static final int DB_VERSION = 2;
	public static final String DB_NAME = "inspection.db";
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if(!db.isReadOnly()){
			db.execSQL("PRAGMA foreign_keys=ON");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE vehicle(vehicleid INTEGER PRIMARY KEY AUTOINCREMENT," +
				"licence VARCHAR(20),clsbdh VARCHAR(50),lictype VARCHAR(2),useProperties VARCHAR(8),insptype VARCHAR(2),vehcode INTEGER," +
				"enginenum INTEGER,brand INTEGER,color VARCHAR(20),isColorCheck INTEGER," +
				"seats VARCHAR(5),isSeatsCheck INTEGER,vehtype VARCHAR(20),isVehtypeCheck INTEGER," +
				"aspect INTEGER,tyrecondition INTEGER,triangles INTEGER,gabarite INTEGER, zbzl INTEGER," +
				"tyrespec INTEGER,saftyguard INTEGER,reflectlog INTEGER,extinguisher INTEGER," +
				"tachographs INTEGER,hammer INTEGER,alarmdevice INTEGER,spraypaint INTEGER," +
				"certify INTEGER,conclusion INTEGER,remark TEXT)";
		db.execSQL(sql);
		sql = "CREATE TABLE images(imgid INTEGER PRIMARY KEY AUTOINCREMENT," +
				"imagepath VARCHAR(50),imagepos VARCHA(3)," +
				"vehicleIndex INTEGER,FOREIGN KEY(vehicleIndex) REFERENCES vehicle(vehicleid))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql1=" DROP TABLE IF EXISTS images;";
		String sql2=" DROP TABLE IF EXISTS vehicle;";
        db.execSQL(sql1);
        db.execSQL(sql2);
        onCreate(db);
	}

}
