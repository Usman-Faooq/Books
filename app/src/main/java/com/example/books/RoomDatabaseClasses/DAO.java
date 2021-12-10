package com.example.books.RoomDatabaseClasses;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insertData(BookMarks marks);

    @Query("SELECT EXISTS(SELECT * FROM BookMarks WHERE book_name = :bookname and page_number = :pagenum)")
    Boolean is_exist(String pagenum, String bookname);

    @Query("SELECT * FROM BookMarks where book_name = :bookname ORDER BY uid DESC LIMIT 10")
    List<BookMarks> getallData(String bookname);

    @Query("SELECT DISTINCT book_name from BookMarks")
    List<String> getBookName();

}
