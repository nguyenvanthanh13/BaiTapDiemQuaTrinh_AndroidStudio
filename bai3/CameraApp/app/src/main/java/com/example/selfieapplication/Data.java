package com.example.selfieapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Data extends BaseAdapter {
    Context context;
    int resources;
    ArrayList<File> files;
    LayoutInflater inflater;
    public Data(Context context, int resources, ArrayList<File> files){

        this.context=context;
        this.resources=resources;
        this.files = files;
        inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public Object getItem(int position) {

        return position;
    }
    @Override
    public int getCount() {

        return files.size();
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View dataView=convertView;
        if(dataView == null){
            dataView =  inflater.inflate(R.layout.single_item,parent,false);
        }
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        bmOptions.inSampleSize=4;
        BitmapFactory.decodeFile(files.get(position).getAbsolutePath(), bmOptions);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(files.get(position).getAbsolutePath(), bmOptions);

        ((ImageView)dataView.findViewById(R.id.image1)).setImageBitmap(bitmap);
        ((ImageView)dataView.findViewById(R.id.image1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), Save.class);
                i.putExtra("FilePath",files.get(position).getAbsolutePath());
                context.startActivity(i);
            }
        });
        ((TextView)dataView.findViewById(R.id.txt1)).setText(files.get(position).getName());
        return  dataView;
    }
}
