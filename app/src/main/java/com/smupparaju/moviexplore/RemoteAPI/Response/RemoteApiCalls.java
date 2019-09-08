package com.smupparaju.moviexplore.RemoteAPI.Response;

import com.smupparaju.moviexplore.BuildConfig;
import com.smupparaju.moviexplore.model.Movie;
import com.smupparaju.moviexplore.model.MovieResponse;

import retrofit2.Call;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 */

public class RemoteApiCalls {


    public static Call<MovieResponse> searchMediaListFromTitle(String query, String lang, int page) {
        return RemoteApiServices.getMediaService().searchMediaListFromTitle(BuildConfig.API_TMDB_KEY, lang, page, query);
    }

    public static Call<MovieResponse> getPopularMovies(int pages) {
        return RemoteApiServices.getMediaService().getPopularMovies(BuildConfig.API_TMDB_KEY, pages);
    }

    public static Call<Movie> getMediaFromId(int tmdbId) {
        return RemoteApiServices.getMediaService().getMediaFromId(tmdbId, BuildConfig.API_TMDB_KEY);
    }
}
