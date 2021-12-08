package com.example.books.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.example.books.MainActivity;
import com.example.books.R;
import com.example.books.VeriablesClasses.RetrivePagesData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SaveDataService extends Service {

    ArrayList<RetrivePagesData> list;
    OutputStream outputStream;
    NotificationCompat.Builder builder;
    boolean check;
    public SaveDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        list = intent.getParcelableArrayListExtra("Pages");
        String book = intent.getStringExtra("BookName");
        setNotification(book);
        int i;
        for ( i = 0; i < list.size() ; i++){
            SavePages(list.get(i).getPageNo(), list.get(i).getPage(), book);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!(i< list.size())){
            stopSelf();
        }


        return START_STICKY;
    }

    private void setNotification(String bookname) {
        builder = new NotificationCompat.Builder(getApplicationContext(), "Notification");
        Intent newIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(this,1, new Intent[]{newIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("Downloading");
        builder.setContentText(bookname +" Downloading");
        builder.setOngoing(true);
        builder.setSmallIcon(R.drawable.downloadicon);
        builder.setContentIntent(pendingIntent);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String channelid = "Channel ID";
            NotificationChannel channel = new NotificationChannel(
                    channelid,"Notification Channel", NotificationManager.IMPORTANCE_HIGH);
            managerCompat.createNotificationChannel(channel);
            builder.setChannelId(channelid);
        }
        managerCompat.notify(1,builder.build());

    }

    private void SavePages(String pagenumber, String page , String bookname) {
        Picasso.get().load(page).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File dir = new File(storage.getAbsolutePath()+ "/PDF_Books/"+bookname);
                    if (!dir.exists()){
                        dir.mkdirs();
                    }
                    String filename = String.format(pagenumber +".jpg");
                    File outfile = new File(dir, filename);
                    if (outfile.exists()){
                        Log.e("Already Exist: ", "Page No." + pagenumber);
                    }else{
                        outputStream = new FileOutputStream(outfile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        Log.e("Page Saved ", "PageNo." + pagenumber);
                        outputStream.flush();
                        outputStream.close();
                    }

                }catch (Exception e){

                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.cancelAll();
        Log.e("Ending: ", "Service Destoried");
    }
}