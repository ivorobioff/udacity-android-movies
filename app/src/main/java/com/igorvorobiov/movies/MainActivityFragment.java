package com.igorvorobiov.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private PosterAdapter posterAdapter = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        GridView grid = (GridView) root.findViewById(R.id.all_movies_grid);

        grid.setAdapter(getPosterAdapter());

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PosterModel model = (PosterModel) getPosterAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("poster", model);

                startActivity(intent);
            }
        });

        return root;
    }

    public void onStart(){
        super.onStart();

        String sorting = PreferenceManager
                .getDefaultSharedPreferences(getActivity())
                .getString("sorting", getString(R.string.default_movies_sorting_value));

        new FetchMoviesTask(sorting).execute();
    }

    private PosterAdapter getPosterAdapter(){
        if (posterAdapter == null){
            posterAdapter = new PosterAdapter(getActivity());
        }

        return posterAdapter;
    }


    private class FetchMoviesTask extends AsyncTask<Void, Void,  PosterModel[]>{

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
        private final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";
        private final String API_KEY = "fe0b7211cd3692d836285c1277af7732";
        private final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";

        private String sorting;

        FetchMoviesTask(String sorting){
            this.sorting = sorting;
        }

        protected void onPostExecute(PosterModel[] models) {
            getPosterAdapter().refresh(models);
        }

        @Override
        protected PosterModel[] doInBackground(Void ...params) {
            String json = fetchJSON();

            if (json == null){
                return new PosterModel[0];
            }

            try {
                return createModels(json);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e){
                e.printStackTrace();
            }

            return new PosterModel[0];
        }

        private String fetchJSON(){
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String json = null;

            try {
                URL url = new URL(buildUrl());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                json = buffer.toString();
            } catch (IOException e){
                Log.v(LOG_TAG, e.getMessage());

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.v(LOG_TAG, e.getMessage());
                    }
                }
            }

            return json;
        }

        private PosterModel[] createModels(String json) throws JSONException, ParseException {

            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray("results");

            PosterModel[] models = new PosterModel[results.length()];

            for (int i = 0; i < results.length(); i ++){
                JSONObject item = results.getJSONObject(i);

                PosterModel model = new PosterModel();
                model.setOverview(item.getString("overview"));
                model.setPosterUrl(IMAGE_BASE_URL + "/w342" + item.getString("poster_path"));
                model.setReleaseDate(item.getString("release_date"));
                model.setTitle(item.getString("title"));
                model.setVoteAverage(item.getDouble("vote_average"));
                models[i] = model;
            }

            return models;
        }

        private String buildUrl()
        {
            return Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("sort_by", sorting + ".desc")
                    .toString();
        }
    }
}
