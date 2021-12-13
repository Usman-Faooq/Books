package com.example.books;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.books.Adapters.BooksAdapter;
import com.example.books.BookMark.BookMarkPagees;
import com.example.books.OfflineBook.OfflineBooks;
import com.example.books.VeriablesClasses.RetriveBookData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    BooksAdapter booksAdapter;
    ImageView lateastimg;
    TextView booknameview, rel_date, views;
    FirebaseFirestore firestore;
    ArrayList<String> booknamelist;
    String bookname, coverurl, releasedate;
    int id, bookviews;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firestore = FirebaseFirestore.getInstance();
        lateastimg = findViewById(R.id.lateastbook);
        booknameview = findViewById(R.id.booktitle);
        views = findViewById(R.id.bookviews);
        rel_date = findViewById(R.id.rel_date);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.download_books:
                        //Intent intent = new Intent(MainActivity.this, DownloadedImages.class);
                        //intent.putStringArrayListExtra("booknamelist", booknamelist);
                        //startActivity(intent);
                        ArrayList<String> filelist = new ArrayList<>();
                        File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File dir = new File(storage.getAbsolutePath()+"/PDF_Books");
                        File[] listdir = dir.listFiles();
                        filelist.clear();
                        for (File file : listdir){
                            filelist.add(file.getPath());
                        }
                        Intent intent = new Intent(MainActivity.this, OfflineBooks.class);
                        intent.putStringArrayListExtra("Dir_List", filelist);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.bookmarks:
                        Intent i = new Intent(MainActivity.this, BookMarkPagees.class);
                        startActivity(i);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.aboutus:
                        Toast.makeText(MainActivity.this, "About Clicked", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });


        //Latest Book
        Query latestbook = firestore.collection("books").orderBy("id", Query.Direction.DESCENDING).limit(1);
        latestbook.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot snapshot : task.getResult()){
                        id = snapshot.getLong("id").intValue();
                        bookname = snapshot.getString("book_name");
                        coverurl = snapshot.getString("book_cover");
                        releasedate = snapshot.getString("release_date");
                        bookviews = snapshot.getLong("book_views").intValue();
                    }
                    Glide.with(lateastimg.getContext()).load(coverurl).placeholder(R.drawable.pdficon).into(lateastimg);
                    booknameview.setText(bookname);
                    views.setText("Views: " + bookviews);
                    rel_date.setText(releasedate);
                }
            }
        });

        lateastimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firestore.collection("books").document(String.valueOf(id)).update("book_views",++bookviews);

                Intent i = new Intent(MainActivity.this, BookPages.class);
                String currentid = String.valueOf(id);
                String v = String.valueOf(bookviews);
                i.putExtra("getbookname", bookname);
                i.putExtra("bookcover", coverurl);
                i.putExtra("currentid", currentid);
                i.putExtra("VIEWS", v);
                i.putExtra("releasedate", releasedate);
                startActivity(i);
            }
        });

        //all book detail...
        Query query =firestore.collection("books");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<RetriveBookData> list = new ArrayList<>();
                    booknamelist = new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult()){
                        String getbooknames = snapshot.getString("book_name");
                        booknamelist.add(getbooknames);
                        int bookid = snapshot.getLong("id").intValue();
                        if (bookid == id){
                            break;
                        }else{
                            RetriveBookData obj = snapshot.toObject(RetriveBookData.class);
                            list.add(obj);
                        }
                    }

                    booksAdapter = new BooksAdapter(list, MainActivity.this);
                    recyclerView.setAdapter(booksAdapter);
                }
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.toolbarbookmark:
                Intent intent = new Intent(this, BookMarkPagees.class);
                startActivity(intent);
            default:
                return false;
        }
    }
}