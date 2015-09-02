package com.atomEdition.FortuneCookies.row;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.atomEdition.FortuneCookies.R;
import com.atomEdition.FortuneCookies.model.Prophecy;
import com.atomEdition.FortuneCookies.services.CookieUtils;

import java.util.LinkedList;

/**
 * Created by FruityDevil on 29.12.2014.
 */
public class RowHistoryAdapter extends BaseAdapter {

    private static LayoutInflater layoutInflater = null;
    Context context;
    Activity activity;
    LinkedList<Prophecy> data;

    public RowHistoryAdapter(Context context, Activity activity, LinkedList<Prophecy> data){
        this.context = context;
        this.activity = activity;
        this.data = data;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View baseView, ViewGroup viewGroup) {
        View view = baseView;
        if(view == null)
            view = layoutInflater.inflate(R.layout.row_history, null);
        TextView textView = (TextView)view.findViewById(R.id.row_history_prophecy);
        textView.setText(data.get(position).getProphecy());
        textView = (TextView)view.findViewById(R.id.row_history_date);
        textView.setText(data.get(position).getDateToShow());
        ImageView imageView = (ImageView)view.findViewById(R.id.row_history_category);
        imageView.setBackgroundResource(new CookieUtils(context, activity).getCookieImage(data.get(position).getProphecyType()));
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
