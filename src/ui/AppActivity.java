package ui;

import config.MeiDiApp;
import android.os.Bundle;
import tools.AppContext;
import tools.BaseActivity;
import tools.Logger;

public class AppActivity extends BaseActivity {
	protected MeiDiApp appContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appContext =  (MeiDiApp)getApplication();
	}
}
