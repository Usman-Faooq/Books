package com.example.books.BookMark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.books.Adapters.BookMarkAdapter;
import com.example.books.R;
import com.example.books.RoomDatabaseClasses.AppDatabase;
import com.example.books.RoomDatabaseClasses.BookMarks;
import com.example.books.RoomDatabaseClasses.DAO;

import java.util.ArrayList;
import java.util.List;

public class BookMarkActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BookMarkAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        toolbar = findViewById(R.id.bookmarktoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();

    }

    private void getData() {

        AppDatabase  database = Room.databaseBuilder(getApplicationContext()
                , AppDatabase.class, "BookMark_Pages").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        DAO dao = database.markDao();
        recyclerView = findViewById(R.id.bookmark_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        List<String> l = dao.getBookName();
        for (int i = 0; i<l.size(); i++){
            Log.e("RoomDATA: " , l.get(i));
        }

        adapter = new BookMarkAdapter(l, this, false);
        recyclerView.setAdapter(adapter);
    }
}