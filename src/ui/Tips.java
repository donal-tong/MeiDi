package ui;

import java.util.ArrayList;
import java.util.List;

import bean.TipDisplayListEntity;
import bean.TipEntity;
import bean.TipListEntity;

import com.vikaa.meidi.R;

import config.CommonValue;

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
		addData();
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
	
	private void addData() {
		TipListEntity list = new TipListEntity();
		TipEntity tip1 = new TipEntity();
		tip1.image = "a";
		tip1.url = "http://42.120.12.71/md/post-1.html";
		list.tips.add(tip1);
		TipEntity tip2 = new TipEntity();
		tip2.image = "a";
		tip2.url = "http://42.120.12.71/md/post-2.html";
		list.tips.add(tip2);
		TipEntity tip3 = new TipEntity();
		tip3.image = "a";
		tip3.url = "http://42.120.12.71/md/post-3.html";
		list.tips.add(tip3);
		TipEntity tip4 = new TipEntity();
		tip4.image = "a";
		tip4.url = "http://42.120.12.71/md/post-4.html";
		list.tips.add(tip4);
		TipEntity tip5 = new TipEntity();
		tip5.image = "a";
		tip5.url = "http://42.120.12.71/md/post-5.html";
		list.tips.add(tip5);
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
