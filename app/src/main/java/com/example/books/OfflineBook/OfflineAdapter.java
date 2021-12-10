package com.example.books.OfflineBook;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.books.R;

import java.io.File;
import java.util.ArrayList;

public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.MyHolder>{

    ArrayList<String> dirlist;
    Context myContext;

    public OfflineAdapter(ArrayList<String> dirlist, Context myContext) {
        this.dirlist = dirlist;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offline_dirlist_cardview,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String dirname = dirlist.get(position).substring(39);
        holder.textView.setText(dirname);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myContext, OfflineBookPages.class);
                intent.putExtra("folder_name", dirname);
                myContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dirlist.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ConstraintLayout layout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.dir_name);
            layout = itemView.findViewById(R.id.dir_layout_click);
        }
    }
}
