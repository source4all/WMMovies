package com.smupparaju.moviexplore.ui;

import android.app.SearchManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import com.smupparaju.moviexplore.R;
import com.smupparaju.moviexplore.adapters.MediaListAdapter;
import com.smupparaju.moviexplore.databinding.ActivityMainBinding;
import com.smupparaju.moviexplore.db.AppDatabase;
import com.smupparaju.moviexplore.db.MediaRepository;
import com.smupparaju.moviexplore.model.Movie;
import com.smupparaju.moviexplore.viewmodel.MediaListViewModel;
import com.smupparaju.moviexplore.utils.Constants;

import java.util.List;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 */

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = Constants.EXTRA_MESSAGE;
    private MediaListAdapter mMediaListAdapter;
    private ActivityMainBinding mBinding;
    private MediaListViewModel mMediaListViewModel;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);;

        setSupportActionBar(mBinding.toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.mediaList.setLayoutManager(linearLayoutManager);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        AppDatabase mDatabase = AppDatabase.getInstance(this);
        MediaRepository mediaRepository = MediaRepository.getInstance(mDatabase);

        mMediaListViewModel = ViewModelProviders.of(this, new MediaListViewModel.Factory(mediaRepository)).get(MediaListViewModel.class);

        mMediaListAdapter = new MediaListAdapter(mMediaClickCallback);
        mBinding.mediaList.setAdapter(mMediaListAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_search:
                    fetchPopular();
                    // fetchData(mLastSearch);
                    break;
                case R.id.action_favorites:
                    fetchFavs();
                    break;
                default:
                    fetchFavs();
            }
            return true;
        });

        // Load popular movies to screens
        fetchPopular();


    }


    private void observerMediaListResults(LiveData<List<Movie>> mediaListObservable) {
        // Observer LiveData
        mediaListObservable.observe(this, mMediaListObserver);
    }

    private Observer<List<Movie>> mMediaListObserver =  new Observer<List<Movie>>() {
        @Override
        public void onChanged(@Nullable List<Movie> mediaList) {
            checkEmptyResult(mediaList);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu
        getMenuInflater().inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchMenuItem = menu.findItem(R.id.search_movies);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getString(R.string.search_hint));

        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener(){
            @Override
            public boolean onMenuItemActionExpand(MenuItem item){
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        searchView.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) { // it's never null. I've added this line just to make the compiler happy
                            imm.showSoftInput(searchView.findFocus(), 0);
                        }
                    }
                });
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item){
                mBinding.alertMessage.setText(getResources().getString(R.string.default_search_message));
                mBinding.alertMessage.setVisibility(View.VISIBLE);
                mBinding.mediaList.setVisibility(View.INVISIBLE);
                mBinding.mediaList.setAdapter(null);
                // I have added this as to show popular section on menu collapse. Optional
                fetchPopular();
                return true;
            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newSearch) {
                fetchData(newSearch);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newSearch) {
                if (newSearch.length() > 2) {
                    fetchData(newSearch);
                } else {
                    mBinding.alertMessage.setText(getResources().getString(R.string.default_search_message));
                    mBinding.alertMessage.setVisibility(View.VISIBLE);
                    mBinding.mediaList.setVisibility(View.INVISIBLE);
                    mBinding.mediaList.setAdapter(null);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                fetchPopular();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void fetchData(String newSearch) {
        if (newSearch != null) {
            observerMediaListResults(mMediaListViewModel.loadMediaList(newSearch));
        }
    }


    private void fetchFavs() {
        observerMediaListResults(mMediaListViewModel.getAllFavMovies());
    }

    private void fetchPopular() {
        observerMediaListResults(mMediaListViewModel.getPopularMovies());
    }

    private void checkEmptyResult(List<Movie> mediaListResult) {
        if (mediaListResult != null && mediaListResult.size() > 0) {
            mBinding.alertMessage.setVisibility(View.INVISIBLE);
            mBinding.mediaList.setVisibility(View.VISIBLE);
            mMediaListAdapter.setMediaList(mediaListResult);
            mBinding.mediaList.setAdapter(mMediaListAdapter);
        } else {
            mBinding.alertMessage.setText(getResources().getString(R.string.empty_search_message));
            mBinding.alertMessage.setVisibility(View.VISIBLE);
            mBinding.mediaList.setVisibility(View.INVISIBLE);
        }
    }

    private final MediaClickCallback mMediaClickCallback = new MediaClickCallback() {
        @Override
        public void onClick(Movie media) {

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                Intent intent = new Intent(MainActivity.this, MediaActivity.class);
                intent.putExtra(EXTRA_MESSAGE,  media.getTmdbid());

                startActivity(intent);
            }
        }
    };

}
