package com.example.books.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.books.BookMark.BookMarkPagees;
import com.example.books.BookMark.ViewImage;
import com.example.books.R;
import com.example.books.RoomDatabaseClasses.BookMarks;

import java.util.List;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.MyViewHolder>{

    List<String> list;
    List<BookMarks> bookMarksList;
    Context context;

    public BookMarkAdapter(List<BookMarks> bookMarksList, Context context) {
        this.bookMarksList = bookMarksList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_rowdesign, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.pagenumber.setText(bookMarksList.get(position).getPageNumber()+ " \n " +bookMarksList.get(position).getBookName());
            Glide.with(holder.page.getContext())
                    .load(bookMarksList.get(position).getBookURL()).into(holder.page);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ViewImage.class);
                    holder.page.buildDrawingCache();
                    Bitmap bitmap = holder.page.getDrawingCache();
                    intent.putExtra("FullImage",bitmap);
                    view.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return  bookMarksList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout layout;
        TextView pagenumber;
        ImageView page;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pagenumber = itemView.findViewById(R.id.bookmarkpagenumber);
            page = itemView.findViewById(R.id.bookmarkpageimage);
            layout = itemView.findViewById(R.id.bookmark_pages_click_cover);
        }
    }
}
