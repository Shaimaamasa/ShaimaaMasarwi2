package com.example.shaimaamasarwi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2home extends AppCompatActivity {
private Button button4, button3, button9, button8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2home);
button4 = findViewById(R.id.button4);
button3 = findViewById(R.id.button3);
button8 = findViewById(R.id.button8);
button9 = findViewById(R.id.button9);

button4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        openActivity4();
    }

});

button9.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        openActivity9();
    }
});

button3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        openActivity3();
    }
});

button8.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        openActivity8();
    }
});

    }



public void openActivity8(){
    Intent intent = new Intent(this,health.class);
    startActivity(intent);
}





    public void openActivity3(){
            Intent intent = new Intent(this, kids.class);

        startActivity(intent);
    }


   public void openActivity9(){
       Intent intent = new Intent(this,history.class);
       startActivity(intent);
   }

    public void openActivity4(){
        Intent intent = new Intent(this,fiction.class);
        startActivity(intent);
    }
}