package adapter;

import java.util.ArrayList;
import java.util.List;

import tools.StringUtils;

import com.google.android.gms.internal.ce;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vikaa.meidi.R;

import config.CommonValue;
import android.content.Context;
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
	private List<String> datas = new ArrayList<String>();
	
	static class CellHolder {
		ImageView avatarImageView;
		TextView titleView;
	}
	
 	public TipAdapter(Context context) {
 		this.context = context;
		this.inflater = LayoutInflater.from(context);
		datas.add("冬天正确的洗澡频率");
		datas.add("冬日洗澡别从“头”开始");
		datas.add("冬至过后，洗澡四“不宜“");
		datas.add("美的“零冷水“——家庭大战的终结者");
		datas.add("沐浴科普：热水器内胆水质污染对人体的危害");
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
			convertView = inflater.inflate(R.layout.tip_cell, null);
			cell.avatarImageView = (ImageView) convertView.findViewById(R.id.tipImageView);
			cell.titleView = (TextView) convertView.findViewById(R.id.nameView);
			convertView.setTag(cell);
		}
		else {
			cell = (CellHolder) convertView.getTag();
		}
		String name = datas.get(position);
		cell.titleView.setText(name);
		cell.avatarImageView.setImageResource(R.drawable.tip_cell_image);
		return convertView;
	}

}
