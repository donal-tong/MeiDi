package ui;

import java.io.File;
import java.io.FileOutputStream;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import bean.SKWeather;


import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

import com.vikaa.meidi.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import tools.BaseActivity;
import tools.CalendarUtil;
import tools.HttpUtil;
import tools.Logger;

public class Wash extends BaseActivity{
	private TextView dayTV;
	private TextView tempTV;
	private TextView wetTV;
	private TextView waterTempTV;
	private TextView waterTimeTV;
	private TextView waterFallTV;
	private String waterTempString;
	/** Colors to be used for the pie slices. */
	private static CategorySeries mSeries = new CategorySeries("");
	/** The main renderer for the main dataset. */
	private DefaultRenderer mRenderer = new DefaultRenderer();
	private static GraphicalView mChartView;
	static SKWeather weather;
	private static boolean isDraw;
	private static boolean isTaked;
	private String imagePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wash);
		setUI();
		mRenderer.setInScroll(false);
		mRenderer.setPanEnabled(false);
		isDraw = false;
		isTaked = false;
		mRenderer.setStartAngle(90);
		mRenderer.setShowLegend(false);
		mRenderer.setLabelsColor(getResources().getColor(R.color.pie_paint_color));
		mRenderer.setLabelsTextSize(15);
		mRenderer.setScale(0.8f);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		mSeries = (CategorySeries) savedState.getSerializable("current_series");
		mRenderer = (DefaultRenderer) savedState.getSerializable("current_renderer");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("current_series", mSeries);
		outState.putSerializable("current_renderer", mRenderer);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	protected void onResume() {
	    super.onResume();
	    if (mChartView == null) {
	      LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
	      mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
	      mRenderer.setClickEnabled(true);
	      mChartView.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	          SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
	          if (seriesSelection == null) {
//	            Toast.makeText(Wash.this, "No chart element selected", Toast.LENGTH_SHORT)
//	                .show();
	          } else {
	            for (int i = 0; i < mSeries.getItemCount(); i++) {
	              mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
	            }
	            mChartView.repaint();
	          }
	        }
	      });
	      layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT,
	          LayoutParams.MATCH_PARENT));
	    } else {
	      mChartView.repaint();
	    }
	    if (weather != null) {
	    	setWeaterTV();
	    	drawPie();
		}
	}
	
	private void setUI() {
		dayTV = (TextView) findViewById(R.id.dayTV);
		tempTV = (TextView) findViewById(R.id.tempTV);
		wetTV = (TextView) findViewById(R.id.wetTV);
		waterTimeTV = (TextView) findViewById(R.id.waterTimeTV);
		waterFallTV = (TextView) findViewById(R.id.waterFallTV);
		waterTempTV = (TextView) findViewById(R.id.waterTempTV);
		
		CalendarUtil day = new CalendarUtil(0);
		dayTV.setText(day.Date2String(day.getDay(),"M月d日") + " " + day.getWeek(day.getDay()));
	}
	
	private void setWeaterTV() {
		tempTV.setText("温度"+weather.temp+"℃");
		wetTV.setText("相对湿度"+weather.SD);
	}
	
	private void drawPie() {
		int temp = Integer.valueOf(weather.temp);
		if (temp <= 10) {
			waterTempString = "35~40℃";
		} else if (temp >10 && temp < 20) {
			waterTempString = "30~35℃";
		} else if (temp >=20 && temp < 25) {
			waterTempString = "10~20℃";
		} else if (temp >= 25) {
			waterTempString = "0~10℃";
		}
		waterTempTV.setText("水温"+waterTempString);
		waterFallTV.setText("水量适中");
		waterTimeTV.setText("时长15～20min");
		if (!isDraw) {
			isDraw = true;
			drawFall(30);
			drawTemp(40);
			drawTime(30);
		}
	}
	
	private void drawTemp(int value) {
		mSeries.add(waterTempString, value);
        SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
        renderer.setColor(getResources().getColor(R.color.temp_color));
        mRenderer.addSeriesRenderer(renderer);
        mChartView.repaint();
	}
	
	private void drawTime(int value) {
		mSeries.add("时长15～20min" , value);
        SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
        renderer.setColor(getResources().getColor(R.color.time_color));
        mRenderer.addSeriesRenderer(renderer);
        mChartView.repaint();
	}
	
	private void drawFall(int value) {
		mSeries.add("水量适中" , value);
        SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
        renderer.setColor(getResources().getColor(R.color.fall_color));
        mRenderer.addSeriesRenderer(renderer);
        mChartView.repaint();
	}
	
	public Bitmap getViewBitmap(View v) {
        v.clearFocus(); // 清除视图焦点
        v.setPressed(false);// 将视图设为不可点击

        boolean willNotCache = v.willNotCacheDrawing(); // 返回视图是否可以保存他的画图缓存
        v.setWillNotCacheDrawing(false);

     //将视图在此操作时置为透明
        int color = v.getDrawingCacheBackgroundColor(); // 获得绘制缓存位图的背景颜色
        v.setDrawingCacheBackgroundColor(0); // 设置绘图背景颜色
        if (color != 0) { // 如果获得的背景不是黑色的则释放以前的绘图缓存
                v.destroyDrawingCache(); // 释放绘图资源所使用的缓存
        }
        v.buildDrawingCache(); // 重新创建绘图缓存，此时的背景色是黑色
        Bitmap cacheBitmap = v.getDrawingCache(); // 将绘图缓存得到的,注意这里得到的只是一个图像的引用
        if (cacheBitmap == null) {
                return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap); // 将位图实例化
        //恢复视图
        v.destroyDrawingCache();// 释放位图内存
        v.setWillNotCacheDrawing(willNotCache);// 返回以前缓存设置
        v.setDrawingCacheBackgroundColor(color);// 返回以前的缓存颜色设置
         
        return bitmap;
	}
	
	private void takeshot() {
		RelativeLayout l = (RelativeLayout) findViewById(R.id.chartLayout);
		if (!isTaked) {
			try {
				Bitmap mScreenBitmap = getViewBitmap(l);
				imagePath = Environment.getExternalStorageDirectory()+File. separator+"Screenshot.png" ;
			    FileOutputStream out = new FileOutputStream(imagePath);
		        mScreenBitmap.compress(Bitmap.CompressFormat. PNG, 100, out);
		        isTaked = true;
		    } catch (Exception e) {
		        isTaked = false;
		    }
		}
		
		
	}
	
	private void showShare(boolean silent, String platform) {
		final OnekeyShare oks = new OnekeyShare();
		oks.setNotification(R.drawable.ic_launcher, getApplicationContext().getString(R.string.app_name));
		oks.setTitle("#美的#");
		oks.setTitleUrl("www.momoka.cc");
		oks.setText("#美的#");
		oks.setImagePath(imagePath);
		oks.setSilent(silent);
		if (platform != null) {
			oks.setPlatform(platform);
		}
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
			@Override
			public void onShare(Platform platform, ShareParams paramsToShare) {
				
			}
		});
		oks.show(getApplicationContext());
	}
	
	public void OnClick(View v) {
		takeshot();
        showShare(true, null);
	}
	
	/**
	 * 接受weather的广播
	 * @author Donal Tong
	 */
	public static class WeatherReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			weather = (SKWeather) intent.getExtras().getSerializable("weather");
			
			if (mChartView != null) {
				Logger.i("a");
				mSeries.clear();
				mChartView.repaint();
				isDraw = false;
				isTaked = false;
			}
		}
	}
	
	
	
}
