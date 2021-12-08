package com.example.books.PageSliderWork;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.books.R;
import com.example.books.VeriablesClasses.RetrivePagesData;

import java.util.List;

public class PagerViewerAdapter extends RecyclerView.Adapter<PagerViewerAdapter.SliderHolder>{
    List<RetrivePagesData> list;
    ViewPager2 viewPager2;
    Context context;
    String bookname;

    public PagerViewerAdapter(List<RetrivePagesData> list, ViewPager2 viewPager2, Context context, String bookname) {
        this.list = list;
        this.viewPager2 = viewPager2;
        this.context = context;
        this.bookname = bookname;
    }

    @NonNull
    @Override
    public SliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pagerviewer_cardview,parent,false);
        return new SliderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderHolder holder, int position) {
        holder.textView.setText(list.get(position).getPageNo());
        Glide.with(holder.imageView.getContext())
                .load(list.get(position).getPage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
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
