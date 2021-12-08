package com.example.books.RoomDatabaseClasses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BookMarks {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "page_number")
    public String pageNumber;

    @ColumnInfo(name = "book_name")
    public String bookName;

    @ColumnInfo(name = "bookURL")
    public String bookURL;

    public BookMarks() {
    }

    public BookMarks(int uid, String pageNumber, String bookName, String bookURL) {
        this.uid = uid;
        this.pageNumber = pageNumber;
        this.bookName = bookName;
        this.bookURL = bookURL;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookURL() {
        return bookURL;
    }

    public void setBookURL(String bookURL) {
        this.bookURL = bookURL;
    }
}
