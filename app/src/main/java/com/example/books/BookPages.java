package com.example.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.books.Adapters.PagesAdapter;
import com.example.books.PageSliderWork.PagesViewer;
import com.example.books.Services.SaveDataService;
import com.example.books.VeriablesClasses.RetrivePagesData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BookPages extends AppCompatActivity {

    ImageView latestimage, recentpage;
    TextView lateasttext, releasedate, recentbookpage, bookviews;
    Button downloadtotalbook;
    RecyclerView recyclerView;
    PagesAdapter adapter;
    FirebaseFirestore firestore;
    CardView cardView;
    String bookname;
    String getpage;
    Toolbar toolbar;
    SharedPreferences preferences;
    ArrayList<RetrivePagesData> list;

    String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);

        //permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        toolbar = findViewById(R.id.pagestoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        lateasttext = findViewById(R.id.booktitlepages);
        latestimage = findViewById(R.id.lateastbookpages);
        recentpage = findViewById(R.id.recentbookpage);
        bookviews = findViewById(R.id.pagesbookviews);
        recentbookpage = findViewById(R.id.recentpagenumber);
        releasedate = findViewById(R.id.release_Date);
        downloadtotalbook = findViewById(R.id.downloadtotalbook);
        cardView = findViewById(R.id.page_card_view_simple);
        recyclerView = findViewById(R.id.pagesrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        String currentid = getIntent().getStringExtra("currentid");
        bookname = getIntent().getStringExtra("getbookname");
        String bookcover = getIntent().getStringExtra("bookcover");
        String reldate = getIntent().getStringExtra("releasedate");
        String totalviews = getIntent().getStringExtra("VIEWS");
        Glide.with(latestimage.getContext()).load(bookcover).into(latestimage);
        lateasttext.setText(bookname);
        bookviews.setText("Views: " + totalviews);
        releasedate.setText(reldate);


        list = new ArrayList();
        preferences = getSharedPreferences("RecentPage", MODE_PRIVATE);
        getpage = preferences.getString(bookname+"recentpage", "");

        if (getpage == null){
            recentbookpage.setVisibility(View.INVISIBLE);
        }else{
            recentpage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(BookPages.this, PagesViewer.class);
                    i.putExtra("checkactivity",true);
                    i.putExtra("bookname", bookname);
                    i.putExtra("currentpagenumber", getpage);
                    i.putParcelableArrayListExtra("BookPages", list);
                    startActivity(i);
                }
            });
        }

        //get current book pages by using currentid(Book ID)...
        Query query = firestore.collection("books")
                .document(currentid).collection("pages");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot snapshot : task.getResult()){
                        RetrivePagesData obj = snapshot.toObject(RetrivePagesData.class);
                        list.add(obj);
                        if (obj.getPageNo().equals(getpage)){
                            Glide.with(recentpage.getContext()).load(obj.getPage()).placeholder(R.drawable.pdficon).into(recentpage);
                        }
                    }
                    adapter = new PagesAdapter(list, BookPages.this, bookname);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        downloadtotalbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BookPages.this, SaveDataService.class);
                i.putExtra("BookName", bookname);
                i.putParcelableArrayListExtra("Pages", list);
                startService(i);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){ }
            //showing message
        }
    }
}