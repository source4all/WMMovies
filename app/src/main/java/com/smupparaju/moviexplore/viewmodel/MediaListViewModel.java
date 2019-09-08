package com.smupparaju.moviexplore.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.smupparaju.moviexplore.db.MediaRepository;
import com.smupparaju.moviexplore.model.Movie;

import java.util.List;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 */

public class MediaListViewModel extends ViewModel {

    private MutableLiveData<String> searchTerm;
    private MediaRepository mMediaRepository;
    private LiveData<List<Movie>> mMediaList;

    private MediaListViewModel() {
        searchTerm = new MutableLiveData<>();
        mMediaList = Transformations.switchMap(searchTerm, input -> mMediaRepository.searchMedias(input));
    }

    private MediaListViewModel(@Nullable MediaRepository mediaRepository) {
        this();
        if (this.mMediaRepository != null) {
            // Technically ViewModel is created per Activity so just instantiate once
            return;
        }
        if (mediaRepository != null) {
            this.mMediaRepository = mediaRepository;
        }
    }

    public LiveData<List<Movie>> loadMediaList(final String search) {
        searchTerm.postValue(search);
        return mMediaList;
    }

    public LiveData<List<Movie>> getAllFavMovies() {
        return mMediaRepository.getAllFMovies();
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return mMediaRepository.getPopularMovies();
    }

    /**
     * Technically MediaRepository can be passed in a public method but this is to show case
     * how to inject dependencies into ViewModels.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private MediaRepository mMediaRepository;

        public Factory(@NonNull MediaRepository mediaRepository) {
            mMediaRepository = mediaRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new MediaListViewModel(mMediaRepository);
        }
    }
}