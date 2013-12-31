package moji;

import bean.Entity;

public class AirIdxEntity extends Entity {
public String cityid;
public String cityName; 
public String title;
public String cityaveragename;
public String lv;//="178" 
public String pmtwo;
public String pmten;
public String pmtwoaqi;
public String pmtenaqi;
public String so2;
public String co;
public String no2; 
public String o3;
public String aqigrade;
public String content;
public String ptime;
public String sd;
public String ed;
public String st;
public String et;
@Override
public String toString() {
	return "AirIdxEntity [cityid=" + cityid + ", cityName=" + cityName
			+ ", title=" + title + ", cityaveragename=" + cityaveragename
			+ ", lv=" + lv + ", pmtwo=" + pmtwo + ", pmten=" + pmten
			+ ", pmtwoaqi=" + pmtwoaqi + ", pmtenaqi=" + pmtenaqi + ", so2="
			+ so2 + ", co=" + co + ", no2=" + no2 + ", o3=" + o3
			+ ", aqigrade=" + aqigrade + ", content=" + content + ", ptime="
			+ ptime + ", sd=" + sd + ", ed=" + ed + ", st=" + st + ", et=" + et
			+ "]";
}

}
