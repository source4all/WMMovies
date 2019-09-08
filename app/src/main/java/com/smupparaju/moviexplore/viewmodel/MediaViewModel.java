package com.smupparaju.moviexplore.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.smupparaju.moviexplore.MovieApplication;
import com.smupparaju.moviexplore.db.MediaRepository;
import com.smupparaju.moviexplore.model.Movie;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 */

public class MediaViewModel extends ViewModel {

    private LiveData<Movie> mObservableMedia;
    public ObservableField<Movie> media = new ObservableField<>();
    private MediaRepository mediaRepository;

    private MediaViewModel(MediaRepository repository, final int mediaId) {
        mObservableMedia = repository.loadMedia(mediaId);
        mediaRepository = repository;
    }

    /**
     * UI to observe LiveData Movie and updates the same on change.
     */
    public LiveData<Movie> getObservableMedia() {
        return mObservableMedia;
    }


    public Movie getMovie(int id) {
        return mediaRepository.getMovie(id);
    }

    public int isFavMovie(int id) {
        return mediaRepository.isFavMovie(id);
    }

    public void addFMovie(Movie movie) {
        mediaRepository.AddFMovie(movie);
        mObservableMedia = mediaRepository.loadMedia(movie.getTmdbid());
    }


    /**
     * A creator is used to inject the Movie ID into the ViewModel
     */
    public static class MediaFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mMediaId;

        private final MediaRepository mRepository;

        public MediaFactory(@NonNull Application application, int mediaId) {
            mApplication = application;
            mMediaId = mediaId;
            mRepository = ((MovieApplication) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new MediaViewModel(mRepository, mMediaId);
        }
    }
}