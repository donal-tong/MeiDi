package bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import tools.Logger;

public class TipListEntity extends Entity{
	public List<TipEntity> tips = new ArrayList<TipEntity>();
	
	public static TipListEntity parse(String res) {
		TipListEntity tipList = new TipListEntity();
		try {
			JSONArray arr = new JSONArray(res);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				TipEntity tip = new TipEntity();
				tip.title = obj.getString("title");
				tip.url = obj.getString("url");
				tip.image = obj.getString("thumb");
				tipList.tips.add(tip);
			}
		}catch (Exception e) {
			Logger.i(e);
		}
		return tipList;
	}
}
