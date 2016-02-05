package com.igorvorobiov.movies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

public class PosterAdapter extends BaseAdapter {

    private PosterModel[] models = new PosterModel[0];
    private Context context;

    PosterAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.length;
    }

    @Override
    public Object getItem(int position) {
        return models[position];
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

        PosterModel model = (PosterModel) getItem(position);

        Picasso.with(context).load(model.getPosterUrl()).into(poster);

        return poster;
    }

    public void refresh(PosterModel[] models){
        this.models = models;
        notifyDataSetChanged();
    }
}
