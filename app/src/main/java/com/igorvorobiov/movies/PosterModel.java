package com.igorvorobiov.movies;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Igor Vorobiov<igor.vorobioff@gmail.com>
 */
public class PosterModel implements Parcelable{

    private String title;
    private Date releaseDate;
    private String posterUrl;
    private Double voteAverage;
    private String overview;

    public PosterModel(){

    }

    public PosterModel(Parcel parcel) throws ParseException {
        title = parcel.readString();
        posterUrl = parcel.readString();
        overview = parcel.readString();
        voteAverage = parcel.readDouble();
        setReleaseDate(parcel.readString());
    }

    public static final Creator<PosterModel> CREATOR = new Creator<PosterModel>() {
        @Override
        public PosterModel createFromParcel(Parcel in) {
            try {
                return new PosterModel(in);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public PosterModel[] newArray(int size) {
            return new PosterModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterUrl);
        dest.writeString(overview);
        dest.writeDouble(voteAverage);
        dest.writeString(new SimpleDateFormat("yyyy-mm-dd").format(releaseDate));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setReleaseDate(String releaseDate) throws ParseException {
        setReleaseDate(new SimpleDateFormat("yyyy-mm-dd").parse(releaseDate));
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
