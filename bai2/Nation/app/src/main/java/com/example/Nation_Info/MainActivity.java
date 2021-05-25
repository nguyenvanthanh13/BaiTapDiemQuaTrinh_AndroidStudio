package com.example.Nation_Info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NationHR nationHR;
    List<String> list;
    Spinner spinner;
    ImageView imgNationOne;
    TextView countryNameDisplay;
    TextView populationDisplay;
    TextView areaDisplay;

    public void init() {
        nationHR = new NationHR();
        list = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        AndroidNetworking.initialize(getApplicationContext());

        AndroidNetworking.get("http://api.geonames.org/countryInfoJSON?&username=thanh")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        response = response.substring(1, response.length() - 1);

                        String[] data = response.split("\\{");

                        for (int i = 1; i < data.length ; i++) {
                            String[] s = data[i].split(",");

                            String name = "";
                            String code = "";
                            String popular = "";
                            String areaInSqKm = "";
                            for (int j = 0; j < s.length; j++) {
                                if (s[j].indexOf("countryName") != -1)
                                    name = s[j];
                                if (s[j].indexOf("countryCode") != -1)
                                    code = s[j];
                                if (s[j].indexOf("population") != -1)
                                    popular = s[j];
                                if (s[j].indexOf("areaInSqKm") != -1)
                                    areaInSqKm = s[j];
                            }


                            Nation nation = new Nation();

                            s = name.split("\"");
                            nation.setCountryName(s[3]);
                            list.add(s[3]);

                            s = code.split("\"");
                            nation.setCountryCode(s[3]);

                            s = popular.split("\"");
                            nation.setPopulation(s[3]);

                            s = areaInSqKm.split("\"");
                            nation.setAreaInSqKm(s[3]);

                            nationHR.add(nation);
                        }

                        makeSpinner();
                        nationHR.display();
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getBaseContext(), anError.toString(), Toast.LENGTH_LONG).show();
                    }
                } );

        EditText srTxtNationOne = (EditText) findViewById(R.id.searchNationOne);
        srTxtNationOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list = nationHR.findNationName(s.toString());
                makeSpinner();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public void makeSpinner() {
        spinner = (Spinner) findViewById(R.id.id_spn);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Nation nation = nationHR.getNation(list.get(position));

                AndroidNetworking.get("http://www.geonames.org/flags/x/"
                        + nation.getCountryCode().toLowerCase() +".gif")
                        .build().getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView i = findViewById(R.id.imgNationOne);
                        i.setImageBitmap(response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, "Error loading flag", Toast.LENGTH_SHORT).show();
                    }
                });

                //Display Nation name
                countryNameDisplay = findViewById(R.id.countryNameDisplay);
                countryNameDisplay.setText(nation.getCountryName());
                //Display Nation Pop
                populationDisplay = findViewById(R.id.populationDisplay);
                populationDisplay.setText(nation.getPopulation());
                //Display nation Area
                areaDisplay = findViewById(R.id.areaDisplay);
                areaDisplay.setText(nation.getAreaInSqKm() + " km²");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(MainActivity.this, "No country selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

}