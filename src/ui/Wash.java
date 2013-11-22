package ui;

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
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
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
	private CategorySeries mSeries = new CategorySeries("");
	/** The main renderer for the main dataset. */
	private DefaultRenderer mRenderer = new DefaultRenderer();
	private GraphicalView mChartView;
	static SKWeather weather;
	private boolean isDraw;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wash);
		setUI();
		mRenderer.setInScroll(false);
		mRenderer.setPanEnabled(false);
		isDraw = false;
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
		mSeries.clear();
		mChartView.repaint();
		isDraw = false;
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
//	            Toast.makeText(
//	            		Wash.this,
//	                "Chart data point index " + seriesSelection.getPointIndex() + " selected"
//	                    + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
	            showShare(true, null);
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
	
	private void showShare(boolean silent, String platform) {
		final OnekeyShare oks = new OnekeyShare();
		oks.setNotification(R.drawable.ic_launcher, getApplicationContext().getString(R.string.app_name));
		oks.setTitle("#美的#");
		oks.setTitleUrl("www.momoka.cc");
		oks.setText("#美的#");
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
	/**
	 * 接受weather的广播
	 * @author Donal Tong
	 */
	public static class WeatherReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			weather = (SKWeather) intent.getExtras().getSerializable("weather");
		}
	}
	
}
