package com.smupparaju.moviexplore.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 */

@Entity(tableName = "movie")
public class Movie implements Serializable {


    @PrimaryKey
    @SerializedName("id")
    @NonNull // SQLite need it on PrimaryKey
    private int tmdbid;

    @SerializedName("vote_count") private int voteCount;

    @SerializedName("video") private boolean video;

    @SerializedName("vote_average") private double voteAverage;

    @SerializedName("title") private String title;

    @SerializedName("popularity") private double popularity;

    @SerializedName("poster_path") private String posterPath;

    @SerializedName("original_language") private String originalLanguage;

    @SerializedName("original_title") private String originalTitle;

    @SerializedName("backdrop_path") private String backdropPath;

    @SerializedName("adult") private boolean adult;

    @SerializedName("overview") private String overview;

    @SerializedName("release_date") private String releaseDate;

    @SerializedName("IsFav") private int isfav;


    public int getTmdbid() { return tmdbid; }

    public void setTmdbid(int tmdbid) { this.tmdbid = tmdbid; }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getIsfav() { return isfav; }

    public void setIsfav(int isfav) { this.isfav = isfav; }

}
