package com.example.books.OfflineBook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.example.books.R;

import java.util.ArrayList;

public class OfflineBooks extends AppCompatActivity {

    ArrayList<String> dirlist;
    RecyclerView recyclerView;
    OfflineAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_books);

        toolbar = findViewById(R.id.offlinetoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dirlist = getIntent().getStringArrayListExtra("Dir_List");
        recyclerView = findViewById(R.id.dirlistrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new OfflineAdapter(dirlist, this);
        recyclerView.setAdapter(adapter);

    }

}

