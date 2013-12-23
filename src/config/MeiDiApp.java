package config;

import com.nostra13.universalimageloader.utils.L;

import tools.AppContext;
import tools.AppException;
import tools.Logger;

public class MeiDiApp extends AppContext{

	@Override
	public void onCreate() {
		super.onCreate();
		Logger.getLogger().setTag("MeiDi");
		Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
		L.disableLogging();
		Logger.setDebug(true);
	}
	
}
