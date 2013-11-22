package adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import tools.ImageCacheUtil.OPTIONS;
import tools.ImageUtils;
import tools.Logger;

import bean.SKWeather;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ForecastAdapter extends BaseAdapter implements OPTIONS{
	static class ViewHolder {
		ImageView iconImageView;
		TextView dayTV;
		TextView tempTV;
	}
	
	private Context context;
	private List<SKWeather> data;
	private DisplayImageOptions displayOptions;
	public ForecastAdapter() {
		
	}
	public ForecastAdapter(Context context, List<SKWeather> data) {
		this.context = context;
		this.data = data;
		displayOptions = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.local_market_button_default)
//		.showImageForEmptyUri(R.drawable.local_market_button_default)
//		.showImageOnFail(R.drawable.local_market_button_default)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return data.get(arg0).getId();
	}

	@Override
	public View getView(int position, View contentView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (contentView == null) {
			contentView = LayoutInflater.from(context).inflate(com.vikaa.meidi.R.layout.weather_cell, null);
			viewHolder = new ViewHolder();
			viewHolder.iconImageView = (ImageView) contentView.findViewById(com.vikaa.meidi.R.id.iconImageView);
			viewHolder.dayTV = (TextView) contentView.findViewById(com.vikaa.meidi.R.id.dayTV);
			viewHolder.tempTV = (TextView) contentView.findViewById(com.vikaa.meidi.R.id.tempTV);
			contentView.setTag(viewHolder);
		}
		else 
			viewHolder = (ViewHolder) contentView.getTag();
		SKWeather weather = data.get(position);
		ImageLoader.getInstance().displayImage("http://m.weather.com.cn/img/a"+weather.isRadar+".gif", viewHolder.iconImageView, displayOptions);
		viewHolder.tempTV.setText(weather.temp);
		viewHolder.dayTV.setText(weather.time);
		return contentView;
	}

}
