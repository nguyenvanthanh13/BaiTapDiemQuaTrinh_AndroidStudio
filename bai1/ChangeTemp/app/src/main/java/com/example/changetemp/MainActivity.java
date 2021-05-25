package com.example.changetemp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int inp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        Button btnConvert = (Button) findViewById(R.id.btnConvert);
        EditText input = findViewById(R.id.input);
        TextView output = findViewById(R.id.output);
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb1:
                if (checked) {
                        btnConvert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    double d = Double.valueOf(String.valueOf(input.getText()));
                                    output.setText(String.format("%.1f", (d - 32.0) * 5.0 / 9.0));
                                    history(String.format("F to C: %.1f", (d - 32.0) * 5.0 / 9.0));
                                } catch (NumberFormatException e) {
                                    Toast.makeText(MainActivity.this, "Enter valid number.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
                break;
            case R.id.rb2:
                if (checked) {
                    btnConvert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                double d = Double.valueOf(String.valueOf(input.getText()));
                                output.setText(String.format("%.1f", (d*9.0/5.0)+32.0));
                                history(String.format("C to F: %.1f", (d*9.0/5.0)+32.0));
                            } catch (NumberFormatException e) {
                                Toast.makeText(MainActivity.this, "Enter valid number.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
        }
    }

    public void history(String var) {
        TextView textView = (TextView) findViewById(R.id.TVHistory);
        String s = var+"\n"+textView.getText().toString();
        textView.setText(s);
    }
}