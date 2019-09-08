package com.smupparaju.moviexplore.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.smupparaju.moviexplore.BuildConfig;
import com.smupparaju.moviexplore.MovieApplication;
import com.smupparaju.moviexplore.R;
import com.smupparaju.moviexplore.databinding.ActivityMediaBinding;
import com.smupparaju.moviexplore.model.Movie;
import com.smupparaju.moviexplore.viewmodel.MediaViewModel;
import com.squareup.picasso.Picasso;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 */

public class MediaActivity extends AppCompatActivity {

    private ActivityMediaBinding mBinding;
    private MediaViewModel mMediaViewModel;

    Movie mMedia, tempMedia;
    FloatingActionButton mFavoriteButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_media);

        mFavoriteButton = findViewById(R.id.fav_button);

        Intent intent = getIntent();
        int mMediaId = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, 999);

        mMediaViewModel = ViewModelProviders.of(this, new MediaViewModel.MediaFactory(new MovieApplication(),mMediaId)).get(MediaViewModel.class);
        observerMediaResult(mMediaViewModel.getObservableMedia());

        favButtonInit(mMediaId);
    }

    private void favButtonInit(int mMediaId) {
        mMedia = mMediaViewModel.getMovie(mMediaId);
        int isFav = mMedia.getIsfav();

        if (isFav != 1) {
            mFavoriteButton.setImageResource(R.drawable.ic_favorite_border);
        } else {
            mFavoriteButton.setImageResource(R.drawable.ic_favorite);
        }

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fav = mMedia.getIsfav();
                if (fav != 1) {
                    mMedia.setIsfav(1);
                    mFavoriteButton.setImageResource(R.drawable.ic_favorite);
                } else {
                    mMedia.setIsfav(0);
                    mFavoriteButton.setImageResource(R.drawable.ic_favorite_border);
                }
                mMediaViewModel.addFMovie(mMedia);

            }
        });

    }

    private void observerMediaResult(LiveData<Movie> mediaObservable) {
        // Observer LiveData
        mediaObservable.observe(this, mMediaObserver);
    }

    private Observer<Movie> mMediaObserver =  new Observer<Movie>() {
        @Override
        public void onChanged(@Nullable Movie media) {
            if (media != null) {
                mBinding.setMovie(media);
                String mPoster = media.getPosterPath();
                if (mPoster != null && !mPoster.isEmpty()) {
                    Picasso.get().load(BuildConfig.TMDB_POSTER_URL + mPoster).into(mBinding.mediaImage);
                }
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
    }

}
