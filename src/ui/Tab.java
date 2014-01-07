package ui;

import com.crashlytics.android.Crashlytics;
import java.io.IOException;

import tools.AppContext;
import tools.AppManager;
import tools.DataBaseUtil;
import tools.Logger;

import cn.sharesdk.framework.ShareSDK;

import com.vikaa.meidi.R;

import config.MeiDiApp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Tab extends TabActivity  implements OnCheckedChangeListener{
	private MeiDiApp ac;
	private RadioGroup mainTab;
	public static TabHost mTabHost;
	
	private Intent homeIntent;
	private Intent nearmeIntent;
	private Intent meIntent;
	
	private final static String TAB_TAG_HOME = "tab_tag_home";
	private final static String TAB_TAG_NEARME = "tab_tag_nearme";
	private final static String TAB_TAG_ME = "tab_tag_me";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crashlytics.start(this);
		setContentView(R.layout.tab);
		ShareSDK.initSDK(this);
		AppManager.getAppManager().addActivity(this);
        mainTab=(RadioGroup)findViewById(R.id.main_tab);
        mainTab.setOnCheckedChangeListener(this);
        prepareIntent();
        setupIntent();
        RadioButton homebutton = (RadioButton)findViewById(R.id.radio_button1);
        homebutton.setChecked(true);
        ac = (MeiDiApp) getApplication();
        copyDataBaseToPhone();
	}
	
	@Override
	protected void onDestroy() {
		ShareSDK.stopSDK(this);
		super.onDestroy();
	}
	
	private void prepareIntent() {
		homeIntent = new Intent(this, Home.class);
		nearmeIntent = new Intent(this, Wash.class);
		meIntent = new Intent(this, Tips.class);
	}
	
	private void setupIntent() {
		mTabHost = getTabHost();
		TabHost localTabHost = mTabHost;
		localTabHost.addTab(buildTabSpec(TAB_TAG_HOME, R.string.main_home, R.drawable.tabbar_button1, homeIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_NEARME, R.string.main_diy, R.drawable.tabbar_button2, nearmeIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_ME, R.string.main_my_order, R.drawable.tabbar_button3, meIntent));
	}
	
	/**
	 * ????TabHost??Tab?
	 * @param tag ???
	 * @param resLabel ???
	 * @param resIcon ???
	 * @param content ??tab????????
	 * @return ???tab
	 */
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,final Intent content) {
		return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
	} 
	
	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		switch(checkedId){
		case R.id.radio_button1:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
			break;
		case R.id.radio_button2:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_NEARME);
			break;
		case R.id.radio_button3:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_ME);
			break;
		}
	}
	
	public void tabClick(View v) {
		
	}
	
	private void copyDataBaseToPhone() {  
        DataBaseUtil util = new DataBaseUtil(this);  
        // ??????????????  
        boolean dbExist = util.checkDataBase();  
  
        if (dbExist) {  
            Logger.i("The database is exist.");  
        } else {// ????????raw??????????????  
            try {  
                util.copyDataBase();  
            } catch (IOException e) {  
                throw new Error("Error copying database");  
            }  
        }  
    }
}
