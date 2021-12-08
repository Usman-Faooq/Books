package com.example.books.VeriablesClasses;

public class RetriveBookData {

    String book_name, book_url, book_cover, release_date;
    int id, book_views;

    public RetriveBookData() {
    }

    public RetriveBookData(String book_name, String book_url, String book_cover, int id, int book_views, String release_date) {
        this.book_name = book_name;
        this.book_url = book_url;
        this.book_cover = book_cover;
        this.id = id;
        this.book_views = book_views;
        this.release_date = release_date;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_url() {
        return book_url;
    }

    public void setBook_url(String book_url) {
        this.book_url = book_url;
    }

    public String getBook_cover() {
        return book_cover;
    }

    public void setBook_cover(String book_cover) {
        this.book_cover = book_cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBook_views() {
        return book_views;
    }

    public void setBook_views(int book_views) {
        this.book_views = book_views;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
