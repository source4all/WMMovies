package com.smupparaju.moviexplore.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.smupparaju.moviexplore.BuildConfig;
import com.smupparaju.moviexplore.R;
import com.smupparaju.moviexplore.databinding.MediaItemBinding;
import com.smupparaju.moviexplore.model.Movie;
import com.smupparaju.moviexplore.ui.MediaClickCallback;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Objects;

/**
 * Created by Siva Mupparaju on 2019-09-08.
 */

public class MediaListAdapter extends RecyclerView.Adapter<MediaListAdapter.MediaViewHolder> {

    private List<? extends Movie> mMediaList;

    @Nullable
    private final MediaClickCallback mMediaClickCallback;

    public MediaListAdapter(@Nullable MediaClickCallback clickCallback) {
        mMediaClickCallback = clickCallback;
    }

    public void setMediaList(final List<? extends Movie> mediaList) {
        if (mMediaList == null) {
            mMediaList = mediaList;
            notifyItemRangeInserted(0, mediaList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mMediaList.size();
                }

                @Override
                public int getNewListSize() {
                    return mediaList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mMediaList.get(oldItemPosition).getTmdbid() ==
                           mediaList.get(newItemPosition).getTmdbid();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Movie newProduct = mediaList.get(newItemPosition);
                    Movie oldProduct = mMediaList.get(oldItemPosition);
                    return newProduct.getTmdbid() == oldProduct.getTmdbid()
                           && Objects.equals(newProduct.getOverview(), oldProduct.getOverview())
                           && Objects.equals(newProduct.getTitle(), oldProduct.getTitle())
                           && newProduct.getReleaseDate() == oldProduct.getReleaseDate();
                }
            });
            mMediaList = mediaList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MediaItemBinding binding = DataBindingUtil
                                             .inflate(LayoutInflater.from(parent.getContext()), R.layout.media_item,
                                                      parent, false);
        binding.setCallback(mMediaClickCallback);
        return new MediaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        Movie media  = mMediaList.get(position);


        if (media.getPosterPath() != null && !media.getPosterPath().isEmpty()) {
            Picasso.get().load(BuildConfig.TMDB_POSTER_URL + media.getPosterPath()).into(holder.binding.mediaImage);
        }

        holder.binding.setMovie(mMediaList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mMediaList == null ? 0 : mMediaList.size();
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {

        final MediaItemBinding binding;

        public MediaViewHolder(MediaItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}