package config;


import bean.ForecastWeather;
import bean.SKWeather;

import tools.AppContext;
import tools.AppException;
import tools.HttpUtil;

public class ApiClent {
	
	public static SKWeather getSKWeather(AppContext appContext, String cityCode) throws AppException{
		try{
			return SKWeather.pase(HttpUtil.getRequest("http://www.weather.com.cn/data/sk/"+cityCode+".html", appContext));
		}catch (Exception e) {
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}
	}
	
	public static ForecastWeather forcastWeather(AppContext appContext, String cityCode) throws AppException{
		try{
			return ForecastWeather.pase(HttpUtil.getRequest("http://m.weather.com.cn/data/"+cityCode+".html", appContext));
		}catch (Exception e) {
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}
	}
}
