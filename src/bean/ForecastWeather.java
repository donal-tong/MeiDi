package bean;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.AppException;

public class ForecastWeather extends Entity{
	public String city;//: "番禺",
	public String city_en;//: "panyu",
	public String date_y;//: "2013年11月20日",
	public String date;//: "",
	public String week;//: "星期三",
	public String fchh;//: "11",
	public String cityid;//: "101280102",
	public String temp1;//: "22℃~16℃",
	public String temp2;//: "23℃~17℃",
	public String temp3;//: "24℃~18℃",
	public String temp4;//: "25℃~19℃",
	public String temp5;//: "25℃~14℃",
	public String temp6;//: "21℃~12℃",
	public String tempF1;//: "71.6℉~60.8℉",
	public String tempF2;//: "73.4℉~62.6℉",
	public String tempF3;//: "75.2℉~64.4℉",
	public String tempF4;//: "77℉~66.2℉",
	public String tempF5;//: "77℉~57.2℉",
	public String tempF6;//: "69.8℉~53.6℉",
	public String weather1;//: "阴",
	public String weather2;//: "阴转多云",
	public String weather3;//: "多云",
	public String weather4;//: "多云",
	public String weather5;//: "阵雨",
	public String weather6;//: "多云",
	public String img1;//: "2",
	public String img2;//: "99",
	public String img3;//: "2",
	public String img4;//: "1",
	public String img5;//: "1",
	public String img6;//: "99",
	public String img7;//: "1",
	public String img8;//: "99",
	public String img9;//: "3",
	public String img10;//: "99",
	public String img11;//: "1",
	public String img12;//: "99",
	public String img_single;//: "2",
	public String img_title1;//: "阴",
	public String img_title2;//: "阴",
	public String img_title3;//: "阴",
	public String img_title4;//: "多云",
	public String img_title5;//: "多云",
	public String img_title6;//: "多云",
	public String img_title7;//: "多云",
	public String img_title8;//: "多云",
	public String img_title9;//: "阵雨",
	public String img_title10;//: "阵雨",
	public String img_title11;//: "多云",
	public String img_title12;//: "多云",
	public String img_title_single;//: "阴",
	public String wind1;//: "微风",
	public String wind2;//: "微风",
	public String wind3;//: "微风",
	public String wind4;//: "微风",
	public String wind5;// "微风",
	public String wind6;// "微风",
	public String fx1;// "微风",
	public String fx2;// "微风",
	public String fl1;// "小于3级",
	public String fl2;// "小于3级",
	public String fl3;// "小于3级",
	public String fl4;// "小于3级",
	public String fl5;// "小于3级",
	public String fl6;// "小于3级",
	public String index;// "较舒适",
	public String index_d;// "建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。",
	public String index48;// "较舒适",
	public String index48_d;// "建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。",
	public String index_uv;
	public String index48_uv;
	public String index_xc;
	public String index_tr;
	public String index_co;
	public String st1;
	public String st2;
	public String st3;
	public String st4;
	public String st5;
	public String st6;
	public String index_cl;
	public String index_ls;
	public String index_ag;
	
	
	
	@Override
	public String toString() {
		return "ForecastWeather [city=" + city + ", city_en=" + city_en
				+ ", date_y=" + date_y + ", date=" + date + ", week=" + week
				+ ", fchh=" + fchh + ", cityid=" + cityid + ", temp1=" + temp1
				+ ", temp2=" + temp2 + ", temp3=" + temp3 + ", temp4=" + temp4
				+ ", temp5=" + temp5 + ", temp6=" + temp6 + ", tempF1="
				+ tempF1 + ", tempF2=" + tempF2 + ", tempF3=" + tempF3
				+ ", tempF4=" + tempF4 + ", tempF5=" + tempF5 + ", tempF6="
				+ tempF6 + ", weather1=" + weather1 + ", weather2=" + weather2
				+ ", weather3=" + weather3 + ", weather4=" + weather4
				+ ", weather5=" + weather5 + ", weather6=" + weather6
				+ ", img1=" + img1 + ", img2=" + img2 + ", img3=" + img3
				+ ", img4=" + img4 + ", img5=" + img5 + ", img6=" + img6
				+ ", img7=" + img7 + ", img8=" + img8 + ", img9=" + img9
				+ ", img10=" + img10 + ", img11=" + img11 + ", img12=" + img12
				+ ", img_single=" + img_single + ", img_title1=" + img_title1
				+ ", img_title2=" + img_title2 + ", img_title3=" + img_title3
				+ ", img_title4=" + img_title4 + ", img_title5=" + img_title5
				+ ", img_title6=" + img_title6 + ", img_title7=" + img_title7
				+ ", img_title8=" + img_title8 + ", img_title9=" + img_title9
				+ ", img_title10=" + img_title10 + ", img_title11="
				+ img_title11 + ", img_title12=" + img_title12
				+ ", img_title_single=" + img_title_single + ", wind1=" + wind1
				+ ", wind2=" + wind2 + ", wind3=" + wind3 + ", wind4=" + wind4
				+ ", wind5=" + wind5 + ", wind6=" + wind6 + ", fx1=" + fx1
				+ ", fx2=" + fx2 + ", fl1=" + fl1 + ", fl2=" + fl2 + ", fl3="
				+ fl3 + ", fl4=" + fl4 + ", fl5=" + fl5 + ", fl6=" + fl6
				+ ", index=" + index + ", index_d=" + index_d + ", index48="
				+ index48 + ", index48_d=" + index48_d + ", index_uv="
				+ index_uv + ", index48_uv=" + index48_uv + ", index_xc="
				+ index_xc + ", index_tr=" + index_tr + ", index_co="
				+ index_co + ", st1=" + st1 + ", st2=" + st2 + ", st3=" + st3
				+ ", st4=" + st4 + ", st5=" + st5 + ", st6=" + st6
				+ ", index_cl=" + index_cl + ", index_ls=" + index_ls
				+ ", index_ag=" + index_ag + "]";
	}



	public static ForecastWeather pase(String res) throws IOException, AppException {
		ForecastWeather weather = new ForecastWeather();
		try {
			JSONObject js = new JSONObject(res);
			JSONObject object = js.getJSONObject("weatherinfo");
			weather.city = object.getString("city");
			weather.date_y = object.getString("date_y");
			weather.week = object.getString("week");
			weather.fchh = object.getString("fchh");
			weather.temp1 = object.getString("temp1");
			weather.temp2 = object.getString("temp2");
			weather.temp3 = object.getString("temp3");
			weather.temp4 = object.getString("temp4");
			weather.temp5 = object.getString("temp5");
			weather.temp6 = object.getString("temp6");
			weather.weather1 = object.getString("weather1");
			weather.weather2 = object.getString("weather2");
			weather.weather3 = object.getString("weather3");
			weather.weather4 = object.getString("weather4");
			weather.weather5 = object.getString("weather5");
			weather.weather6 = object.getString("weather6");
			weather.img1 = object.getString("img1");
			weather.img2 = object.getString("img2");
			weather.img3 = object.getString("img3");
			weather.img4 = object.getString("img4");
			weather.img5 = object.getString("img5");
			weather.img6 = object.getString("img6");
			weather.img7 = object.getString("img7");
			weather.img8 = object.getString("img8");
			weather.img9 = object.getString("img9");
			weather.img10 = object.getString("img10");
			weather.img11 = object.getString("img11");
			weather.img12 = object.getString("img12");
			weather.img_title1 = object.getString("img_title1");
			weather.img_title2 = object.getString("img_title2");
			weather.img_title3 = object.getString("img_title3");
			weather.img_title4 = object.getString("img_title4");
			weather.img_title5 = object.getString("img_title5");
			weather.img_title6 = object.getString("img_title6");
			weather.img_title7 = object.getString("img_title7");
			weather.img_title8 = object.getString("img_title8");
			weather.img_title9 = object.getString("img_title9");
			weather.img_title10 = object.getString("img_title10");
			weather.img_title11 = object.getString("img_title11");
			weather.img_title12 = object.getString("img_title12");
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return weather;
	}
}
