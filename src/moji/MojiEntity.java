package moji;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import bean.Entity;
import tools.Logger;

import android.util.Xml;

public class MojiEntity extends Entity{
	public CCEntity cc = new CCEntity();
	public List<forecastDayEntity> forecastDays = new ArrayList<forecastDayEntity>();;
	public List<IdxListEntity> idxs = new ArrayList<IdxListEntity>();
	public AirIdxEntity air = new AirIdxEntity();
	
	public static MojiEntity parse (String res) {
		MojiEntity moji = new MojiEntity();
		
		CCEntity cc = new CCEntity();
		
		List<forecastDayEntity> forecastDays = null;
		forecastDayEntity day = null;
		
		List<IdxListEntity> idxs = null;
		IdxListEntity idx = null;
		
		AirIdxEntity air = null;
		
		XmlPullParser xmlParser = Xml.newPullParser();
		try {
			xmlParser.setInput(new ByteArrayInputStream(res.getBytes()), UTF8);
			int evtType=xmlParser.getEventType();
			
			while(evtType!=XmlPullParser.END_DOCUMENT){ 
				String tag = xmlParser.getName(); 
				switch(evtType){ 
		    		case XmlPullParser.START_TAG:
		    			if (tag.equalsIgnoreCase("cc")) {
		    				cc.gdt = xmlParser.getAttributeValue(null, "gdt");
		    				cc.ldt= xmlParser.getAttributeValue(null, "ldt");
		    				cc.upt= xmlParser.getAttributeValue(null, "upt");;
		    				cc.tmp= xmlParser.getAttributeValue(null, "tmp");;
		    				cc.htmp= xmlParser.getAttributeValue(null, "htmp");;
		    				cc.ltmp= xmlParser.getAttributeValue(null, "ltmp");;
		    				cc.wd= xmlParser.getAttributeValue(null, "wd");;
		    				cc.wid= xmlParser.getAttributeValue(null, "wid");;
		    				cc.wl= xmlParser.getAttributeValue(null, "wl");;
		    				cc.wdir= xmlParser.getAttributeValue(null, "wdir");;
		    				cc.uvidx= xmlParser.getAttributeValue(null, "uvidx");;
		    				cc.hum= xmlParser.getAttributeValue(null, "hum");;
						}
		    			else if (tag.equalsIgnoreCase("forecastDay")) { 
		    				forecastDays = new ArrayList<forecastDayEntity>();
		    			}
		    			else if (tag.equalsIgnoreCase("day")) {
		    				day = new forecastDayEntity();
		    				day.lwd = xmlParser.getAttributeValue(null, "lwd");;
		    				day. lwid= xmlParser.getAttributeValue(null, "lwid");;
		    				day.id= xmlParser.getAttributeValue(null, "id");;
		    				day.dow= xmlParser.getAttributeValue(null, "dow");;
		    				day.date= xmlParser.getAttributeValue(null, "date");;
		    				day.kn= xmlParser.getAttributeValue(null, "kn");;
		    				day.wl= xmlParser.getAttributeValue(null, "wl");;
		    				day.wdir= xmlParser.getAttributeValue(null, "wdir");;
		    				day.hwd= xmlParser.getAttributeValue(null, "hwd");;
		    				day.hwid= xmlParser.getAttributeValue(null, "hwid");;
		    				day. ftv= xmlParser.getAttributeValue(null, "flv");; 
		    				day. ltmp= xmlParser.getAttributeValue(null, "ltmp");;
		    				day. htmp= xmlParser.getAttributeValue(null, "htmp");;
		    				day. sr= xmlParser.getAttributeValue(null, "sr");; 
		    				day. ss= xmlParser.getAttributeValue(null, "ss");;
		    				day. tn= xmlParser.getAttributeValue(null, "tn");;
		    				day. newkn= xmlParser.getAttributeValue(null, "newkn");; 
		    				day. hwl= xmlParser.getAttributeValue(null, "hwl");;
		    				day. lwl= xmlParser.getAttributeValue(null, "lwl");;
		    				day. hwdir= xmlParser.getAttributeValue(null, "hwdir");;
		    				day. lwdir= xmlParser.getAttributeValue(null, "lwdir");;
		    			}
		    			else if (tag.equalsIgnoreCase("idxs")) {
		    				idxs = new ArrayList<IdxListEntity>();
		    			}
		    			else if (tag.equalsIgnoreCase("idx")) {
		    				idx = new IdxListEntity();
		    				idx. type = xmlParser.getAttributeValue(null, "type");
		    				idx. nm = xmlParser.getAttributeValue(null, "nm");
		    				idx. lv= xmlParser.getAttributeValue(null, "lv");
		    				idx. desc= xmlParser.getAttributeValue(null, "desc");
		    				idx. recom= xmlParser.getAttributeValue(null, "recom");
		    			}
		    			else if (tag.equalsIgnoreCase("air")) {
		    				air = new AirIdxEntity();
		    				air.cityid = xmlParser.getAttributeValue(null, "cityid");
		    				air. cityName = xmlParser.getAttributeValue(null, "cityName");
		    				air. title = xmlParser.getAttributeValue(null, "title");
		    				air. cityaveragename = xmlParser.getAttributeValue(null, "cityaveragename");
		    				air. lv = xmlParser.getAttributeValue(null, "lv");
		    				air. pmtwo = xmlParser.getAttributeValue(null, "pmtwo");
		    				air. pmten = xmlParser.getAttributeValue(null, "pmten");
		    				air. pmtwoaqi = xmlParser.getAttributeValue(null, "pmtwoaqi");
		    				air. pmtenaqi = xmlParser.getAttributeValue(null, "pmtenaqi");
		    				air. so2 = xmlParser.getAttributeValue(null, "so2");
		    				air. co = xmlParser.getAttributeValue(null, "co");
		    				air. no2 = xmlParser.getAttributeValue(null, "no2");
		    				air. o3 = xmlParser.getAttributeValue(null, "o3");
		    				air. aqigrade = xmlParser.getAttributeValue(null, "aqigrade");
		    				air. content = xmlParser.getAttributeValue(null, "content");
		    				air. ptime = xmlParser.getAttributeValue(null, "ptime");
		    				air. sd = xmlParser.getAttributeValue(null, "sd");
		    				air. ed = xmlParser.getAttributeValue(null, "ed");
		    				air. st = xmlParser.getAttributeValue(null, "st");
		    				air. et = xmlParser.getAttributeValue(null, "et");
		    			}
		    			break;
		    		case XmlPullParser.END_TAG:
		    			if (tag.equalsIgnoreCase("cc") && cc != null) { 
		    				moji.cc = cc;
				       	}
		    			else if (tag.equalsIgnoreCase("day") && day != null) { 
		    				forecastDays.add(day);
		    				day = null;
				       	}
		    			else if (tag.equalsIgnoreCase("forecastDay") && forecastDays != null) {
		    				moji.forecastDays.addAll(forecastDays);
		    			}
		    			else if (tag.equalsIgnoreCase("idx") && idx != null) {
		    				idxs.add(idx);
		    				idx = null;
		    			}
		    			else if (tag.equalsIgnoreCase("idxs") && idxs != null) {
		    				moji.idxs.addAll(idxs);
		    			}
		    			else if (tag.equalsIgnoreCase("air") && air != null) {
		    				moji.air = air;
		    			}
		    			break;
				}
				evtType = xmlParser.next(); 
			}
		} catch (Exception e) {
			Logger.i(e);
		}
		return moji;
	}
}
