package com.example.movie.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.MyApplication;
import com.example.movie.R;
import com.example.movie.activity.PlayMovieActivity;
import com.example.movie.adapter.MovieAdapter;
import com.example.movie.model.Movie;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteFragment extends Fragment {

    private List<Movie> listMovies;
    private MovieAdapter movieAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        RecyclerView rcvFavorite = view.findViewById(R.id.rcv_favorite);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvFavorite.setLayoutManager(gridLayoutManager);
        listMovies = new ArrayList<>();
        movieAdapter = new MovieAdapter(listMovies, getActivity(), new MovieAdapter.IClickItemListener() {
            @Override
            public void onClickItem(Movie movie) {
                onClickItemMovie(movie);
            }

            @Override
            public void onClickFavorite(int id, boolean favorite) {
                onClickFavoriteMovie(id, favorite);
            }
        });
        rcvFavorite.setAdapter(movieAdapter);

        getListMoviesFavorite();

        return view;
    }

    private void getListMoviesFavorite() {
        if (getActivity() == null) {
            return;
        }
        MyApplication.get(getActivity()).getDatabaseReference().orderByChild("favorite").equalTo(true)
                .addChildEventListener(new ChildEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                        Movie movie = dataSnapshot.getValue(Movie.class);
                        if (movie == null || listMovies == null || movieAdapter == null) {
                            return;
                        }
                        listMovies.add(0, movie);
                        movieAdapter.notifyDataSetChanged();
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                        Movie movie = dataSnapshot.getValue(Movie.class);
                        if (movie == null || listMovies == null || listMovies.isEmpty() || movieAdapter == null) {
                            return;
                        }
                        for (Movie movieEntity : listMovies) {
                            if (movie.getId() == movieEntity.getId()) {
                                if (!movie.isFavorite()) {
                                    listMovies.remove(movieEntity);
                                } else {
                                    movieEntity.setImage(movie.getImage());
                                    movieEntity.setTitle(movie.getTitle());
                                    movieEntity.setUrl(movie.getUrl());
                                    movieEntity.setHistory(movie.isHistory());
                                }
                                break;
                            }
                        }
                        movieAdapter.notifyDataSetChanged();
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        Movie movie = dataSnapshot.getValue(Movie.class);
                        if (movie == null || listMovies == null || listMovies.isEmpty() || movieAdapter == null) {
                            return;
                        }
                        for (Movie movieDelete : listMovies) {
                            if (movie.getId() == movieDelete.getId()) {
                                listMovies.remove(movieDelete);
                                break;
                            }
                        }
                        movieAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }



    private void onClickItemMovie(Movie movie) {
        Intent intent = new Intent(getActivity(), PlayMovieActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_movie", movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onClickFavoriteMovie(int id, boolean favorite) {
        if (getActivity() == null) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("favorite", favorite);
        MyApplication.get(getActivity()).getDatabaseReference()
                .child(String.valueOf(id)).updateChildren(map);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (movieAdapter != null) {
            movieAdapter.release();
        }
    }
}
