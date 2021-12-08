package com.example.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import com.example.books.Adapters.BookMarkAdapter;
import com.example.books.RoomDatabaseClasses.AppDatabase;
import com.example.books.RoomDatabaseClasses.BookMarks;
import com.example.books.RoomDatabaseClasses.DAO;

import java.util.List;

public class BookMark extends AppCompatActivity {

    RecyclerView recyclerView;
    BookMarkAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        getData();

    }

    private void getData() {

        AppDatabase  database = Room.databaseBuilder(getApplicationContext()
                , AppDatabase.class, "BookMark_Pages").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        DAO dao = database.markDao();
        recyclerView = findViewById(R.id.bookmark_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        List<BookMarks> list = dao.getallData();

        adapter = new BookMarkAdapter(list);
        recyclerView.setAdapter(adapter);

    }
}