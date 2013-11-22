package ui;

import com.vikaa.meidi.R;

import adapter.TipAdapter;
import android.os.Bundle;
import tools.BaseActivity;
import tools.StringUtils;
import widget.PullToRefreshListView;

public class Tips extends BaseActivity {
	private PullToRefreshListView mlistView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tip);
		setUI();
	}
	
	private void setUI() {
		mlistView = (PullToRefreshListView) findViewById(R.id.listView);
		mlistView.setDividerHeight(0);
		mlistView.setAdapter(new TipAdapter());
	}
}
