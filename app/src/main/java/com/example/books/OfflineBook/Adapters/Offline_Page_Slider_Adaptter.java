package com.example.books.OfflineBook.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.books.R;

import java.io.File;
import java.util.ArrayList;

public class Offline_Page_Slider_Adaptter extends RecyclerView.Adapter<Offline_Page_Slider_Adaptter.SliderHolder> {

    Context myContext;
    ArrayList<String> bitmaplist;
    ViewPager2 viewPager2;
    String folder;

    public Offline_Page_Slider_Adaptter(ArrayList<String> list, ViewPager2 pager2, Context context, String folder) {

        this.bitmaplist = list;
        this.viewPager2 = pager2;
        this.myContext = context;
        this.folder = folder;
    }


    @NonNull
    @Override
    public SliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pagerviewer_cardview,parent,false);
        return new SliderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderHolder holder, int position) {

        String filename = bitmaplist.get(position).substring(0, 1);
        holder.textView.setText(filename);

        File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dir = new File(storage.getAbsolutePath()+"/PDF_Books/"+folder);

        Bitmap mBitmap = BitmapFactory.decodeFile(dir.getPath()+"/"+bitmaplist.get(position));
        holder.imageView.setImageBitmap(mBitmap);

    }

    @Override
    public int getItemCount() {
        return bitmaplist.size();
    }

    class SliderHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public SliderHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pagerimage);
            textView = itemView.findViewById(R.id.pagerimagenumber);
        }
    }

}
