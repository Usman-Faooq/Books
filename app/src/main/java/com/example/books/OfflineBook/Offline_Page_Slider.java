package com.example.books.OfflineBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.widget.Toolbar;

import com.example.books.OfflineBook.Adapters.Offline_Page_Slider_Adaptter;
import com.example.books.R;

import java.util.ArrayList;

public class Offline_Page_Slider extends AppCompatActivity {

    ViewPager2 pager2;
    Toolbar toolbar;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_page_slider);

        pager2 = findViewById(R.id.offlineviewpager);
        toolbar = findViewById(R.id.offlineviewpagertoolbar);
        list = getIntent().getStringArrayListExtra("BitmapImages");
        String foldername = getIntent().getStringExtra("folder");
        String page = getIntent().getStringExtra("selectedpage");
        toolbar.setTitle(page);
        pager2.setAdapter(new Offline_Page_Slider_Adaptter(list, pager2 ,this, foldername));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pager2.setCurrentItem(Integer.parseInt(page) - 1, false);
            }
        }, 0);

    }
}