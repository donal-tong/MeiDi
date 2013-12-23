package bean;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import tools.AppException;
import tools.CalendarUtil;

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
	
	public String day1;
	public String day2;
	public String day3;
	public String day4;
	public String day5;
	public String day6;
	
	public String day1max;
	public String day2max;
	public String day3max;
	public String day4max;
	public String day5max;
	public String day6max;
	
	public String day1min;
	public String day2min;
	public String day3min;
	public String day4min;
	public String day5min;
	public String day6min;
	
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
			weather.index = object.getString("index");
			weather.index_uv = object.getString("index_uv");
			weather.index_co = object.getString("index_co");
			weather.index_xc = object.getString("index_xc");
			weather.index_tr = object.getString("index_tr");
			
			String cRegex         = "(.*)℃~(.*)℃";
			Pattern pattern = Pattern.compile(cRegex);
			Matcher matcher = pattern.matcher(weather.temp1);
		   	if (matcher.find()) {
		   		weather.day1max = matcher.group(1);
		   		weather.day1min = matcher.group(2);
			}
		   	
		   	matcher = pattern.matcher(weather.temp2);
		   	if (matcher.find()) {
		   		weather.day2max = matcher.group(1);
		   		weather.day2min = matcher.group(2);
			}
		   	
		   	matcher = pattern.matcher(weather.temp3);
		   	if (matcher.find()) {
		   		weather.day3max = matcher.group(1);
		   		weather.day3min = matcher.group(2);
			}
		   	
		   	matcher = pattern.matcher(weather.temp4);
		   	if (matcher.find()) {
		   		weather.day4max = matcher.group(1);
		   		weather.day4min = matcher.group(2);
			}
		   	
		   	matcher = pattern.matcher(weather.temp5);
		   	if (matcher.find()) {
		   		weather.day5max = matcher.group(1);
		   		weather.day5min = matcher.group(2);
			}
		   	
		   	matcher = pattern.matcher(weather.temp6);
		   	if (matcher.find()) {
		   		weather.day6max = matcher.group(1);
		   		weather.day6min = matcher.group(2);
			}
		   	
		   	weather.day1 = "今天";
			CalendarUtil day2 = new CalendarUtil(1);
		   	weather.day2 = day2.getWeek(day2.getDay());
			CalendarUtil day3 = new CalendarUtil(2);
		   	weather.day3 = day3.getWeek(day3.getDay());
			CalendarUtil day4 = new CalendarUtil(3);
		   	weather.day4 = (day4.getWeek(day4.getDay()));
			CalendarUtil day5 = new CalendarUtil(4);
		   	weather.day5 = (day5.getWeek(day5.getDay()));
			CalendarUtil day6 = new CalendarUtil(5);
		   	weather.day6 = (day6.getWeek(day6.getDay()));
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return weather;
	}
}
