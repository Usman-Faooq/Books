package com.example.books.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.books.PageSliderWork.PagesViewer;
import com.example.books.RoomDatabaseClasses.AppDatabase;
import com.example.books.RoomDatabaseClasses.BookMarks;
import com.example.books.RoomDatabaseClasses.DAO;
import com.example.books.VeriablesClasses.RetrivePagesData;
import com.example.books.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class PagesAdapter extends RecyclerView.Adapter<PagesAdapter.MyViewHolder> {

    ArrayList<RetrivePagesData> list;
    Context mycontext;
    OutputStream outputStream;
    String bookname;
    public PagesAdapter(ArrayList<RetrivePagesData> list, Context mycontext, String bookname) {
        this.list = list;
        this.mycontext = mycontext;
        this.bookname = bookname;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pages_cardview,parent,false);
        return new PagesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.imageView.getContext())
                .load(list.get(position).getPage()).into(holder.imageView);
        holder.textView.setText(list.get(position).getPageNo());
        String currentpagenmber = list.get(position).getPageNo();
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mycontext, PagesViewer.class);
                intent.putExtra("bookname", bookname);
                intent.putExtra("checkactivity",false);
                intent.putExtra("currentpagenumber", currentpagenmber);
                intent.putParcelableArrayListExtra("BookPages", list);
                mycontext.startActivity(intent);
            }
        });

        //Downloading Single Page from current book
        holder.downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File dir = new File(storage.getAbsolutePath()+ "/PDF_Books/"+bookname);
                if (!dir.exists()){
                    dir.mkdirs();
                }

                BitmapDrawable drawable = (BitmapDrawable) holder.imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                String pagenumber = list.get(position).getPageNo();
                String filename = String.format(pagenumber +".jpg");
                File outfile = new File(dir, filename);
                if (outfile.exists()){
                    Toast.makeText(mycontext, "Already Downloaded...", Toast.LENGTH_SHORT).show();
                }else{

                    try {
                        outputStream = new FileOutputStream(outfile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    Toast.makeText(mycontext, "File Saved", Toast.LENGTH_SHORT).show();
                    try {
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mycontext, "Bookmark Clicked", Toast.LENGTH_SHORT).show();
                new RoomThread( bookname, list.get(position).getPageNo(), list.get(position).getPage()).start();
            }
        });

    }

    class RoomThread extends Thread{
        String bookname, pageNo, page;

        public RoomThread(String bookname, String pageNo, String page) {
            this.bookname = bookname;
            this.pageNo = pageNo;
            this.page = page;
        }
        @Override
        public void run() {
            super.run();
            AppDatabase  database = Room.databaseBuilder(mycontext.getApplicationContext()
                    , AppDatabase.class, "BookMark_Pages").allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
            DAO dao = database.markDao();
            BookMarks bookMarks = new BookMarks();
            Boolean check = dao.is_exist(pageNo, bookname);
            if (check == true){
                Log.e("Check", "Already Exist");
            }else {
                bookMarks.bookURL = page;
                bookMarks.bookName = bookname;
                bookMarks.pageNumber = pageNo;
                dao.insertData(bookMarks);
                Log.e("Check", "Insert Sucessfully");
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout layout;
        ImageView imageView, downloadbtn, bookmark;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pageimage);
            textView = itemView.findViewById(R.id.pagenumber);
            downloadbtn = itemView.findViewById(R.id.downloadbtn);
            bookmark = itemView.findViewById(R.id.bookmarkbtn);
            layout = itemView.findViewById(R.id.pages_click_cover);
        }
    }
}
