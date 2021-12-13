package com.example.books.BookMark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import com.example.books.Adapters.BookMarkAdapter;
import com.example.books.R;
import com.example.books.RoomDatabaseClasses.AppDatabase;
import com.example.books.RoomDatabaseClasses.BookMarks;
import com.example.books.RoomDatabaseClasses.DAO;

import java.util.List;

public class BookMarkPagees extends AppCompatActivity {

    RecyclerView recyclerView;
    BookMarkAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark_pagees);

        toolbar = findViewById(R.id.bookmarkpagestoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String bookname = getIntent().getStringExtra("bookname");

        getData(bookname);

        recyclerView = findViewById(R.id.bookmarkpages_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void getData(String book) {

        AppDatabase database = Room.databaseBuilder(getApplicationContext()
                , AppDatabase.class, "BookMark_Pages").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        DAO dao = database.markDao();
        recyclerView = findViewById(R.id.bookmarkpages_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        List<BookMarks> list = dao.retriveData();

        adapter = new BookMarkAdapter(list, this);
        recyclerView.setAdapter(adapter);



    }
}