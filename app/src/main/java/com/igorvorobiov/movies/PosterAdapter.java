package com.igorvorobiov.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

public class PosterAdapter extends BaseAdapter {

    private String[] urls = new String[0];
    private Context context;

    PosterAdapter(Context context){
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

        PosterView poster;

        if (convertView != null){
            poster = (PosterView) convertView;
        } else {
            poster = new PosterView(context);
        }

        Picasso.with(context).load((String) getItem(position)).into(poster);

        return poster;
    }

    public void refresh(String[] urls){
        this.urls = urls;
        notifyDataSetChanged();
    }
}
