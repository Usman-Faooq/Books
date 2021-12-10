package com.example.books.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.books.R;
import com.example.books.VeriablesClasses.RetrivePagesData;

import java.util.ArrayList;

public class PageSelectionAdapter extends  RecyclerView.Adapter<PageSelectionAdapter.MyViewHolder>{

    ArrayList<RetrivePagesData> list;
    Context mycontext;
    ViewPager2 pager2;

    public PageSelectionAdapter(ArrayList<RetrivePagesData> list, Context mycontext, ViewPager2 pager2) {
        this.list = list;
        this.mycontext = mycontext;
        this.pager2 = pager2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pageselection_cardview,parent,false);
        return new PageSelectionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.imageView.getContext())
                .load(list.get(position).getPage()).into(holder.imageView);
        holder.textView.setText(list.get(position).getPageNo());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int currentitem = Integer.parseInt(list.get(position).getPageNo());
                        pager2.setCurrentItem(currentitem - 1,false);
                    }
                }, 0);
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
            imageView = itemView.findViewById(R.id.selectpageimage);
            textView = itemView.findViewById(R.id.selectpagenumber);
            layout = itemView.findViewById(R.id.pages_click_cover);
        }
    }
}

