package adapter;

import java.util.List;

import com.vikaa.meidi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<String> datas ;
	
	static class CellHolder {
		TextView city;
	}
	
 	public CityAdapter(Context context, List<String> data) {
 		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.datas = data;
 	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		CellHolder cell = null;
		if (convertView == null) {
			cell = new CellHolder();
			convertView = inflater.inflate(R.layout.city_cell, null);
			cell.city = (TextView) convertView.findViewById(R.id.city);
			convertView.setTag(cell);
		}
		else {
			cell = (CellHolder) convertView.getTag();
		}
		String city = datas.get(position);
		cell.city.setText(city);
		return convertView;
	}
}
