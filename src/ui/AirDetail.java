package ui;

import moji.IdxListEntity;
import moji.MojiEntity;
import tools.AppManager;
import tools.ImageUtils;
import tools.Logger;

import com.vikaa.meidi.R;

import android.R.integer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class AirDetail extends AppActivity{
	private MojiEntity moji;
	float x;
	private View view1;
	private View view2;
	private View view3;
	private View view4;
	private View view5;
	private View view6;
	
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	private TextView tv6;
	
	private TextView desTV;
	private TextView windTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.air_detail);
		moji = (MojiEntity) getIntent().getSerializableExtra("moji");
		initWeekWeatherUI();
		setIdx();
	}
	
	private void initWeekWeatherUI() {
		int screenWidth = ImageUtils.getDisplayWidth(this) - ImageUtils.dip2px(this, 64);
		RelativeLayout day1View = (RelativeLayout) findViewById(R.id.day1View);
		LinearLayout.LayoutParams p1 = (LayoutParams) day1View.getLayoutParams();
		p1.width = screenWidth/6;
		day1View.setLayoutParams(p1);
		
		RelativeLayout day2View = (RelativeLayout) findViewById(R.id.day2View);
		LinearLayout.LayoutParams p2 = (LayoutParams) day2View.getLayoutParams();
		p2.width = screenWidth/6;
		day2View.setLayoutParams(p2);
		
		RelativeLayout day3View = (RelativeLayout) findViewById(R.id.day3View);
		LinearLayout.LayoutParams p3 = (LayoutParams) day1View.getLayoutParams();
		p3.width = screenWidth/6;
		day3View.setLayoutParams(p3);
		
		RelativeLayout day4View = (RelativeLayout) findViewById(R.id.day4View);
		LinearLayout.LayoutParams p4 = (LayoutParams) day1View.getLayoutParams();
		p4.width = screenWidth/6;
		day4View.setLayoutParams(p4);
		
		RelativeLayout day5View = (RelativeLayout) findViewById(R.id.day5View);
		LinearLayout.LayoutParams p5 = (LayoutParams) day1View.getLayoutParams();
		p5.width = screenWidth/6;
		day5View.setLayoutParams(p5);
		
		RelativeLayout day6View = (RelativeLayout) findViewById(R.id.day6View);
		LinearLayout.LayoutParams p6 = (LayoutParams) day1View.getLayoutParams();
		p6.width = screenWidth/6;
		day6View.setLayoutParams(p6);
		
		view1 = (View) findViewById(R.id.view1);
		view2 = (View) findViewById(R.id.view2);
		view3 = (View) findViewById(R.id.view3);
		view4 = (View) findViewById(R.id.view4);
		view5 = (View) findViewById(R.id.view5);
		view6 = (View) findViewById(R.id.view6);
		
		tv1 = (TextView) findViewById(R.id.day1TV);
		tv2 = (TextView) findViewById(R.id.day2TV);
		tv3 = (TextView) findViewById(R.id.day3TV);
		tv4 = (TextView) findViewById(R.id.day4TV);
		tv5 = (TextView) findViewById(R.id.day5TV);
		tv6 = (TextView) findViewById(R.id.day6TV);
		
		desTV = (TextView ) findViewById(R.id.des);
		windTV = (TextView)findViewById(R.id.windTV);
		
		windTV.setText(String.format("%s\n%s", moji.air.lv, moji.air.aqigrade));
		for (IdxListEntity idx : moji.idxs) {
   			if (idx.nm.equals("运动指数")) {
   				desTV.setText(String.format("Mr-Hot   提醒您，%s", idx.recom));
			}
		}
		
		TextView timeTV = (TextView) findViewById(R.id.timeTV);
		timeTV.setText(String.format("%s%s发布", moji.air.title, moji.cc.upt));
	}
	
	public void ButtonClick(View v){
		switch (v.getId()) {
		case R.id.leftBarButton:
			AppManager.getAppManager().finishActivity(this);
			break;

		default:
			break;
		}
	}
	
	private void setIdx() {
		x = 1;
		int lv = Integer.valueOf(moji.air.lv);
	    int pm1 = Integer.valueOf(moji.air.pmtenaqi);
	    int no = Integer.valueOf(moji.air.no2);
	    int so = Integer.valueOf(moji.air.so2);
	    int o = Integer.valueOf(moji.air.o3);
	    int co = Integer.valueOf(moji.air.co);
	    if (lv>=400) {
	        x=5;
	    }
	    else if (lv >= 300) {
	        x = 4f;
	    }
	    else if (lv >= 200) {
	        x = 3;
	    }
	    else if (lv >= 150) {
	        x = 2f;
	    }
	    else if (lv >= 100) {
	        x = 1.5f;
	    }
	    else if (lv >= 50) {
	        x = 1f;
	    }
	    tv1.setText(pm1+"");
	    tv2.setText(lv+"");
	    tv3.setText(no+"");
	    tv4.setText(so+"");
	    tv5.setText(o+"");
	    tv6.setText(co+"");
	    
	    RelativeLayout.LayoutParams p1 = (RelativeLayout.LayoutParams) view1.getLayoutParams();
	    p1.height = (int) (pm1/x +5);
	    view1.setLayoutParams(p1);
	    view1.setBackgroundColor(getResources().getColor(getColor(pm1)));
	    
	    RelativeLayout.LayoutParams p2 = (RelativeLayout.LayoutParams) view2.getLayoutParams();
	    p2.height = (int) (lv/x +5);
	    view2.setLayoutParams(p2);
	    view2.setBackgroundColor(getResources().getColor(getColor(lv)));
	    
	    RelativeLayout.LayoutParams p3 = (RelativeLayout.LayoutParams) view3.getLayoutParams();
	    p3.height = (int) (no/x +5);
	    view3.setLayoutParams(p3);
	    view3.setBackgroundColor(getResources().getColor(getColor(no)));
	    
	    RelativeLayout.LayoutParams p4 = (RelativeLayout.LayoutParams) view4.getLayoutParams();
	    p4.height = (int) (so/x +5);
	    view4.setLayoutParams(p4);
	    view4.setBackgroundColor(getResources().getColor(getColor(so)));
	    
	    RelativeLayout.LayoutParams p5 = (RelativeLayout.LayoutParams) view5.getLayoutParams();
	    p5.height = (int) (o/x +5);
	    view5.setLayoutParams(p5);
	    view5.setBackgroundColor(getResources().getColor(getColor(o)));
	    
	    RelativeLayout.LayoutParams p6 = (RelativeLayout.LayoutParams) view6.getLayoutParams();
	    p6.height = (int) (co/x +5);
	    view6.setLayoutParams(p6);
	    view6.setBackgroundColor(getResources().getColor(getColor(co)));
	}
	
	private  int getColor(int value)
	{
	    if (value >= 500) {
	        return R.color.air7;
	    }
	    else if (value >= 300) {
	    	return R.color.air6;
	    }
	    else if (value >= 200) {
	    	return R.color.air5;
	    }
	    else if (value >= 150) {
	    	return R.color.air4;
	    }
	    else if (value >= 100) {
	    	return R.color.air3;
	    }
	    else if (value >= 50) {
	    	Logger.i("s");
	    	return R.color.air2;
	    }
	    else {
	    	return R.color.air1;
	    }
	}
}
