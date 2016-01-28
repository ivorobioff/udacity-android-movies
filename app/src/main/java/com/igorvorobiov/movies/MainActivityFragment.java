package com.igorvorobiov.movies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        GridView grid = (GridView) root.findViewById(R.id.all_movies_grid);
        String[] urls = {
                "http://globe-views.com/dcim/dreams/smile/smile-01.jpg",
                "http://globe-views.com/dcim/dreams/smile/smile-01.jpg",
                "http://globe-views.com/dcim/dreams/smile/smile-01.jpg",
                "http://globe-views.com/dcim/dreams/smile/smile-01.jpg",
                "http://globe-views.com/dcim/dreams/smile/smile-01.jpg",
                "http://globe-views.com/dcim/dreams/smile/smile-01.jpg",
        };

        grid.setAdapter(new ImageAdapter(getContext(), urls, R.layout.movie_grid_item));

        return root;
    }
}
