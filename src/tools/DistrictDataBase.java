package tools;

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
//	
//	public static String getCity(Context context, String city) {
//		DistrictDataBase helper = new DistrictDataBase(context, DB_NAME, null, VERSIONOFCREATING);
//		SQLiteDatabase db = helper.getWritableDatabase();
//		Cursor cursor = db.query(TB_CITY, null, "province_id=?", new String[]{province_id}, null, null, "city_id asc");
//		try {
//			while(cursor.moveToNext()) {
//				City city = new City();
//				city.province_id = cursor.getString(cursor.getColumnIndex("province_id"));
//				city.city_id = cursor.getString(cursor.getColumnIndex("city_id"));
//				city.city_name = cursor.getString(cursor.getColumnIndex("city_name"));
//				list.add(city);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			cursor.close();
//			db.close();
//			helper.close();
//		}
//		return list;
//	}
	
	public static String getCityId(Context context, String cityName) {
		String cityCode = "";
		DistrictDataBase helper = new DistrictDataBase(context, DB_NAME, null, VERSIONOFCREATING);
		SQLiteDatabase db = helper.getWritableDatabase();
//		Cursor cursor = db.query("moji", null, "city_name like?", new String[]{"'%"}, null, null, "district_id asc");
		String sql = "select * from moji where city_name like" +"'%"
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
	
}
