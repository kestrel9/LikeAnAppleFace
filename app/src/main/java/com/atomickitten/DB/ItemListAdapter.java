package com.atomickitten.DB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atomickitten.likeanappleface.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by YANG-_-V on 2016-11-08.
 */

public class ItemListAdapter extends BaseAdapter {
    class ViewHolder{

        TextView tv_name;
        TextView tv_term;
        //ProgressBar pb_leaf;
        RelativeLayout rl_background;
        ProgressBar pb_progress;




    }


    Context context;
    ArrayList<Item> itemArrayList;
    ViewHolder viewHolder;
    final long now;
    Date startDate;
    Date endDate;
    Date today;
    SimpleDateFormat dataFormat;

    public ItemListAdapter(Context context, ArrayList<Item> itemArrayList) {

        this.context = context;
        this.itemArrayList = itemArrayList;
        this.now = System.currentTimeMillis();
        this.startDate = null;
        this.endDate = null;
        this.dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.today = new Date(now);
    }

    @Override
    public int getCount() {
        return this.itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);

            viewHolder = new ViewHolder();
            viewHolder.rl_background = (RelativeLayout)convertView.findViewById(R.id.rl_background);
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tv_term = (TextView)convertView.findViewById(R.id.tv_term);
            viewHolder.pb_progress = (ProgressBar)convertView.findViewById(R.id.pb_progress);
            //viewHolder.pb_leaf = (ProgressBar)convertView.findViewById(R.id.pb_leaf);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 아이템 내 각 위젯에 데이터 반영
        viewHolder.tv_name.setText(itemArrayList.get(position).getProduct_name());

        //String [] registerDate = itemArrayList.get(position).getRegister_date().split(" ");

        //String term = "[" + registerDate[0] + " ~ " + itemArrayList.get(position).getExpire_date() + "]";




        try {
            startDate = dataFormat.parse(itemArrayList.get(position).getRegister_date());
            endDate = dataFormat.parse(itemArrayList.get(position).getExpire_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = endDate.getTime() - startDate.getTime();
        int max = (int) (diff / (24 * 60 * 60 * 1000));

        diff = today.getTime() - startDate.getTime();
        int progress = (int) (diff / (24 * 60 * 60 * 1000));
/*
        if(today.compareTo(endDate) >= 0)
        {

            viewHolder.rl_background.setBackgroundColor(58000000);

        }
*/
        String term = "[" + dataFormat.format(startDate) + " ~ " + dataFormat.format(endDate) + "]";

        viewHolder.tv_term.setText(term);
        viewHolder.pb_progress.setMax(max);
        viewHolder.pb_progress.setProgress(progress);

        return convertView;

    }
}
