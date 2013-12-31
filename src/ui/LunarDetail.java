package ui;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.AppManager;
import tools.Logger;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vikaa.meidi.R;

public class LunarDetail extends AppActivity{
	private TextView dayTV;
	private TextView lunarTV;
	private TextView yTV;
	private TextView jTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lunar_detail);
		initUI();
		initData();
	}
	
	private void  initUI() {
		dayTV = (TextView) findViewById(R.id.dayTV);
		lunarTV = (TextView) findViewById(R.id.lunTV);
		yTV = (TextView) findViewById(R.id.yTV);
		jTV = (TextView) findViewById(R.id.jTV);
	}
	
	private void initData() {
		String time = getIntent().getStringExtra("lunar");
		try {
			String key = String.format("d%s%s", time.substring(5,7), time.substring(8,10));
			dayTV.setText(time.substring(8,10));
			lunarTV.setText(time.substring(11));
			InputStream is = getResources().openRawResource(R.raw.rili);
			int i = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
			String content = baos.toString();
			JSONObject obj = new JSONObject(content);
			JSONObject d = obj.getJSONObject(key);
			yTV.setText("宜:"+d.getString("y"));
			jTV.setText("忌:"+d.getString("j"));
		} catch (Exception e) {
			Logger.i(e);
		}
	}
	
	public void ButtonClick(View v) {
		switch (v.getId()) {
		case R.id.leftBarButton:
			AppManager.getAppManager().finishActivity(this);
			break;
		}
	}
}
