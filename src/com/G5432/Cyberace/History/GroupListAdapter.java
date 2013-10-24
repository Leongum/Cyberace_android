package com.G5432.Cyberace.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.G5432.Cyberace.R;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-23
 * Time: 下午5:10
 * To change this template use File | Settings | File Templates.
 */
public class GroupListAdapter extends SimpleAdapter {

    private Context context;

    public GroupListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
    }

    @Override
    public boolean isEnabled(int position) {
        Map<String, Object> currentData = (HashMap<String, Object>) getItem(position);
        Boolean titleTag = (Boolean) currentData.get("titleTag");
        if (titleTag) {
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        View view = convertView;
        Map<String, Object> currentData = (HashMap<String, Object>) getItem(position);
        Boolean titleTag = (Boolean) currentData.get("titleTag");
        if (titleTag) {
            view = LayoutInflater.from(context).inflate(R.layout.historylistgrouptitle, null);
            TextView txtTitle = (TextView) view.findViewById(R.id.historydetailListGroupTitle);
            txtTitle.setText((String)currentData.get("title"));
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.historylistrecord, null);
            TextView txtDistance =(TextView) view.findViewById(R.id.historydetailTxtRecordDistance);
            txtDistance.setText((String)currentData.get("distance"));
            TextView txtTime = (TextView)view.findViewById(R.id.historydetailTxtTime);
            txtTime.setText((String)currentData.get("time"));
        }
        return view;
    }

}
