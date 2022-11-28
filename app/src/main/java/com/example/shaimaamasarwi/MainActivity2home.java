package com.example.shaimaamasarwi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2home extends AppCompatActivity {
EditText TextNumber, TextNumber2;
Button button3,button4;
TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2home);
        TextNumber = findViewById(R.id.editTextNumber);
        TextNumber2 = findViewById(R.id.editTextNumber2) ;
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

    }

    @SuppressLint("SetTextI18n")
    public void plus(View view) {
        if(TextNumber.getText().toString().equals(""))
            Toast.makeText(this, "fill the number", Toast.LENGTH_SHORT).show();
        else if (TextNumber2.getText().toString().equals(""))
            Toast.makeText(this, "fill the number", Toast.LENGTH_SHORT).show();
        else{
            double num1 = Double.parseDouble(TextNumber.getText().toString());
            double num2 =Double.parseDouble(TextNumber2.getText().toString()) ;
            textView2.setText((num1+num2)+"");
        }
    }

    @SuppressLint("SetTextI18n")
    public void minos(View view) {
        if (TextNumber.getText().toString().equals(""))
            Toast.makeText(this, "fill the number", Toast.LENGTH_SHORT).show();
        else if (TextNumber2.getText().toString().equals(""))
            Toast.makeText(this, "fill the number", Toast.LENGTH_SHORT).show();
        else {
            double num1 = Double.parseDouble(TextNumber.getText().toString());
            double num2 = Double.parseDouble(TextNumber2.getText().toString());
            textView2.setText((num1 - num2) + "");
        }
    }}