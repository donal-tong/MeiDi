package config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;

import tools.Logger;

import com.loopj.android.http.AsyncHttpResponseHandler;
import bean.Entity;
import bean.ForecastWeather;
import bean.IndexEntity;
import bean.SKWeather;

public class WeatherClient {
    
    private static void saveCache(MeiDiApp appContext, String key, Entity entity) {
    	appContext.saveObject(entity, key);
    }
    
    public interface ClientCallback{
        abstract void onSuccess(Entity data);
        abstract void onFailure(String message);
        abstract void onError(Exception e);
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
    
    public static void forcastWeatherhtml(final MeiDiApp appContext, final String cityCode, final ClientCallback callback) {
    	QYRestClient.get("http://www.weather.com.cn/weather/"+cityCode+".shtml", null, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] content) {
				String s = new String(content);
				s = s.replaceAll("(\n|\r\n)\\s+", "");
				String regex = "<section class=\"detail pp\"><aside><b>(.*?)</b>(.*?)</aside>.*</section>";
				Pattern pa = Pattern.compile(regex);
				Matcher m = pa.matcher(s);
				if (m.find()) {
					IndexEntity index = new IndexEntity();
					index.makeupValue = m.group(1);
					callback.onSuccess(index);
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
			}
		});
    }
    
}
