package com.smupparaju.moviexplore.RemoteAPI.Response;

import com.smupparaju.moviexplore.model.Movie;
import com.smupparaju.moviexplore.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 */

public class RemoteApiServices {

    private static MediaService mMediaService;

    static synchronized MediaService getMediaService() {

        if (mMediaService == null) {
            mMediaService = RemoteApiClients
                                .getApiClient()
                                .create(MediaService.class);
        }
        return mMediaService;
    }

    public interface MediaService {

        @GET("search/movie")
        Call<MovieResponse> searchMediaListFromTitle(@Query("api_key") String API_KEY, @Query("language") String LANGUAGE, @Query("page") int PAGE, @Query("query") String QUERY);


        @GET("movie/popular")
        Call<MovieResponse> getPopularMovies(@Query("api_key")String API_KEY, @Query("page")int pageIndex);

        @GET("movie/{movie_id}")
        Call<Movie> getMediaFromId(@Path("movie_id") int MOVIE_ID, @Query("api_key") String API_KEY);

    }
}