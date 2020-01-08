package com.example.carassistantforuserfragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.carassistantforuserfragments.entity.Car;

import java.util.List;

public class LstViewAdapter extends ArrayAdapter<Car> {
    private int groupid;
    private List<Car> item_list;
    private Context context;

    public LstViewAdapter(Context context, int vg, int id, List<Car> item_list) {
        super(context, vg, id, item_list);
        this.context = context;
        groupid = vg;
        this.item_list = item_list;

    }

    static class ViewHolder {
        TextView carName;
        TextView placeNumber;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.carName = rowView.findViewById(R.id.carName);
            viewHolder.placeNumber = rowView.findViewById(R.id.carPlateNumber);
            rowView.setTag(viewHolder);

        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.carName.setText(item_list.get(position).getCarName());
        holder.placeNumber.setText(item_list.get(position).getPlateNumber());
        return rowView;
    }

}

