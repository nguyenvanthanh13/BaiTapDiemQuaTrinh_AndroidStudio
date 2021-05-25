package com.example.currencyconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView thanh_tien;
    EditText input;
    Spinner loai_tien, tien_doi;
    ArrayList<String> arrayList;
    String[] item;
    String ratio;

    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myparser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<>();
        new getData().execute();
        thanh_tien = findViewById(R.id.textView3);
        input = findViewById(R.id.editTextTextPersonName);
        loai_tien = findViewById(R.id.spinner);
        tien_doi = findViewById(R.id.spinner2);
        item = new String[]{"Loại tiền."};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, item);
        loai_tien.setAdapter(adapter);
        tien_doi.setAdapter(adapter);
        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        try {
            myparser = xmlFactoryObject.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        loai_tien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new getRSS().execute((String)loai_tien.getSelectedItem(),(String)tien_doi.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tien_doi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new getRSS().execute((String)loai_tien.getSelectedItem(),(String)tien_doi.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                new getRSS().execute((String)loai_tien.getSelectedItem(),(String)tien_doi.getSelectedItem());
                return false;
            }
        });
    }

    public void doiTien(String str){
        String tien1 = "", tien2 = "";
        int dem = 0;
        boolean f = true;
        if(!(loai_tien.getSelectedItemId() == tien_doi.getSelectedItemId()) && ratio != null){
            Pattern pattern = Pattern.compile("\\d");
            for(int i = 0; i < ratio.length() && dem < 2; i++){
                Matcher matcher = pattern.matcher(String.valueOf(ratio.charAt(i)));
                if(f)
                    if(matcher.matches()){
                        for(; i < ratio.length() && dem < 2; i++){
                            if(ratio.charAt(i) == ' '){
                                dem ++;
                                break;
                            }
                            else{
                                if(dem == 0){
                                    tien1 += ratio.charAt(i);

                                }
                                if(dem == 1){
                                    tien2 += ratio.charAt(i);

                                }
                            }
                        }
                    }
                if(!f)
                    if(matcher.matches()){
                        for(; i < ratio.length() && dem < 2; i++){
                            if(ratio.charAt(i) == ' '){
                                dem ++;
                                break;
                            }
                            else{
                                if(dem == 0){
                                    tien2 += ratio.charAt(i);

                                }
                                if(dem == 1){
                                    tien1 += ratio.charAt(i);

                                }
                            }
                        }
                    }
                if(tien2.indexOf('E') != -1){
                    f = false;
                    dem = 0;
                    tien1 = "";
                    tien2 = "";
                }
            }
            double t1 = Double.parseDouble(tien1);
            double t2 = Double.parseDouble(tien2);
            double tien, thanh_tien;
            if(input.getText().toString().contentEquals("")) {
                tien = 0;
            }
            else{
                tien = Double.parseDouble(input.getText().toString());
            }
            thanh_tien = tien * (t2 / t1);
            this.thanh_tien.setText(String.valueOf(thanh_tien));
            ratio = null;
        }
    }




    public void setLoaiTien(){
        item = getCurrencyCode();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, item);
        loai_tien.setAdapter(adapter);
        tien_doi.setAdapter(adapter);
    }

    private String[] getCurrencyCode() {
        String[] arr = new String[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++)
            arr[i] = arrayList.get(i);
        return arr;
    }


    public class getData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                URL url = new URL("http://api.geonames.org/countryInfoJSON?formatted=true&username=thanh&style=full");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String temp;

                    while ((temp = reader.readLine()) != null) {
                        stringBuilder.append(temp);
                    }
                    result = stringBuilder.toString();
                }else  {
                    result = "error";
                }

            } catch (Exception  e) {
                e.printStackTrace();
            }
            return result;
        }
        public void onPostExecute(String s) {
            super .onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("geonames");

                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsonObject = array.getJSONObject(i);
                    String currencyCode = jsonObject.getString("currencyCode");
                    if(arrayList.indexOf(currencyCode) == -1 && !currencyCode.contentEquals("")){
                        arrayList.add(currencyCode);
                    }
                }
                setLoaiTien();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class getRSS extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String code1 = strings[0];
            String code2 = strings[1];
            String path1 = "https://" + code1.toLowerCase() + ".fxexchangerate.com/" + code2.toLowerCase() + ".xml";

            try {
                URL url = new URL(path1);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                myparser.setInput(inputStream, null);
                int event = myparser.getEventType();
                String text = null;
                while (event != XmlPullParser.END_DOCUMENT) {
                    String name = myparser.getName();
                    switch (event) {
                        case XmlPullParser.START_TAG:
                            break;

                        case XmlPullParser.TEXT:
                            text = myparser.getText();
                            break;
                        case XmlPullParser.END_TAG:
                            if (name.equals("description")) {
                                result = text;
                            }
                            break;
                    }
                    event = myparser.next();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ratio = s;
            doiTien(ratio);
        }
    }
}