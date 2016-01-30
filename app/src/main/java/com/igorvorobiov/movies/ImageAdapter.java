package com.igorvorobiov.movies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {

    private String[] urls = new String[0];
    private Context context;

    ImageAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public Object getItem(int position) {
        return urls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ImageView imageView;

        if (convertView != null){
            imageView = (ImageView) convertView;
        } else {
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
        }

        Picasso.with(context).load((String) getItem(position)).into(imageView);

        return imageView;
    }

    public void refresh(String[] urls){
        this.urls = urls;
        notifyDataSetChanged();
    }

    private LayoutInflater getInflater(){
        return LayoutInflater.from(context);
    }
}
