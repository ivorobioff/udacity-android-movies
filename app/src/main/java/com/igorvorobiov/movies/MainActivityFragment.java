package com.igorvorobiov.movies;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ImageAdapter imageAdapter = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        GridView grid = (GridView) root.findViewById(R.id.all_movies_grid);

        grid.setAdapter(getImageAdapter());

        return root;
    }

    public void onStart(){
        super.onStart();

        new FetchMoviesTask().execute();
    }

    private ImageAdapter getImageAdapter(){
        if (imageAdapter == null){
            imageAdapter = new ImageAdapter(getActivity());
        }

        return imageAdapter;
    }


    private class FetchMoviesTask extends AsyncTask<Void, Void,  String[]>{

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
        private final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";
        private final String API_KEY = "fe0b7211cd3692d836285c1277af7732";
        private final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";

        protected void onPostExecute(String[] urls) {
            getImageAdapter().refresh(urls);
        }

        @Override
        protected String[] doInBackground(Void ...params) {
            String json = fetchJSON();

            if (json == null){
                return new String[0];
            }

            try {
                return extractURLs(json);
            } catch (JSONException e) {

            }

            return new String[0];
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

        private String[] extractURLs(String json) throws JSONException {

            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray("results");

            String[] urls = new String[results.length()];

            for (int i = 0; i < results.length(); i ++){
                JSONObject item = results.getJSONObject(i);
                urls[i] = IMAGE_BASE_URL + "/w342" + item.getString("poster_path");
            }

            return urls;
        }

        private String buildUrl()
        {
            return Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("api_key", API_KEY)
                    .toString();
        }
    }
}
