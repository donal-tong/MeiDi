package ui;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

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

import bean.AirIndexEntity;
import bean.Entity;
import bean.ForecastWeather;
import bean.IndexEntity;
import bean.Result;
import bean.SKWeather;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.vikaa.meidi.R;

import config.ApiClent;
import config.CommonValue;
import config.WeatherClient;
import config.WeatherClient.ClientCallback;

import adapter.HomeAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class Home extends AppActivity implements AMapLocationListener, OnHeaderRefreshListener, OnFooterRefreshListener{
	private RelativeLayout homeBg;
	private LocationManagerProxy mAMapLocManager = null;
	private LinearLayout weatherInfoView;
	private View header;
	private TextView locationTV;
	private TextView tempTV;
	private TextView weatherTV;
	private TextView dayTV;
	private TextView lunarTV;
	private ImageView cImageView;
	private ImageView defaultImageView;
	
	private TextView minMaxTV;
	private TextView windTV;
	private TextView wetTV;
	
	private TextView clothTV;
	private TextView lineTV;
	private TextView sportTV;
	private TextView cleanupTV;
	private TextView carTV;
	private TextView tripTV;
	
	private TextView day1TV;
	private TextView day1maxTV;
	private TextView day1minTV;
	private ImageView day1dIV;
	private ImageView day1nIV;
	private TextView day2TV;
	private TextView day2maxTV;
	private TextView day2minTV;
	private ImageView day2dIV;
	private ImageView day2nIV;
	private TextView day3TV;
	private TextView day3maxTV;
	private TextView day3minTV;
	private ImageView day3dIV;
	private ImageView day3nIV;
	private TextView day4TV;
	private TextView day4maxTV;
	private TextView day4minTV;
	private ImageView day4dIV;
	private ImageView day4nIV;
	private TextView day5TV;
	private TextView day5maxTV;
	private TextView day5minTV;
	private ImageView day5dIV;
	private ImageView day5nIV;
	private TextView day6TV;
	private TextView day6maxTV;
	private TextView day6minTV;
	private ImageView day6dIV;
	private ImageView day6nIV;
	
	private LinearLayout wuranLayout;
	private TextView qualityTV;
	private TextView pm2TV;
	
	private PullToRefreshView mPullToRefreshView;
	private ListView mListView;
	
	private int screenWidth;
	
	private AnimationDrawable anim = null;
	Timer timer = new Timer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		screenWidth = ImageUtils.getDisplayWidth(this);
		setUI();
		mAMapLocManager = LocationManagerProxy.getInstance(this);
		if(!appContext.isNetworkConnected()) {
        	UIHelper.ToastMessage(this, R.string.network_not_connected, Toast.LENGTH_SHORT);
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
		homeBg = (RelativeLayout) findViewById(R.id.homebg);
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
		mPullToRefreshView.mHeaderTextView.setTextColor(getResources().getColor(R.color.white));
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mListView = (ListView) findViewById(R.id.mlistView);
		mListView.setDividerHeight(0);
		header = getLayoutInflater().inflate(R.layout.home_head, null);
		weatherInfoView = (LinearLayout) header.findViewById(R.id.weatherInfoView);
		LinearLayout contentView = (LinearLayout) header.findViewById(R.id.contentView);
		RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) contentView.getLayoutParams();
		p.height = 2*ImageUtils.getDisplayHeighth(this);
		contentView.setLayoutParams(p);
		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/code.otf");
		locationTV = (TextView) findViewById(R.id.locationTV);
		tempTV = (TextView) header.findViewById(R.id.tempTV);
		tempTV.setTypeface(face);
		weatherTV = (TextView) header.findViewById(R.id.weatherTV);
		weatherTV.setTypeface(face);
		
		dayTV = (TextView) header.findViewById(R.id.dayTV);
		dayTV.setTypeface(face);
		lunarTV = (TextView) header.findViewById(R.id.lunarTV);
		lunarTV.setTypeface(face);
		cImageView = (ImageView) header.findViewById(R.id.cImageView);
		defaultImageView = (ImageView) header.findViewById(R.id.defaultImageView);
		wuranLayout = (LinearLayout) header.findViewById(R.id.wuranLayout);
		qualityTV = (TextView) header.findViewById(R.id.qualityTV);
		pm2TV = (TextView) header.findViewById(R.id.pm2TV);
		
		minMaxTV = (TextView) header.findViewById(R.id.minMaxTV);
		minMaxTV.setTypeface(face);
		windTV = (TextView) header.findViewById(R.id.windTV);
		windTV.setTypeface(face);
		wetTV = (TextView) header.findViewById(R.id.wetTV);
		wetTV.setTypeface(face);
		int w = ImageUtils.getDisplayWidth(this);
		LinearLayout.LayoutParams p1 = (LayoutParams) minMaxTV.getLayoutParams();
		p1.width = w/3;
		minMaxTV.setLayoutParams(p1);
		LinearLayout.LayoutParams p2 = (LayoutParams) windTV.getLayoutParams();
		p2.width = w/3;
		windTV.setLayoutParams(p2);
		LinearLayout.LayoutParams p3 = (LayoutParams) wetTV.getLayoutParams();
		p3.width = w/3;
		wetTV.setLayoutParams(p3);
		initIndexUI();
		initWeekWeatherUI();
		mListView.addHeaderView(header, null, false);
		mListView.setAdapter(new HomeAdapter());
	}
	
	private void initIndexUI() {
		
		RelativeLayout clothView = (RelativeLayout) header.findViewById(R.id.clothView);
		RelativeLayout.LayoutParams pc = (RelativeLayout.LayoutParams) clothView.getLayoutParams();
		pc.width = (ImageUtils.getDisplayWidth(this)-ImageUtils.dip2px(this, 26))/2;
		clothView.setLayoutParams(pc);
		
		RelativeLayout lineView = (RelativeLayout) header.findViewById(R.id.lineView);
		RelativeLayout.LayoutParams pl = (RelativeLayout.LayoutParams) lineView.getLayoutParams();
		pl.width = (ImageUtils.getDisplayWidth(this)-ImageUtils.dip2px(this, 26))/2;
		lineView.setLayoutParams(pl);
		
		RelativeLayout sportView = (RelativeLayout) header.findViewById(R.id.sportView);
		RelativeLayout.LayoutParams ps = (RelativeLayout.LayoutParams) sportView.getLayoutParams();
		ps.width = (ImageUtils.getDisplayWidth(this)-ImageUtils.dip2px(this, 26))/2;
		sportView.setLayoutParams(ps);
		
		RelativeLayout cleanupView = (RelativeLayout) header.findViewById(R.id.cleanupView);
		RelativeLayout.LayoutParams pcl = (RelativeLayout.LayoutParams) cleanupView.getLayoutParams();
		pcl.width = (ImageUtils.getDisplayWidth(this)-ImageUtils.dip2px(this, 26))/2;
		cleanupView.setLayoutParams(pcl);
		
		RelativeLayout carView = (RelativeLayout) header.findViewById(R.id.carView);
		RelativeLayout.LayoutParams pca = (RelativeLayout.LayoutParams) carView.getLayoutParams();
		pca.width = (ImageUtils.getDisplayWidth(this)-ImageUtils.dip2px(this, 26))/2;
		carView.setLayoutParams(pca);
		
		RelativeLayout tripView = (RelativeLayout) header.findViewById(R.id.tripView);
		RelativeLayout.LayoutParams pt = (RelativeLayout.LayoutParams) tripView.getLayoutParams();
		pt.width = (ImageUtils.getDisplayWidth(this)-ImageUtils.dip2px(this, 26))/2;
		tripView.setLayoutParams(pt);
		
		clothTV = (TextView) header.findViewById(R.id.clothTV);
		lineTV = (TextView) header.findViewById(R.id.lineTV);
		sportTV = (TextView) header.findViewById(R.id.sportTV);
		cleanupTV = (TextView) header.findViewById(R.id.cleanupTV);
		carTV = (TextView) header.findViewById(R.id.carTV);
		tripTV = (TextView) header.findViewById(R.id.tripTV);
	}
	
	private void initWeekWeatherUI() {
		LinearLayout day1View = (LinearLayout) header.findViewById(R.id.day1View);
		LinearLayout.LayoutParams p1 = (LayoutParams) day1View.getLayoutParams();
		p1.width = ImageUtils.getDisplayWidth(this)/6;
		day1View.setLayoutParams(p1);
		
		LinearLayout day2View = (LinearLayout) header.findViewById(R.id.day2View);
		LinearLayout.LayoutParams p2 = (LayoutParams) day2View.getLayoutParams();
		p2.width = ImageUtils.getDisplayWidth(this)/6;
		day2View.setLayoutParams(p2);
		
		LinearLayout day3View = (LinearLayout) header.findViewById(R.id.day3View);
		LinearLayout.LayoutParams p3 = (LayoutParams) day1View.getLayoutParams();
		p3.width = ImageUtils.getDisplayWidth(this)/6;
		day3View.setLayoutParams(p3);
		
		LinearLayout day4View = (LinearLayout) header.findViewById(R.id.day4View);
		LinearLayout.LayoutParams p4 = (LayoutParams) day1View.getLayoutParams();
		p4.width = ImageUtils.getDisplayWidth(this)/6;
		day4View.setLayoutParams(p4);
		
		LinearLayout day5View = (LinearLayout) header.findViewById(R.id.day5View);
		LinearLayout.LayoutParams p5 = (LayoutParams) day1View.getLayoutParams();
		p5.width = ImageUtils.getDisplayWidth(this)/6;
		day5View.setLayoutParams(p5);
		
		LinearLayout day6View = (LinearLayout) header.findViewById(R.id.day6View);
		LinearLayout.LayoutParams p6 = (LayoutParams) day1View.getLayoutParams();
		p6.width = ImageUtils.getDisplayWidth(this)/6;
		day6View.setLayoutParams(p6);
		
		day1TV = (TextView) header.findViewById(R.id.day1TV);
		day1maxTV = (TextView) header.findViewById(R.id.day1maxTV);
		day1minTV = (TextView) header.findViewById(R.id.day1minTV);
		day1dIV = (ImageView) header.findViewById(R.id.day1dImageView);
		day1nIV = (ImageView) header.findViewById(R.id.day1nImageView);
		
		day2TV = (TextView) header.findViewById(R.id.day2TV);
		day2maxTV = (TextView) header.findViewById(R.id.day2maxTV);
		day2minTV = (TextView) header.findViewById(R.id.day2minTV);
		day2dIV = (ImageView) header.findViewById(R.id.day2dImageView);
		day2nIV = (ImageView) header.findViewById(R.id.day2nImageView);
		
		day3TV = (TextView) header.findViewById(R.id.day3TV);
		day3maxTV = (TextView) header.findViewById(R.id.day3maxTV);
		day3minTV = (TextView) header.findViewById(R.id.day3minTV);
		day3dIV = (ImageView) header.findViewById(R.id.day3dImageView);
		day3nIV = (ImageView) header.findViewById(R.id.day3nImageView);
		
		day4TV = (TextView) header.findViewById(R.id.day4TV);
		day4maxTV = (TextView) header.findViewById(R.id.day4maxTV);
		day4minTV = (TextView) header.findViewById(R.id.day4minTV);
		day4dIV = (ImageView) header.findViewById(R.id.day4dImageView);
		day4nIV = (ImageView) header.findViewById(R.id.day4nImageView);
		
		day5TV = (TextView) header.findViewById(R.id.day5TV);
		day5maxTV = (TextView) header.findViewById(R.id.day5maxTV);
		day5minTV = (TextView) header.findViewById(R.id.day5minTV);
		day5dIV = (ImageView) header.findViewById(R.id.day5dImageView);
		day5nIV = (ImageView) header.findViewById(R.id.day5nImageView);
		
		day6TV = (TextView) header.findViewById(R.id.day6TV);
		day6maxTV = (TextView) header.findViewById(R.id.day6maxTV);
		day6minTV = (TextView) header.findViewById(R.id.day6minTV);
		day6dIV = (ImageView) header.findViewById(R.id.day6dImageView);
		day6nIV = (ImageView) header.findViewById(R.id.day6nImageView);
		
		initWeekImageUI();
	}
	
	private void initWeekImageUI() {
		LinearLayout.LayoutParams pd1 = (LayoutParams) day1dIV.getLayoutParams();
		pd1.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day1dIV.setLayoutParams(pd1);
		LinearLayout.LayoutParams pn1 = (LayoutParams) day1nIV.getLayoutParams();
		pn1.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day1nIV.setLayoutParams(pn1);
		
		LinearLayout.LayoutParams pd2 = (LayoutParams) day2dIV.getLayoutParams();
		pd2.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day2dIV.setLayoutParams(pd2);
		LinearLayout.LayoutParams pn2 = (LayoutParams) day2nIV.getLayoutParams();
		pn2.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day2nIV.setLayoutParams(pn2);
		
		LinearLayout.LayoutParams pd3 = (LayoutParams) day3dIV.getLayoutParams();
		pd3.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day3dIV.setLayoutParams(pd3);
		LinearLayout.LayoutParams pn3 = (LayoutParams) day3nIV.getLayoutParams();
		pn3.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day3nIV.setLayoutParams(pn3);
		
		LinearLayout.LayoutParams pd4 = (LayoutParams) day4dIV.getLayoutParams();
		pd4.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day4dIV.setLayoutParams(pd4);
		LinearLayout.LayoutParams pn4 = (LayoutParams) day4nIV.getLayoutParams();
		pn4.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day4nIV.setLayoutParams(pn4);
		
		LinearLayout.LayoutParams pd5 = (LayoutParams) day5dIV.getLayoutParams();
		pd5.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day5dIV.setLayoutParams(pd5);
		LinearLayout.LayoutParams pn5 = (LayoutParams) day5nIV.getLayoutParams();
		pn5.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day5nIV.setLayoutParams(pn5);
		
		LinearLayout.LayoutParams pd6 = (LayoutParams) day6dIV.getLayoutParams();
		pd6.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day6dIV.setLayoutParams(pd6);
		LinearLayout.LayoutParams pn6 = (LayoutParams) day6nIV.getLayoutParams();
		pn6.width = screenWidth/6-ImageUtils.dip2px(this, 5);
		day6nIV.setLayoutParams(pn6);
		
	}
	
	
	@SuppressLint("HandlerLeak")
	public void initWaetherData(final String disName, String cityName) {
		getAirIndexFromCache(cityName);
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					String cityCode = (String)msg.obj;
					appContext.setDisName(cityCode);
					getWeatherFromCache(cityCode);
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
							+ disName + "'" + ";";
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
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	private void getAirIndexFromCache(String cityCode) {
		String key = String.format("%s-%s", "AirIndex", cityCode);
		AirIndexEntity entity = (AirIndexEntity) appContext.readObject(key);
		if(entity != null){
			switch (entity.getError_code()) {
			case Result.RESULT_OK:
				wuranLayout.setVisibility(View.VISIBLE);
				pm2TV.setText(entity.pm2_5);
				qualityTV.setText(entity.quality);
				break;
			default:
				break;
			}
		}
		WeatherClient.getAirIndex(appContext, cityCode, new ClientCallback() {
			
			@Override
			public void onSuccess(Entity data) {
				AirIndexEntity entity = (AirIndexEntity) data;
				switch (entity.getError_code()) {
				case Result.RESULT_OK:
					wuranLayout.setVisibility(View.VISIBLE);
					pm2TV.setText(entity.pm2_5);
					qualityTV.setText(entity.quality);
					break;
				default:
					break;
				}
				
			}
			
			@Override
			public void onFailure(String message) {
				
			}
			
			@Override
			public void onError(Exception e) {
				
			}
		});
	}
	
	private void getWeatherFromCache(String cityCode) {
		String key = String.format("%s-%s", "weather", cityCode);
		SKWeather entity = (SKWeather) appContext.readObject(key);
		if(entity != null){
			handleDayWeather(entity);
		}
		
		String key1 = String.format("%s-%s", "weekweather", cityCode);
		ForecastWeather entity1 = (ForecastWeather) appContext.readObject(key1);
		if(entity1 != null){
			handlerWeekWeather(entity1);
		}
	}
	
	private void forecastWeather(final String cityCode) {
		WeatherClient.forcastWeather(appContext, cityCode, new ClientCallback() {
			
			@Override
			public void onSuccess(Entity data) {
				ForecastWeather weather = (ForecastWeather)data;
				if (weather != null) {
					handlerWeekWeather(weather);
				}
				mPullToRefreshView.onHeaderRefreshComplete();
			}
			
			@Override
			public void onFailure(String message) {
				mPullToRefreshView.onHeaderRefreshComplete();
			}
			
			@Override
			public void onError(Exception e) {
				Logger.i(e);
				mPullToRefreshView.onHeaderRefreshComplete();
//				((AppException)e).makeToast(Home.this);
			}
		});
		WeatherClient.forcastWeatherhtml(appContext, cityCode, new ClientCallback() {
			
			@Override
			public void onSuccess(Entity data) {
				IndexEntity index = (IndexEntity) data;
				cleanupTV.setText(index.makeupValue);
			}
			
			@Override
			public void onFailure(String message) {
				
			}
			
			@Override
			public void onError(Exception e) {
				
			}
		});
	}
	
	private void handlerWeekWeather(ForecastWeather weather) {
		weatherInfoView.setVisibility(View.VISIBLE);
		weatherTV.setText(weather.weather1);
		handleWeatherAnimation(weather.weather1);
		minMaxTV.setText(weather.temp1);
		day1TV.setText(weather.day1);
		day2TV.setText(weather.day2);
		day3TV.setText(weather.day3);
		day4TV.setText(weather.day4);
		day5TV.setText(weather.day5);
		day6TV.setText(weather.day6);
   		day1maxTV.setText(weather.day1max+"℃");
   		day1minTV.setText(weather.day1min+"℃");
   		day2maxTV.setText(weather.day2max+"℃");
   		day2minTV.setText(weather.day2min+"℃");
   		day3maxTV.setText(weather.day3max+"℃");
   		day3minTV.setText(weather.day3min+"℃");
   		day4maxTV.setText(weather.day4max+"℃");
   		day4minTV.setText(weather.day4min+"℃");
   		day5maxTV.setText(weather.day5max+"℃");
   		day5minTV.setText(weather.day5min+"℃");
   		day6maxTV.setText(weather.day6max+"℃");
   		day6minTV.setText(weather.day6min+"℃");
	   	
	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img1+".gif", day1dIV, CommonValue.DisplayOptions.default_options);
//	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img2+".gif", day1nIV, CommonValue.DisplayOptions.default_options);
	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img3+".gif", day2dIV, CommonValue.DisplayOptions.default_options);
//	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img4+".gif", day2nIV, CommonValue.DisplayOptions.default_options);
	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img5+".gif", day3dIV, CommonValue.DisplayOptions.default_options);
//	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img6+".gif", day3nIV, CommonValue.DisplayOptions.default_options);
	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img7+".gif", day4dIV, CommonValue.DisplayOptions.default_options);
//	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img8+".gif", day4nIV, CommonValue.DisplayOptions.default_options);
	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img9+".gif", day5dIV, CommonValue.DisplayOptions.default_options);
//	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img10+".gif", day5nIV, CommonValue.DisplayOptions.default_options);
	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img11+".gif", day6dIV, CommonValue.DisplayOptions.default_options);
//	   	imageLoader.displayImage("http://m.weather.com.cn/img/b"+weather.img12+".gif", day6nIV, CommonValue.DisplayOptions.default_options);
	   	
	   	clothTV.setText(weather.index);
		lineTV.setText(weather.index_uv);
		sportTV.setText(weather.index_co);
		carTV.setText(weather.index_xc);
		tripTV.setText(weather.index_tr);
	}
	
	private void handleWeatherAnimation(String weather) {
		homeBg.setBackgroundResource(R.drawable.day_sunny_01);
//		if (weather.indexOf("晴") != -1) {
			defaultImageView.setBackgroundResource(R.anim.feng);
	        Object ob = defaultImageView.getBackground();
	        anim = (AnimationDrawable) ob;
	        anim.setOneShot(true);
	        anim.stop();
	        anim.start();
//		}
//		else if (weather.indexOf("风") != -1) {
////			homeBg.setBackgroundResource(R.drawable.day_sunny_01);
//			defaultImageView.setBackgroundResource(R.anim.feng);
//	        Object ob = defaultImageView.getBackground();
//	        anim = (AnimationDrawable) ob;
//	        anim.setOneShot(true);
//	        anim.stop();
//	        anim.start();
//		}
//		else if (weather.indexOf("雪") != -1) {
////			homeBg.setBackgroundResource(R.drawable.day_sunny_01);
//			homeBg.setBackgroundResource(R.drawable.day_snow_01);
//			defaultImageView.setBackgroundResource(R.anim.xue);
//	        Object ob = defaultImageView.getBackground();
//	        anim = (AnimationDrawable) ob;
//	        anim.setOneShot(true);
//	        anim.stop();
//	        anim.start();
//		}
//		else if (weather.indexOf("雨") != -1) {
////			homeBg.setBackgroundResource(R.drawable.day_sunny_01);
//			homeBg.setBackgroundResource(R.drawable.day_rain_01);
//			defaultImageView.setBackgroundResource(R.anim.yu);
//	        Object ob = defaultImageView.getBackground();
//	        anim = (AnimationDrawable) ob;
//	        anim.setOneShot(true);
//	        anim.stop();
//	        anim.start();
//		}
		try {
			timer.scheduleAtFixedRate(task, 10000, 20000);
		} catch (Exception e) {
			Logger.i(e);
		}
       
	}
	
	private void playAnimation() {
		try {
			Object ob = defaultImageView.getBackground();
	        anim = (AnimationDrawable) ob;
	        anim.setOneShot(true);
	        anim.stop();
			anim.start();
			Logger.i("dddd");
		} catch (Exception e) {
			Logger.i(e);
		}
		
	}
	
	TimerTask task = new TimerTask() {
        @Override
        public void run() {
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
        }
	};
 
	Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
                switch (msg.what) {
                case 0:
                	
                        playAnimation();
                        break;

                default:
                        break;
                }
                return false;
        }
	});
	
	private void notiWeather(SKWeather weather) {
		Intent intent = new Intent();
		intent.putExtra("weather", weather);
		intent.setAction("getweather");
		sendBroadcast(intent);
	}
	
	private void querySKWeather(final String cityCode) {
		WeatherClient.getSKWeather(appContext, cityCode, new ClientCallback() {
			@Override
			public void onSuccess(Entity data) {
				SKWeather weather = (SKWeather) data;
				if (weather != null) {
					handleDayWeather(weather);
				}
				mPullToRefreshView.onHeaderRefreshComplete();
			}
			
			@Override
			public void onFailure(String message) {
				mPullToRefreshView.onHeaderRefreshComplete();
			}
			
			@Override
			public void onError(Exception e) {
				((AppException)e).makeToast(Home.this);
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		});
	}
	
	private void handleDayWeather(SKWeather weather) {
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
//		notiWeather(weather);
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
			initWaetherData(location.getDistrict().substring(0, location.getDistrict().length()-1), location.getCityCode());
			mAMapLocManager.removeUpdates(this);
		}
		else {
			UIHelper.ToastMessage(this, "网络不给力哦，请往下拉再试试", Toast.LENGTH_SHORT);
			mPullToRefreshView.onHeaderRefreshComplete();
		}
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.onFooterRefreshComplete();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		 
		if(!appContext.isNetworkConnected()) {
        	UIHelper.ToastMessage(this, R.string.network_not_connected, Toast.LENGTH_SHORT);
        	mPullToRefreshView.onHeaderRefreshComplete();
        }
		else {
			defaultImageView.clearAnimation();
			defaultImageView.setBackgroundResource(R.drawable.feng0001);
			mAMapLocManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 1000, 10, this);
		}
	}
}
