package com.igorvorobiov.movies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_details, container, false);

        PosterModel model = getActivity().getIntent().getParcelableExtra("poster");

        TextView titleView = (TextView) root.findViewById(R.id.movie_title);
        TextView overviewView = (TextView) root.findViewById(R.id.movie_overview);
        ImageView posterImage = (ImageView) root.findViewById(R.id.movie_poster);
        TextView voteAverage = (TextView) root.findViewById(R.id.movie_vote_average);
        TextView releaseDate = (TextView) root.findViewById(R.id.movie_release_date);

        titleView.setText(model.getTitle());
        overviewView.setText(model.getOverview());
        voteAverage.setText(Html.fromHtml(String.format(getString(R.string.movie_rating_label), model.getVoteAverage())));

        String formattedReleaseDate = new SimpleDateFormat("dd/mm/yyyy").format(model.getReleaseDate());

        releaseDate.setText(Html.fromHtml(String.format(getString(R.string.movie_release_date_label), formattedReleaseDate)));

        Picasso.with(getActivity()).load(model.getPosterUrl()).into(posterImage);

        return root;
    }
}
