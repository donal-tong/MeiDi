package config;

import org.apache.http.Header;

import tools.Logger;

import com.loopj.android.http.AsyncHttpResponseHandler;
import bean.Entity;
import bean.ForecastWeather;
import bean.SKWeather;

public class WeatherClient {
    
    private static void saveCache(MeiDiApp appContext, String key, Entity entity) {
    	appContext.saveObject(entity, key);
    }
    
    public static void getSKWeather(final MeiDiApp appContext, final String cityCode, final ClientCallback callback) {
    	QYRestClient.get("http://www.weather.com.cn/data/sk/"+cityCode+".html", null, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] content) {
				try{
					Logger.i(new String(content));
					SKWeather data = SKWeather.pase(new String(content));
					callback.onSuccess(data);
					saveCache(appContext, "weather-"+cityCode, data);
				}catch (Exception e) {
					callback.onError(e);
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				
			}
		});
    }
    
    public static void forcastWeather(final MeiDiApp appContext, final String cityCode, final ClientCallback callback) {
    	QYRestClient.get("http://m.weather.com.cn/data/"+cityCode+".html", null, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] content) {
				try{
					ForecastWeather data = ForecastWeather.pase(new String(content));
					callback.onSuccess(data);
					saveCache(appContext, "weekweather-"+cityCode, data);
				}catch (Exception e) {
					callback.onError(e);
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				
			}
		});
    }
    
    public interface ClientCallback{
        abstract void onSuccess(Entity data);
        abstract void onFailure(String message);
        abstract void onError(Exception e);
    }
}
