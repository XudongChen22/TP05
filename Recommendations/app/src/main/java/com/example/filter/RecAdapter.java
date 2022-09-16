package com.example.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RecAdapter extends ArrayAdapter<Recommendation>
{
    public RecAdapter(Context context, int resource, List<Recommendation> recList)
    {
        super(context,resource,recList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Recommendation recommendation = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rec_cell, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.recName);


        tv.setText(recommendation.getName());


        return convertView;
    }
}
