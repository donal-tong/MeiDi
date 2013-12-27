package config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import tools.Logger;

import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import bean.AirIndexEntity;
import bean.Entity;
import bean.ForecastWeather;
import bean.IndexEntity;
import bean.Result;
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
    
    public static void getAirIndex(final MeiDiApp appContext, final String city, final ClientCallback callback) {
    	Logger.i(city);
    	QYRestClient.get("http://www.pm25.in/api/querys/aqi_details.json?city="+city+"&token=5j1znBVAsnSf5xQyNQyq", null, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] content) {
				String s = new String(content);
				Logger.i(s);
				try {
					AirIndexEntity data = new AirIndexEntity();
					if (s.indexOf("error") != -1) {
						data.setError_code(11);
					}
					else {
						JSONArray jsonArray = new JSONArray(s);
						JSONObject avg = jsonArray.getJSONObject(jsonArray.length()-1);
						data.pm2_5 = avg.getString("pm2_5");
						data.quality = avg.getString("quality");
						data.setError_code(Result.RESULT_OK);
						saveCache(appContext, "AirIndex-"+city, data);
					}
					callback.onSuccess(data);
				} catch (Exception e) {
					Logger.i(e);
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				
			}
		});
    }
}
