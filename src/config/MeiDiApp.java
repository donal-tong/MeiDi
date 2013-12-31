package config;

import java.util.Properties;

import com.nostra13.universalimageloader.utils.L;

import tools.AppContext;
import tools.AppException;
import tools.Logger;
import tools.StringUtils;

public class MeiDiApp extends AppContext{

	@Override
	public void onCreate() {
		super.onCreate();
		Logger.getLogger().setTag("MeiDi");
		Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
		L.disableLogging();
		Logger.setDebug(true);
	}
	
//	public boolean isTapEnable() {
//		try {
//			String loginStr = getProperty("tap.enable");
//			if (StringUtils.isEmpty(loginStr)) {
//				return false;
//			}
//			else {
//				return (loginStr.equals("1")) ? true : false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//	
//	public void setTapEnable() {
//		setProperties(new Properties(){
//			{
//				setProperty("tap.enable","1");
//			}
//		});
//	}
//	
//	public void setTapDisable() {
//		setProperties(new Properties(){
//			{
//				setProperty("tap.enable","0");
//			}
//		});
//	}
	
	public void setDisName(final String name) {
		setProperties(new Properties(){
			{
				setProperty("disname",name);
			}
		});
	}
	
	public String getDisName() {
		return (getProperty("disname"));
	}
	
	public void setMojiCityId(final String id) {
		setProperties(new Properties(){
			{
				setProperty("MojiCityId",id);
			}
		});
	}
	
	public String getMojiCityId() {
		return (getProperty("MojiCityId"));
	}
}
