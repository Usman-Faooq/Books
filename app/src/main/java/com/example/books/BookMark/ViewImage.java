package com.example.books.BookMark;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.books.R;

public class ViewImage extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        imageView = findViewById(R.id.fullimageView);
        Bitmap bitmap = getIntent().getParcelableExtra("FullImage");
        imageView.setImageBitmap(bitmap);

    }
}