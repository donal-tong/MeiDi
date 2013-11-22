package bean;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.AppException;
import tools.Logger;

public class SKWeather extends bean.Entity {
	public String city;
	public String cityid;
	public String temp;
	public String WD;
	public String WS;
	public String SD;
	public String WSE;
	public String time;
	public String isRadar;
	public String Radar;
	
	@Override
	public String toString() {
		return "SKWeather [city=" + city + ", cityid=" + cityid + ", temp="
				+ temp + ", WD=" + WD + ", WS=" + WS + ", SD=" + SD + ", WSE="
				+ WSE + ", time=" + time + ", isRadar=" + isRadar + ", Radar="
				+ Radar + "]";
	}

	public static SKWeather pase(String res) throws IOException, AppException {
		SKWeather weather = new SKWeather();
		try {
			JSONObject js = new JSONObject(res);
			JSONObject object = js.getJSONObject("weatherinfo");
			weather.city = object.getString("city");
			weather.cityid = object.getString("cityid");
			weather.temp = object.getString("temp");
			weather.WD = object.getString("WD");
			weather.WS = object.getString("WS");
			weather.SD = object.getString("SD");
			weather.WSE = object.getString("WSE");
			weather.time = object.getString("time");
			weather.isRadar = object.getString("isRadar");
			weather.Radar = object.getString("Radar");
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return weather;
	}
}
