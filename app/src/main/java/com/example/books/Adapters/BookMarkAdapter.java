package com.example.books.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.books.R;
import com.example.books.RoomDatabaseClasses.BookMarks;

import java.util.List;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.MyViewHolder>{

    List<BookMarks> list;

    public BookMarkAdapter(List<BookMarks> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_rowdesign, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.pagenumber.setText(list.get(position).getPageNumber()+ " \n " +list.get(position).getBookName());
        Glide.with(holder.page.getContext())
                .load(list.get(position).getBookURL()).into(holder.page);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView pagenumber;
        ImageView page;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pagenumber = itemView.findViewById(R.id.bookmarkpagenumber);
            page = itemView.findViewById(R.id.bookmarkpageimage);
        }
    }
}
