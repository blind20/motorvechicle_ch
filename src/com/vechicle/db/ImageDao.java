package com.vechicle.db;

import com.vechicle.domain.ImageWrap;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ImageDao {
	private Context context;
	private DBHelper dbHelper;
	
	public ImageDao(Context context, DBHelper dbHelper) {
		this.context = context;
		this.dbHelper = new DBHelper(context);
	}

	public long insert(ImageWrap imagewrap){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("imagepath", imagewrap.imagepath);
		values.put("imagepos", imagewrap.imageTypeCode);
		values.put("vehicleIndex", imagewrap.vehicleIndex);
		long value = db.insert("images", null, values);
		db.close();
		return value;
	}
}
