package com.smupparaju.moviexplore.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.smupparaju.moviexplore.model.Movie;

import java.util.List;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> loadAllMedia();

    @Query("SELECT * FROM movie WHERE isfav = 1")
    LiveData<List<Movie>> loadAllFavMedia();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Movie> mediaList);

    @Query("select * from movie where tmdbid = :movieId")
    LiveData<Movie> loadMedia(int movieId);

    @Query("select * from movie where tmdbid = :movieId")
    Movie fetchMedia(int movieId);


    @Update(onConflict = OnConflictStrategy.IGNORE)
    void insertMedia(Movie media);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void insertFMedia(Movie media);

    @Query("select * from movie where title like '%' || :search || '%'")
    LiveData<List<Movie>> loadMediaFromSearch(String search);

    @Delete
    void deleteMedia(Movie media);

    @Query("select * from movie where tmdbid = :id AND isfav = 1")
    int isFavMovie(int id);
}
