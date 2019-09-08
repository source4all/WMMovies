package com.smupparaju.moviexplore.RemoteAPI.Response;

import com.smupparaju.moviexplore.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Siva Mupparaju on 2019-09-08.
 */

public class RemoteApiClients {

    private static Retrofit mClient;
/*TODO: Cache images into cacheDir for offline */
    static synchronized Retrofit getApiClient() {

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new CurlLoggingInterceptor()).build();

        if (mClient == null) {
            mClient = new Retrofit.Builder()
                            .baseUrl(BuildConfig.API_TMDB_DOMAIN)
                            .client(httpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        }

        return mClient;
    }
}