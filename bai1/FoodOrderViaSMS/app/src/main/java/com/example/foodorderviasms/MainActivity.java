package com.example.foodorderviasms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPlaceOrder = findViewById(R.id.btn_placeorder);
        RadioGroup RG_Size = (RadioGroup) findViewById(R.id.rbGroupS);
        RadioGroup RG_Tortilla = findViewById(R.id.rbGroupT);
        btnPlaceOrder.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(),SecondActivity.class);
//              Radio Size
            try {
                String selectedRadioValueS = ((RadioButton) findViewById(RG_Size.getCheckedRadioButtonId())).getText().toString();
                intent.putExtra("radioGroupSizeSelected",selectedRadioValueS);
            }catch (NullPointerException msg){
                    Toast.makeText(MainActivity.this,"Select Size",Toast.LENGTH_SHORT).show();
                    return;
            }
//              Radio Tortilla
            try {
                String selectedRadioValueT = ((RadioButton)findViewById(RG_Tortilla.getCheckedRadioButtonId() )).getText().toString();
                intent.putExtra("radioGroupTortillaSelected",selectedRadioValueT);
            }catch (NullPointerException msg){
                Toast.makeText(MainActivity.this,"Select Tortilla",Toast.LENGTH_SHORT).show();
                return;
            }

//              Checkbox Fillings
            String text= "";
            CheckBox cb_beef = findViewById(R.id.cb_beef);
            CheckBox cb_rice = findViewById(R.id.cb_rice);
            CheckBox cb_chicken = findViewById(R.id.cb_chicken);
            CheckBox cb_beans = findViewById(R.id.cb_beans);
            CheckBox cb_whitefish = findViewById(R.id.cb_whiteFish);
            CheckBox cb_pico = findViewById(R.id.cb_pico);
            CheckBox cb_cheese = findViewById(R.id.cb_cheese);
            CheckBox cb_guacamle = findViewById(R.id.cb_Guacamle);
            CheckBox cb_seafood = findViewById(R.id.cb_seaFood);
            CheckBox cb_lbt = findViewById(R.id.cb_lbt);
            CheckBox[] fillings = new CheckBox[]{cb_beef,cb_rice,cb_chicken,cb_beans,cb_whitefish,cb_pico,cb_cheese,cb_guacamle,cb_seafood,cb_lbt};
            for(int i = 0;i<fillings.length;i++){
                if(fillings[i].isChecked()){
                    text += fillings[i].getText()+", ";
                }
            }
            if (text.length() > 0)
            text = text.substring(0,text.length() - 2);
            String fillingsCheckboxSelected = text.toString();
            intent.putExtra("fillingsCheckboxSelected",fillingsCheckboxSelected);

//              Checkbox Beverage
            CheckBox cb_soda = findViewById(R.id.cb_soda);
            CheckBox cb_margarita = findViewById(R.id.cb_margarita);
            CheckBox cb_cerveza = findViewById(R.id.cb_cerveza);
            CheckBox cb_tequila = findViewById(R.id.cb_tequila);
            CheckBox[] beverage = new CheckBox[]{cb_soda,cb_margarita,cb_cerveza,cb_tequila};

            String text2 = "";
            for(int i = 0;i<beverage.length;i++){
                if(beverage[i].isChecked()){
                    text2 += beverage[i].getText()+", ";
                }
            }
            if (text2.length() > 0)
            text2 = text2.substring(0,text2.length() - 2);

            intent.putExtra("beverageCheckboxSelected",text2);
//            Start Secondary class (send)
            startActivity(intent);
        });

    }
}