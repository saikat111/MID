package com.codingburg.mid.model;

public class TrendingList {
    private String id;
    private String original_language;
    private String overview;
    private String popularity;
    private String poster_path;
/*    private String release_date;*/
/*    private String title;*/
    private String vote_average;
    private String vote_count;

    public TrendingList(String id, String original_language, String overview, String popularity, String poster_path, /*String release_date,*/ /*String title,*/ String vote_average, String vote_count) {
        this.id = id;
        this.original_language = original_language;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
    /*    this.release_date = release_date;*/
    /*    this.title = title;*/
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

  /*  public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
*/
/*
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
*/

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }
}
