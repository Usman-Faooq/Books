package com.example.books.OfflineBook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.books.OfflineBook.Offline_Page_Slider;
import com.example.books.R;

import java.io.File;
import java.util.ArrayList;

public class OfflinePagesAdapter extends RecyclerView.Adapter<OfflinePagesAdapter.MyHolder>{

    ArrayList<String> filelist;
    Context myContext;
    String dirname;

    public OfflinePagesAdapter(ArrayList<String> filelist, Context myContext, String name) {
        this.filelist = filelist;
        this.myContext = myContext;
        this.dirname = name;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_cardview,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String filename = filelist.get(position).substring(0, 1);
        holder.textView.setText("page "+filename);

        File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dir = new File(storage.getAbsolutePath()+"/PDF_Books/"+dirname);

        Bitmap mBitmap = BitmapFactory.decodeFile(dir.getPath()+"/"+filelist.get(position));
        holder.imageView.setImageBitmap(mBitmap);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(myContext, Offline_Page_Slider.class);
                intent.putStringArrayListExtra("BitmapImages", filelist);
                intent.putExtra("folder", dirname);
                intent.putExtra("selectedpage",filename);
                myContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return filelist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        ConstraintLayout layout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.book_cover_img);
            textView = itemView.findViewById(R.id.txt_book_name);
            layout = itemView.findViewById(R.id.dir_layout_click);
        }
    }
}
