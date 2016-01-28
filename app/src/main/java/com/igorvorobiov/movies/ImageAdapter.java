package com.igorvorobiov.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {

    private String[] urls;
    private Context context;
    private int imageViewResourceId;

    ImageAdapter(Context context, String[] urls, int imageViewResourceId){
        this.context = context;
        this.urls = urls;
        this.imageViewResourceId = imageViewResourceId;
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

        ImageView imageView;

        if (convertView != null){
            imageView = (ImageView) convertView;
        } else {
            imageView = (ImageView) getInflater().inflate(imageViewResourceId, null);
        }

        Picasso.with(context).load((String)getItem(position)).into(imageView);

        return imageView;
    }

    private LayoutInflater getInflater(){
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
