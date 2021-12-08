package com.example.books;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class FullPage extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_page);

        imageView = findViewById(R.id.pageimage);
        Bitmap bitmap = getIntent().getParcelableExtra("Current_Image");
        imageView.setImageBitmap(bitmap);
    }
}