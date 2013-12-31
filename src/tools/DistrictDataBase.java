package tools;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DistrictDataBase extends SQLiteOpenHelper {
	public static final String DB_NAME = "moji.db";
	public static final int VERSIONOFCREATING = 1;
	
	public static final String TB_CITY = "moji";
	public DistrictDataBase(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public static String getCityId(Context context, String cityName) {
		String cityCode = "";
		DistrictDataBase helper = new DistrictDataBase(context, DB_NAME, null, VERSIONOFCREATING);
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from moji where city_pinyin !='' and city_name like" +"'%"
				+ cityName + "'" + ";";
		Cursor cursor = db.rawQuery(sql, null);
		try {
			if (cursor != null) {
				cursor.moveToFirst();
				cityCode = cursor.getString(cursor
						.getColumnIndex("city_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
			helper.close();
		}
		return cityCode;
	}
	
	public static List<String> getCitys(Context context) {
		List<String> citys = new ArrayList<String>();
		DistrictDataBase helper = new DistrictDataBase(context, DB_NAME, null, VERSIONOFCREATING);
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from moji where city_pinyin !=''";
		Cursor cursor = db.rawQuery(sql, null);
		try {
			while(cursor.moveToNext()) {
				String city = cursor.getString(cursor.getColumnIndex("city_name"));
				citys.add(city);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
			helper.close();
		}
		return citys;
	}
	
}
