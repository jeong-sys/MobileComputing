package com.example.project;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

public class TripItemAdapter extends BaseAdapter {
    private Context context;
    private List<TripItem> tripItems;

    public TripItemAdapter(Context context, List<TripItem> tripItems) {
        this.context = context;
        this.tripItems = tripItems;
    }

    @Override
    public int getCount() {
        return tripItems.size();
    }

    @Override
    public Object getItem(int position) {
        return tripItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.imageview_trip_item);
            viewHolder.textViewLocation = convertView.findViewById(R.id.textview_trip_item_location);
            viewHolder.textViewCost = convertView.findViewById(R.id.textview_trip_item_cost);
            viewHolder.textViewNote = convertView.findViewById(R.id.textview_trip_item_note);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TripItem tripItem = tripItems.get(position);

        Glide.with(context).load(Uri.parse(tripItem.getImage())).into(viewHolder.imageView); // 이미지 설정
        viewHolder.textViewLocation.setText(tripItem.getLocation());
        viewHolder.textViewCost.setText(String.valueOf(tripItem.getCost()));
        viewHolder.textViewNote.setText(tripItem.getNote());

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textViewLocation;
        TextView textViewCost;
        TextView textViewNote;
    }
}

