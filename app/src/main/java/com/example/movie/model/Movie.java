package com.example.movie.model;

import java.io.Serializable;

public class Movie implements Serializable {

    private int id;
    private String title;
    private String image;
    private String url;
    private String genre;
    private String country;

    private boolean favorite;
    private boolean history;

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGenre() {return genre;}

    public void setGenre(String genre) {this.genre = genre;}
    public String getCountry() {return country;}

    public void setCountry(String country) {this.country = country;}

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }
}
