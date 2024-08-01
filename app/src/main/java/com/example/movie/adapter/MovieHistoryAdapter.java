package com.example.movie.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.example.movie.model.Movie;

import java.util.List;

public class MovieHistoryAdapter extends RecyclerView.Adapter<MovieHistoryAdapter.MovieViewHolder> {

    private final List<Movie> listMovies;
    private Activity mActivity;
    private final IClickItemListener iClickItemListener;

    public interface IClickItemListener {
        void onClickItem(Movie movie);
        void onClickFavorite(int id, boolean favorite);
    }

    public MovieHistoryAdapter(List<Movie> listMovies, Activity mActivity, IClickItemListener listener) {
        this.listMovies = listMovies;
        this.mActivity = mActivity;
        this.iClickItemListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_history, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = listMovies.get(position);
        if (movie == null) {
            return;
        }
        holder.tvTitleMovie.setText(movie.getTitle());
        if (movie.getImage() != null && !movie.getImage().equals("")) {
            Glide.with(mActivity).load(movie.getImage()).error(R.drawable.ic_no_image).into(holder.imgMovie);
        } else {
            holder.imgMovie.setImageResource(R.drawable.ic_no_image);
        }
        if (movie.isFavorite()) {
            holder.imgFavorite.setImageResource(R.drawable.icon_favorite_big_on);
        } else {
            holder.imgFavorite.setImageResource(R.drawable.icon_favorite_big_off);
        }

        holder.layoutItem.setOnClickListener(view -> iClickItemListener.onClickItem(movie));

        holder.imgFavorite.setOnClickListener(view
                -> iClickItemListener.onClickFavorite(movie.getId(), !movie.isFavorite()));
    }

    public void release() {
        if (mActivity != null) {
            mActivity = null;
        }
    }

    @Override
    public int getItemCount() {
        if (listMovies != null) {
            return listMovies.size();
        }
        return 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgMovie;
        private final ImageView imgFavorite;
        private final TextView tvTitleMovie;
        private final LinearLayout layoutItem;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_movie);
            imgFavorite = itemView.findViewById(R.id.img_favorite);
            tvTitleMovie = itemView.findViewById(R.id.tv_title_movie);
            layoutItem = itemView.findViewById(R.id.layout_item);
        }
    }
}
