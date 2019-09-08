package com.smupparaju.moviexplore.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.smupparaju.moviexplore.RemoteAPI.Response.RemoteApiCalls;
import com.smupparaju.moviexplore.model.Movie;
import com.smupparaju.moviexplore.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 *
 * Repository handling the work with movies / tv shows from TMDB.
 */

public class MediaRepository {

    private static MediaRepository sInstance;


    private final AppDatabase mDatabase;
    private MediatorLiveData<List<Movie>> mObservableMedia;

    private MediaRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableMedia = new MediatorLiveData<>();

        mObservableMedia.addSource(mDatabase.movieDao().loadAllMedia(),
                mediaEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableMedia.postValue(mediaEntities);
                    }
                });
    }

    public static MediaRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (MediaRepository.class) {
                if (sInstance == null) {
                    sInstance = new MediaRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Movie>> getAllFMovies()
    {
        return mDatabase.movieDao().loadAllFavMedia();
    }

    public Movie getMovie(int id)
    {
        return mDatabase.movieDao().fetchMedia(id);

    }

    public int isFavMovie(int id) {
        return mDatabase.movieDao().isFavMovie(id);

    }

    public void AddFMovie(Movie movie)
    {
        mDatabase.movieDao().insertFMedia(movie);
    }



    /**
     * Get the list of movies / shows from the database and get notified when the data changes.
     */
    public LiveData<List<Movie>> getMedias() {
        return mObservableMedia;
    }

    public LiveData<List<Movie>> searchMedias(String search) {
        fetchMediasList(search, 0);
        return mDatabase.movieDao().loadMediaFromSearch(search);
    }

    public LiveData<Movie> loadMedia(final int movieId) {
        fetchMedia(movieId);
        LiveData<Movie> movie = mDatabase.movieDao().loadMedia(movieId);
        return movie;
    }

    private void fetchMediasList(String mySearch, int type) {
        Call<MovieResponse> mCall;
        switch (type) {
            case 0:
                mCall = RemoteApiCalls.searchMediaListFromTitle(mySearch, "en-us", 1 );
                break;
            case 1:
                mCall = RemoteApiCalls.getPopularMovies(1 );
                break;

            default:
                mCall = RemoteApiCalls.searchMediaListFromTitle(mySearch, "en-us", 1 );


        }
        mCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(final Call<MovieResponse> call,
                                   final Response<MovieResponse> response) {
                final List<Movie> mediaListResult = response.body().getMovieResult();
                if (mediaListResult != null && response.isSuccessful()) {
                    Runnable r = new Runnable() {
                        @Override
                        public void run()
                        {
                            mDatabase.movieDao().insertAll(mediaListResult);
                        }
                    };

                    new Thread(r).start();
                } else {
                    // Todo :
                }
            }

            @Override
            public void onFailure(final Call<MovieResponse> call, final Throwable t) {
                if (!call.isCanceled()) {
                    // Todo :
                }
            }
        });
    }

    private void fetchMedia(int mediaId) {
        Call<Movie> mCall;
        mCall = RemoteApiCalls.getMediaFromId(mediaId);
        mCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(final Call<Movie> call,
                                   final Response<Movie> response) {
                final Movie mediaResult = response.body();
                int i = getMovie(mediaId).getIsfav();
                if (1 == i) { mediaResult.setIsfav(i); }
                if (response.isSuccessful() && mediaResult != null) {
                    Runnable r = new Runnable() {
                        @Override
                        public void run()
                        {
                            mDatabase.movieDao().insertMedia(mediaResult);
                        }
                    };

                    new Thread(r).start();
                } else {
                    // Todo :
                }
            }

            @Override
            public void onFailure(final Call<Movie> call, final Throwable t) {
                if (!call.isCanceled()) {
                    // Todo :
                }
            }
        });
    }


    public LiveData<List<Movie>> getPopularMovies() {
        fetchMediasList("abc", 1);
        return mDatabase.movieDao().loadAllMedia();

    }
}