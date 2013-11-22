package config;

import tools.AppContext;
import tools.Logger;

public class MeiDiApp extends AppContext{

	@Override
	public void onCreate() {
		super.onCreate();
		Logger.getLogger().setTag("MeiDi");
	}
	
}
