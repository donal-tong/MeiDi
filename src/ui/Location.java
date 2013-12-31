package ui;

import java.util.ArrayList;
import java.util.List;

import tools.AppManager;
import tools.DistrictDataBase;
import tools.Logger;

import com.vikaa.meidi.R;

import adapter.CityAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Location extends AppActivity implements OnItemClickListener {
	private ListView mListView;
	private CityAdapter adapter;
	private List<String> citys = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		initUI();
		getCitys();
	}
	
	private void initUI() {
		mListView = (ListView) findViewById(R.id.listView);
		mListView.setDividerHeight(0);
		adapter = new CityAdapter(this, citys);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
	}
	
	private void getCitys() {
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				List<String> data = (List<String>) msg.obj;
				citys.clear();
				citys.addAll(data);
				adapter.notifyDataSetChanged();
			}
		};
		new Thread(){
			public void run() {
				Message msg = new Message();
				List<String> list = DistrictDataBase.getCitys(getApplicationContext());
				msg.what = 1;
				msg.obj = list;
				handler.sendMessage(msg);
			};
		}.start();
	}
	
	public void ButtonClick(View v) {
		switch (v.getId()) {
		case R.id.leftBarButton:
			AppManager.getAppManager().finishActivity(this);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		String city = citys.get(position);
		Intent intent = new Intent();
		intent.putExtra("city", city);
		setResult(RESULT_OK, intent);
		AppManager.getAppManager().finishActivity(Location.this);
	}
}
