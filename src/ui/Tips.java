package ui;

import com.vikaa.meidi.R;

import config.CommonValue;

import adapter.TipAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import tools.BaseActivity;
import tools.Logger;
import tools.StringUtils;
import widget.PullToRefreshListView;
import widget.PullToRefreshListView.OnRefreshListener;

public class Tips extends AppActivity {
	private PullToRefreshListView mlistView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tip);
//		setUI();
	}
	
//	private void setUI() {
//		mlistView = (PullToRefreshListView) findViewById(R.id.listView);
//		mlistView.setDividerHeight(0);
//		mlistView.setAdapter(new TipAdapter(this));
//		mlistView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				Logger.i(""+arg2);
//				String url = String.format("http://42.120.12.71/md/post-%d.html", arg2);
//				showCreate(url);
//			}
//		});
//		mlistView.setOnRefreshListener(new OnRefreshListener() {
//			
//			@Override
//			public void onRefresh() {
//				mlistView.onRefreshComplete();
//			}
//		});
//	}
//	
//	private void showCreate(String url) {
//		Intent intent = new Intent(this,CreateView.class);
//		intent.putExtra("url", url);
//        startActivity(intent);
//	}
}
