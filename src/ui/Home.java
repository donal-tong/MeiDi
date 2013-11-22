package ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tools.AppException;
import tools.CalendarUtil;
import tools.DBHelper;
import tools.DBManager;
import tools.ImageUtils;
import tools.Logger;
import tools.Lunar;
import tools.UIHelper;
import widget.PullToRefreshView;
import widget.PullToRefreshView.OnFooterRefreshListener;
import widget.PullToRefreshView.OnHeaderRefreshListener;

import bean.ForecastWeather;
import bean.SKWeather;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.vikaa.meidi.R;

import config.ApiClent;

import adapter.ForecastAdapter;
import adapter.HomeAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Home extends tools.BaseActivity implements AMapLocationListener, OnHeaderRefreshListener, OnFooterRefreshListener{
	private LocationManagerProxy mAMapLocManager = null;
	private TextView locationTV;
	private TextView tempTV;
	private TextView weatherTV;
	private TextView windTV;
	private TextView wetTV;
	private TextView dayTV;
	private TextView lunarTV;
	private ImageView cImageView;
	private ListView forecastListView;
	private ForecastAdapter forecastAdapter;
	private List<SKWeather> datas = new ArrayList<SKWeather>();
	private PullToRefreshView mPullToRefreshView;
	private ListView mListView;
	private View header;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		setUI();
		mAMapLocManager = LocationManagerProxy.getInstance(this);
		if(!appContext.isNetworkConnected()) {
        	UIHelper.ToastMessage(this, R.string.network_not_connected);
        }
        else {
        	mPullToRefreshView.headerRefreshing();
        }
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
			mPullToRefreshView.onHeaderRefreshComplete();
		}
	}

	@Override
	protected void onDestroy() {
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
			mAMapLocManager.destory();
		}
		mAMapLocManager = null;
		super.onDestroy();
	}
	
	private void setUI() {
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
		mPullToRefreshView.mHeaderTextView.setTextColor(getResources().getColor(R.color.white));
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mListView = (ListView) findViewById(R.id.mlistView);
		mListView.setDividerHeight(0);
		header = getLayoutInflater().inflate(R.layout.home_head, null);
		RelativeLayout contentView = (RelativeLayout) header.findViewById(R.id.contentView);
		RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) contentView.getLayoutParams();
		p.height = ImageUtils.getDisplayHeighth(this) - ImageUtils.dip2px(this, 100);
		contentView.setLayoutParams(p);
		locationTV = (TextView) findViewById(R.id.locationTV);
		tempTV = (TextView) header.findViewById(R.id.tempTV);
		weatherTV = (TextView) header.findViewById(R.id.weatherTV);
		windTV = (TextView) header.findViewById(R.id.windTV);
		wetTV = (TextView) header.findViewById(R.id.wetTV);
		dayTV = (TextView) header.findViewById(R.id.dayTV);
		lunarTV = (TextView) header.findViewById(R.id.lunarTV);
		cImageView = (ImageView) header.findViewById(R.id.cImageView);
		forecastListView = (ListView) header.findViewById(R.id.listView);
		forecastListView.setDividerHeight(0);
		forecastAdapter = new ForecastAdapter(this, datas);
		forecastListView.setAdapter(forecastAdapter);
		mListView.addHeaderView(header);
		mListView.setAdapter(new HomeAdapter());
	}
	
	@SuppressLint("HandlerLeak")
	public void initWaetherData(final String cityName) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					String cityCode = (String)msg.obj;
					querySKWeather(cityCode);
					forecastWeather(cityCode);
				} else {
					
				}
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				try {
					DBHelper helper = new DBHelper(getApplicationContext());
					DBManager manager = new DBManager(getApplicationContext());
					manager.copyDatabase();
					String cityCode = null;
					String sql = "select * from city_table where CITY like" +"'%"
							+ cityName + "'" + ";";
					Cursor cursor = helper.getReadableDatabase()
							.rawQuery(sql, null);
					if (cursor != null) {
						cursor.moveToFirst();
						cityCode = cursor.getString(cursor
								.getColumnIndex("WEATHER_ID"));
					}
					cursor.close();
					helper.close();
					msg.what = 1;
					msg.obj = cityCode;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	@SuppressLint("HandlerLeak")
	private void forecastWeather(final String cityCode) {
		final Handler mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					datas.clear();
					ForecastWeather weather = (ForecastWeather)msg.obj;
					weatherTV.setText(weather.weather1);
					SKWeather weather1 = new SKWeather();
					weather1.temp = weather.temp1;
					weather1.isRadar = weather.img1;
					weather1.time = "今天";
					datas.add(weather1);
					SKWeather weather2 = new SKWeather();
					weather2.temp = weather.temp2;
					weather2.isRadar = weather.img3;
					CalendarUtil day2 = new CalendarUtil(1);
			        weather2.time = day2.getWeek(day2.getDay());
					datas.add(weather2);
					SKWeather weather3 = new SKWeather();
					weather3.temp = weather.temp3;
					weather3.isRadar = weather.img5;
					CalendarUtil day3 = new CalendarUtil(2);
			        weather3.time = day3.getWeek(day3.getDay());
					datas.add(weather3);
					SKWeather weather4 = new SKWeather();
					weather4.temp = weather.temp4;
					weather4.isRadar = weather.img7;
					CalendarUtil day4 = new CalendarUtil(3);
			        weather4.time = day4.getWeek(day4.getDay());
					datas.add(weather4);
					SKWeather weather5 = new SKWeather();
					weather5.temp = weather.temp5;
					weather5.isRadar = weather.img9;
					CalendarUtil day5 = new CalendarUtil(4);
			        weather5.time = day5.getWeek(day5.getDay());
					datas.add(weather5);
					forecastAdapter.notifyDataSetChanged();
				} 
				else {
					((AppException)msg.obj).makeToast(Home.this);
				}
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					ForecastWeather weather = ApiClent.forcastWeather(appContext, cityCode);
					msg.what = 1;
					msg.obj = weather;
				} catch (Exception e) {
					msg.what = -1;
					msg.obj = e;
				}
				mhandler.sendMessage(msg);
			};
		}.start();
	}
	
	private void notiWeather(SKWeather weather) {
		Intent intent = new Intent();
		intent.putExtra("weather", weather);
		intent.setAction("getweather");
		sendBroadcast(intent);
	}
	
	@SuppressLint("HandlerLeak")
	private void querySKWeather(final String cityCode) {
		final Handler mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					SKWeather weather = (SKWeather)msg.obj;
					tempTV.setText(weather.temp);
					windTV.setText(weather.WD+""+weather.WS);
					wetTV.setText("湿度"+weather.SD);
					cImageView.setVisibility(View.VISIBLE);
					weatherTV.setVisibility(View.VISIBLE);
					Calendar calendar = Calendar.getInstance();
					Lunar lunar = new Lunar(calendar);  
					String lunarStr = "";  
					lunarStr +=lunar.cyclical()+"年";  
					lunarStr +=lunar.toString();  
					CalendarUtil day = new CalendarUtil(0);
					dayTV.setText(day.Date2String(day.getDay(),"yyyy年M月d日"));
					lunarTV.setText(lunarStr);
					notiWeather(weather);
				} 
				else {
					((AppException)msg.obj).makeToast(Home.this);
				}
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					SKWeather weather = ApiClent.getSKWeather(appContext, cityCode);
					msg.what = 1;
					msg.obj = weather;
				} catch (Exception e) {
					msg.what = -1;
					msg.obj = e;
				}
				mhandler.sendMessage(msg);
			};
		}.start();
	}

	@Override
	public void onLocationChanged(Location arg0) {
		Logger.i("asdf");
	}

	@Override
	public void onProviderDisabled(String arg0) {
		Logger.i(arg0);
	}

	@Override
	public void onProviderEnabled(String arg0) {
		Logger.i(arg0);
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		Logger.i(arg0);
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			location.getLatitude();
			location.getLongitude();
			Bundle locBundle = location.getExtras();
			if (locBundle != null) {
				locBundle.getString("citycode");
				locBundle.getString("desc");
			}
//			String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
//					+ "\n精    度    :" + location.getAccuracy() + "米"
//					+ "\n定位方式:" + location.getProvider() + "\n定位时间:"
//					+ AMapUtil.convertToTime(location.getTime()) + "\n城市编码:"
//					+ cityCode + "\n位置描述:" + desc + "\n省:"
//					+ location.getProvince() + "\n市:" + location.getCity()
//					+ "\n区(县):" + location.getDistrict() + "\n城市编码:"
//					+ location.getCityCode() + "\n区域编码:" + location.getAdCode());
//			Logger.i(str);
//			Logger.i(location.getDistrict());
			locationTV.setText(location.getDistrict());
			initWaetherData(location.getDistrict().substring(0, location.getDistrict().length()-1));
			mAMapLocManager.removeUpdates(this);
		}
		else {
			UIHelper.ToastMessage(this, "网络不给力哦，请往下拉再试试");
			mPullToRefreshView.onHeaderRefreshComplete();
		}
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.onFooterRefreshComplete();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mAMapLocManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 1000, 10, this);
	}
}
