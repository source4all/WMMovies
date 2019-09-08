package com.smupparaju.moviexplore.RemoteAPI.Response;

import com.smupparaju.moviexplore.BuildConfig;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class CurlLoggingInterceptor implements Interceptor {

    private final HttpLoggingInterceptor.Logger logger;

    public CurlLoggingInterceptor() {
        this(HttpLoggingInterceptor.Logger.DEFAULT);
    }

    public CurlLoggingInterceptor(HttpLoggingInterceptor.Logger logger) {
        this.logger = logger;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        HttpUrl url = original.url()
                              .newBuilder()
                              .addQueryParameter("apikey", BuildConfig.API_TMDB_KEY)
                              .build();

        Request.Builder requestBuilder = original.newBuilder().url(url);
        Request request = requestBuilder.build();

        logger.log("--- cURL (" + request.url() + ")");
        logger.log("curl \"" + request.url() + "\"");
        logger.log("╰--- (for testing purpose, paste it in your browser)");

        return chain.proceed(request);
    }

}