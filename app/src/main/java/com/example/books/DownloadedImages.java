package com.example.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadedImages extends AppCompatActivity {

    int count;
    Bitmap[] thumbnils;
    boolean thumbnilselection;
    String[] arrpath;
    Toolbar toolbar;
    ImageAdapter imageAdapter;
    ArrayList<String> booknamelist;
    ArrayList<String> f = new ArrayList<String>();// list of file paths
    File[] listFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloaded_images);

        toolbar = findViewById(R.id.imagestoolbar);
        setSupportActionBar(toolbar);
        booknamelist = new ArrayList<>();
        booknamelist = getIntent().getStringArrayListExtra("booknamelist");
        toolbar.setTitle("Offline Pages");
        getFromSdcard();
        GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
        imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);



   }
    private void getFromSdcard() {
        File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        for (int x = 0; x<booknamelist.size() ; x++){
            File dir = new File(storage.getAbsolutePath()+ "/PDF_Books/"+ booknamelist.get(x));
            if (dir.isDirectory()){
                listFile = dir.listFiles();
            }else{
                break;
            }

            for (int i = 0 ; i < listFile.length; i++){
                f.add(listFile[i].getAbsolutePath());
            }

        }
    }


    private class ImageAdapter extends BaseAdapter {
        LayoutInflater mInflater;
        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return f.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = mInflater.inflate(
                        R.layout.galleryitem, null);
                holder.imageview = (ImageView) view.findViewById(R.id.thumbImage);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            Bitmap myBitmap = BitmapFactory.decodeFile(f.get(i));
            holder.imageview.setImageBitmap(myBitmap);
            holder.imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DownloadedImages.this,FullPage.class);
                    intent.putExtra("Current_Image", myBitmap);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    class ViewHolder {
        ImageView imageview;
    }

}