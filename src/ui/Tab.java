package ui;

import tools.AppContext;
import tools.AppManager;

import cn.sharesdk.framework.ShareSDK;

import com.vikaa.meidi.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Tab extends TabActivity  implements OnCheckedChangeListener{
	private AppContext ac;
	private RadioGroup mainTab;
	public static TabHost mTabHost;
	
	//内容Intent
	private Intent homeIntent;
	private Intent nearmeIntent;
	private Intent meIntent;
	
	private final static String TAB_TAG_HOME = "tab_tag_home";
	private final static String TAB_TAG_NEARME = "tab_tag_nearme";
	private final static String TAB_TAG_ME = "tab_tag_me";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		ShareSDK.initSDK(this);
		AppManager.getAppManager().addActivity(this);
        mainTab=(RadioGroup)findViewById(R.id.main_tab);
        mainTab.setOnCheckedChangeListener(this);
        prepareIntent();
        setupIntent();
        RadioButton homebutton = (RadioButton)findViewById(R.id.radio_button1);
        homebutton.setChecked(true);
        ac = (AppContext) getApplication();
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
	 * 构建TabHost的Tab页
	 * @param tag 标记
	 * @param resLabel 标签
	 * @param resIcon 图标
	 * @param content 该tab展示的内容
	 * @return 一个tab
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
}
