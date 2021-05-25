package com.example.foodorderviasms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity  extends AppCompatActivity {

    String resMsm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
//        received intent
        Intent intent = getIntent();
        String selectedRadioValueS = intent.getStringExtra("radioGroupSizeSelected");
        String selectedRadioValueT = intent.getStringExtra("radioGroupTortillaSelected");
//        setText Size
        TextView tv_Size = (TextView) findViewById(R.id.TV_SizeDisplay);
        tv_Size.setText(selectedRadioValueS);
        resMsm += "Size: " + tv_Size.getText().toString() + "\n";

//        setText Tortilla
        TextView tv_Tor = findViewById(R.id.TV_TorDisplay);
        tv_Tor.setText(selectedRadioValueT);
        resMsm += "Tortilla: " + tv_Tor.getText().toString() + "\n";

//        setText fillings
        String selectedCheckboxFillings = intent.getStringExtra("fillingsCheckboxSelected");
        TextView tv_fillings = findViewById(R.id.TV_CheckboxFillDisplay);
        if(selectedCheckboxFillings.isEmpty()==false){
            tv_fillings.setText(selectedCheckboxFillings);
            resMsm += "Fillings: " +  tv_fillings.getText().toString() + "\n";
        }
        else tv_fillings.setText("no Filling");

//        setText beverage
        String selectedCheckboxBeverage = intent.getStringExtra("beverageCheckboxSelected");
        TextView tv_beverage = findViewById(R.id.TV_CheckboxBeverageDisplay);
        if(selectedCheckboxBeverage.isEmpty()==false){
            tv_beverage.setText("\n" + selectedCheckboxBeverage);
            resMsm += "Beverage: " + tv_beverage.getText().toString();
        }
        else tv_beverage.setText("\nno Beverage");

        //        Button Map
        Button btnMap = (Button) findViewById(R.id.btn_Map);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://goo.gl/maps/nNVwu6woL5GYSpuN8";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

//        Button SMS
        Button btnSMS = (Button) findViewById(R.id.btn_sendSMS);
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = "0359401273";
                Intent intent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("sms:" + phoneNum));
                intent.putExtra("sms_body", resMsm);
                startActivity(intent);
            }
        });
    }
}
