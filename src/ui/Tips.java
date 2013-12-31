package ui;

import java.util.ArrayList;
import java.util.List;

import moji.MojiEntity;

import bean.Entity;
import bean.TipDisplayListEntity;
import bean.TipEntity;
import bean.TipListEntity;

import com.vikaa.meidi.R;

import config.CommonValue;
import config.WeatherClient;
import config.WeatherClient.ClientCallback;

import adapter.HomeAdapter;
import adapter.TipAdapter;
import android.R.mipmap;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import tools.BaseActivity;
import tools.ImageUtils;
import tools.Logger;
import tools.StringUtils;
import widget.PullToRefreshListView;
import widget.PullToRefreshListView.OnRefreshListener;

public class Tips extends AppActivity {
	private PullToRefreshListView mlistView;
	private TipAdapter mAdapter;
	private List<TipDisplayListEntity> datas;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tip);
		setUI();
		getTipFromCache();
	}
	
	private void setUI() {
		mlistView = (PullToRefreshListView) findViewById(R.id.listView);
		mlistView.setDividerHeight(0);
		datas = new ArrayList<TipDisplayListEntity>();
		mAdapter = new TipAdapter(this, datas);
		mlistView.setAdapter(mAdapter);
		mlistView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				mlistView.onRefreshComplete();
			}
		});
	}
	
	private void getTipFromCache() {
		String key = "tip";
		TipListEntity entity = (TipListEntity) appContext.readObject(key);
		if(entity != null){
			handleTip(entity);
		}
		getTip();
	}
	
	private void getTip() {
		WeatherClient.getTip(appContext, new ClientCallback() {
			
			@Override
			public void onSuccess(Entity data) {
				TipListEntity list = (TipListEntity) data;
				handleTip(list);
			}
			
			@Override
			public void onFailure(String message) {
				
			}
			
			@Override
			public void onError(Exception e) {
				
			}
		});
	}
	
	private void handleTip(TipListEntity list) {
		datas.clear();
		for (int i = 0; i < list.tips.size(); i++) {
			Logger.i((i%4)+" "+(i/4));
			if (i==0 || i/4 != (i-1)/4) {
				TipDisplayListEntity tip = new TipDisplayListEntity();
				tip.tips.add(list.tips.get(i));
				datas.add(tip);
			}
			else {
				Logger.i("add");
				datas.get(i/4).tips.add(list.tips.get(i));
			}
		}
		mAdapter.notifyDataSetChanged();
	}
}
