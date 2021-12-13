package com.example.books.OfflineBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.widget.Toolbar;

import com.example.books.OfflineBook.Adapters.OfflinePagesAdapter;
import com.example.books.R;

import java.io.File;
import java.util.ArrayList;

public class OfflineBookPages extends AppCompatActivity {

    ArrayList<String> filelist;
    RecyclerView recyclerView;
    OfflinePagesAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_book_pages);

        String dirname = getIntent().getStringExtra("folder_name");
        toolbar = findViewById(R.id.offlinepagestoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(dirname);
        filelist = new ArrayList<>();

        File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dir = new File(storage.getAbsolutePath()+"/PDF_Books/"+dirname);
        String[] listdir = dir.list();

        for (int i = 0; i < listdir.length; i++){
            filelist.add(listdir[i]);
        }
        recyclerView = findViewById(R.id.offlinepagesrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new OfflinePagesAdapter(filelist, this, dirname);
        recyclerView.setAdapter(adapter);

    }
}