package com.example.books.PageSliderWork;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.widget.Toolbar;

import com.example.books.Adapters.BooksAdapter;
import com.example.books.Adapters.PageSelectionAdapter;
import com.example.books.Adapters.PagesAdapter;
import com.example.books.BookPages;
import com.example.books.R;
import com.example.books.VeriablesClasses.RetrivePagesData;

import java.util.ArrayList;

public class PagesViewer extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewPager2 viewPager2;
    String bookname, currentpagenumber;
    ArrayList<RetrivePagesData> list;
    Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages_viewer);

        recyclerView = findViewById(R.id.horizontal_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        toolbar = findViewById(R.id.viewpagertoolbar);
        setSupportActionBar(toolbar);
        bookname = getIntent().getStringExtra("bookname");
        currentpagenumber = getIntent().getStringExtra("currentpagenumber");
        toolbar.setTitle(bookname);
        viewPager2 = findViewById(R.id.viewpager);
        list = getIntent().getParcelableArrayListExtra("BookPages");
        boolean check = getIntent().getBooleanExtra("checkactivity",false);
        if (check == true){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager2.setCurrentItem(Integer.parseInt(currentpagenumber), false);
                }
            }, 0);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager2.setCurrentItem(Integer.parseInt(currentpagenumber) - 1, false);
                }
            }, 0);
        }
        viewPager2.setAdapter(new PagerViewerAdapter(list, viewPager2, this, bookname));

        PageSelectionAdapter adapter = new PageSelectionAdapter(list,this, viewPager2);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = getSharedPreferences("RecentPage", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String itemposition = String.valueOf(viewPager2.getCurrentItem() + 1);
        editor.putString(bookname + "recentpage", itemposition);
        editor.apply();
        Log.e("Current Position:", String.valueOf(itemposition));
    }
}