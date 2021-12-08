package com.example.books.RoomDatabaseClasses;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {BookMarks.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DAO markDao();
}
