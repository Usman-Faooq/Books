package com.example.books.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.books.BookPages;
import com.example.books.R;
import com.example.books.VeriablesClasses.RetriveBookData;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder>{

    List<RetriveBookData> list;
    Context mycontext;

    public BooksAdapter(List<RetriveBookData> list, Context mycontext) {
        this.list = list;
        this.mycontext = mycontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_cardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.imageView.getContext())
                .load(list.get(position).getBook_cover()).into(holder.imageView);
        holder.textView.setText(list.get(position).getBook_name());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookid = String.valueOf(list.get(position).getId());
                String bookname = list.get(position).getBook_name();
                String coverurl = list.get(position).getBook_cover();
                String reldate = list.get(position).getRelease_date();
                Intent i = new Intent(mycontext, BookPages.class);
                i.putExtra("getbookname",bookname);
                i.putExtra("bookcover", coverurl);
                i.putExtra("currentid", bookid);
                i.putExtra("releasedate", reldate);
                mycontext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout layout;
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.book_cover_img);
            textView = itemView.findViewById(R.id.txt_book_name);
            layout = itemView.findViewById(R.id.click_cover);
        }
    }
}
