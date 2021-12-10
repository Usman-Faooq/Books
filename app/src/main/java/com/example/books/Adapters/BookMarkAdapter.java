package com.example.books.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.books.BookMark.BookMarkPagees;
import com.example.books.R;
import com.example.books.RoomDatabaseClasses.BookMarks;

import java.util.List;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.MyViewHolder>{

    List<String> list;
    List<BookMarks> bookMarksList;
    Context context;
    boolean check;

    public BookMarkAdapter(List<BookMarks> bookMarksList, boolean check) {
        this.bookMarksList = bookMarksList;
        this.check = check;
    }

    public BookMarkAdapter(List<String> list, Context context, boolean checkactivity ) {
        this.list = list;
        this.context = context;
        this.check = checkactivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_rowdesign, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (check == true){
            holder.pagenumber.setText(bookMarksList.get(position).getPageNumber()+ " \n " +bookMarksList.get(position).getBookName());
            Glide.with(holder.page.getContext())
                    .load(bookMarksList.get(position).getBookURL()).into(holder.page);
        }else{
            String bookname = list.get(position);
            holder.pagenumber.setText(bookname);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, BookMarkPagees.class);
                    i.putExtra("bookname", bookname);
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (check == true){
            return  bookMarksList.size();
        }else{
            return list.size();
        }
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
