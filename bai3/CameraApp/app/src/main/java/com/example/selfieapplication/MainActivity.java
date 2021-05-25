package com.example.selfieapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    String currentPhotoPath;
    ListView listView;
    ArrayList<File> files;
    private  static  final int REQUEST_IMAGE_CAPTURE=123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Notification notification = new Notification(this);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        listView = (ListView) findViewById(R.id.list);
        files = new ArrayList<>();
        new LoadAsync().execute();
        Intent notificationIntent = new Intent(this,  Notification.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //this pendingIntent will be called by the broadcast receiver
        if (pendingIntent != null &&alarmManager != null){
            Toast.makeText(this.getApplicationContext(),
                    "CamerApp Cancelled", Toast.LENGTH_LONG).show();
           alarmManager.setRepeating(AlarmManager.RTC,System.currentTimeMillis(),86400000,pendingIntent);
        }


      //this intent will be called when taping the notification


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.camera_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.camera:
                dispatchTakePictureIntent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void captureImage(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);



            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap=null;
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    bitmap = BitmapFactory.decodeFile(currentPhotoPath);

                imageView.setImageBitmap(bitmap);
                new LoadAsync().onPostExecute();
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();

        return image;
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private ArrayList<File> getFile(File file){
        ArrayList<File> listFile= new ArrayList<>();
        File[] images= file.listFiles();
        for(File filess:images){
            if (filess.isDirectory() && filess.isHidden()){
                listFile.addAll(getFile(filess));
            }else{
                if(filess.getName().endsWith(".jpg")||
                        filess.getName().endsWith(".png")){
                        listFile.add(filess);
                }
            }
        }
        return  listFile;
    }
    public class LoadAsync extends AsyncTask<File[],Void,Void>{

        public LoadAsync(){

        }

        @Override
        protected Void doInBackground(File[]... files) {
            ArrayList<File> files2 = new ArrayList<>();
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            files2 = getFile(storageDir);
            Data adapter = new Data(MainActivity.this,R.layout.single_item,files2);
            listView.setAdapter(adapter);
            return null;
        }
        private void onPostExecute(){
            display();
        }

    }
    public void display(){
        files.clear();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        files = getFile(storageDir);
        Data adapter = new Data(MainActivity.this,R.layout.single_item,files);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
    public void a(){
        galleryAddPic();
    }

}