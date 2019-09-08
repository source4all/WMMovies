package com.smupparaju.moviexplore;

import com.smupparaju.moviexplore.db.AppDatabase;
import com.smupparaju.moviexplore.db.MediaRepository;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 */


/**
 * Android MovieApplication class. Used for accessing singletons.
 */
public class MovieApplication extends android.app.Application {

    public static AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppDatabase = AppDatabase.getInstance(this);
    }

    public MovieApplication() {}

    public AppDatabase getDatabase() {
        return mAppDatabase;
    }

    public MediaRepository getRepository() {
        return MediaRepository.getInstance(getDatabase());
    }
}