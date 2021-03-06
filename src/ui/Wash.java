package ui;

import moji.MojiEntity;
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
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import tools.Logger;

public class Wash extends AppActivity{
	private TextView tempTV;
	private TextView windTV;
	private TextView wetTV;
	private TextView waterTempTV;
	private TextView timeTV;
	private TextView fallTV;
	
	static MojiEntity moji;

	private String imagePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wash);
		setUI();
	}
	
	private void setUI() {
		tempTV = (TextView) findViewById(R.id.tempTV);
		windTV = (TextView) findViewById(R.id.windTV);
		wetTV = (TextView) findViewById(R.id.wetTV);
		waterTempTV = (TextView) findViewById(R.id.waterTempTV);
		timeTV = (TextView) findViewById(R.id.timeTV);
		fallTV = (TextView) findViewById(R.id.fallTV);
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
//		if (!isTaked) {
//			try {
//				Bitmap mScreenBitmap = getViewBitmap(l);
//				imagePath = Environment.getExternalStorageDirectory()+File. separator+"Screenshot.png" ;
//			    FileOutputStream out = new FileOutputStream(imagePath);
//		        mScreenBitmap.compress(Bitmap.CompressFormat. PNG, 100, out);
//		        isTaked = true;
//		    } catch (Exception e) {
//		        isTaked = false;
//		    }
//		}
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
	
	
	@Override
	protected void onResume() {
		super.onResume();
//		String cityCode = appContext.getDisName();
//		if (cityCode.length() != 0) {
//			String key = String.format("%s-%s", "weather", cityCode);
//			SKWeather entity = (SKWeather) appContext.readObject(key);
////			if(entity != null){
////				weather = entity;
////				drawPie();
////			}
//		}
		
		String mojiCityId = appContext.getMojiCityId();
		if (mojiCityId.length() != 0) {
			String key = String.format("%s-%s", "moji", mojiCityId);
			MojiEntity entity = (MojiEntity) appContext.readObject(key);
			if(entity != null){
				moji = entity;
				drawPie();
			}
		}
		
	}
	
	private void drawPie() {
		String waterTempString = "35~40℃";
		int temp = Integer.valueOf(moji.cc.tmp);
		if (temp <= 10) {
			waterTempString = ("35~40℃");
		} else if (temp >10 && temp < 20) {
			waterTempString = "30~35℃";
		} else if (temp >=20 && temp < 25) {
			waterTempString = "10~20℃";
		} else if (temp >= 25) {
			waterTempString = "0~10℃";
		}
		waterTempTV.setText("水温"+waterTempString);
		fallTV.setText("水量适中");
		timeTV.setText("时长35～40min");
		tempTV.setText(moji.cc.htmp+"/"+moji.cc.ltmp+"℃");
		windTV.setText(String.format("%s\n%s级", moji.cc.wdir, moji.cc.wl));
		wetTV.setText("湿度\n"+moji.cc.hum+"%");
	}
	
}
