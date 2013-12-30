package adapter;

import java.util.ArrayList;
import java.util.List;

import tools.Logger;
import tools.StringUtils;
import ui.CreateView;

import bean.TipDisplayListEntity;

import com.google.android.gms.internal.ce;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vikaa.meidi.R;

import config.CommonValue;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TipAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<TipDisplayListEntity> datas ;
	
	static class CellHolder {
		ImageView tip1;
		ImageView tip2;
		ImageView tip3;
		ImageView tip4;
	}
	
 	public TipAdapter(Context context, List<TipDisplayListEntity> data) {
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
		return datas.get(arg0).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		CellHolder cell = null;
		if (convertView == null) {
			cell = new CellHolder();
			convertView = inflater.inflate(R.layout.tip_cell, null);
			cell.tip1 = (ImageView) convertView.findViewById(R.id.tip1);
			cell.tip2 = (ImageView) convertView.findViewById(R.id.tip2);
			cell.tip3 = (ImageView) convertView.findViewById(R.id.tip3);
			cell.tip4 = (ImageView) convertView.findViewById(R.id.tip4);
			convertView.setTag(cell);
		}
		else {
			cell = (CellHolder) convertView.getTag();
		}
		final TipDisplayListEntity model =  datas.get(position);
		cell.tip1.setVisibility(View.GONE);
		cell.tip2.setVisibility(View.GONE);
		cell.tip3.setVisibility(View.GONE);
		cell.tip4.setVisibility(View.GONE);
		for (int i = 0; i < model.tips.size(); i++) {
			if (i == 0) {
				cell.tip1.setVisibility(View.VISIBLE);
				cell.tip1.setBackgroundResource(Integer.valueOf(model.tips.get(0).image));
				cell.tip1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						showCreate(model.tips.get(0).url);
					}
				});
			}
			else if (i == 1) {
				cell.tip2.setVisibility(View.VISIBLE);
				cell.tip2.setBackgroundResource(Integer.valueOf(model.tips.get(1).image));
				cell.tip2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						showCreate(model.tips.get(1).url);
					}
				});
			}
			else if (i == 2) {
				cell.tip3.setVisibility(View.VISIBLE);
				cell.tip3.setBackgroundResource(Integer.valueOf(model.tips.get(2).image));
				cell.tip3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						showCreate(model.tips.get(2).url);
					}
				});
			}
			else if (i == 3) {
				cell.tip4.setVisibility(View.VISIBLE);
				cell.tip4.setBackgroundResource(Integer.valueOf(model.tips.get(3).image));
				cell.tip4.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						showCreate(model.tips.get(3).url);
					}
				});
			}
		}
		return convertView;
	}
	
	private void showCreate(String url) {
		Logger.i(url);
		Intent intent = new Intent(context, CreateView.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}

}
