package com.example.shaimaamasarwi;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button button,button2 ;
    EditText TextEmailAddress,TextPersonName,TextPassword;
    SharedPreferences preferences;
    // muta5erat m3 no3hen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain2);
        TextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        TextPassword = findViewById(R.id.editTextTextPassword);
        TextPersonName = findViewById(R.id.editTextTextPersonName);
        preferences = getSharedPreferences("Userinfo", 0);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        //create a prefrenses file
        // mode 0 = read and write is only for my application

    }


    public void login(View view) {
        if(TextEmailAddress.getText().toString().equals(""))
            Toast.makeText(this, "Empty Email", Toast.LENGTH_LONG).show();
        else if (TextPassword.getText().toString().equals(""))
            Toast.makeText(this, "Empty Password", Toast.LENGTH_SHORT).show();
        else if (TextPersonName.getText().toString().equals(""))
            Toast.makeText(this, "Empty Name", Toast.LENGTH_LONG).show();
        else {
            String input_mail = TextEmailAddress.getText().toString();
            String input_password = TextPassword.getText().toString();
            String input_name = TextPersonName.getText().toString();

// this line gets the registered email and password in case no user was registered empty string is returned
            String registeredMail= preferences.getString("Email", "");
            String registeredPassword= preferences.getString("password", "");
            String registeredName = preferences.getString("Name", "");

            if(input_mail.equals(registeredMail) && (input_password.equals(registeredPassword)))
            { if(input_name.equals(registeredName)){
                Intent i_mail = new Intent(this, MainActivity2home.class);
                startActivity(i_mail);
            }}
            else
            {
                Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.help_menu :
                Toast.makeText(MainActivity.this, "help clicked", Toast.LENGTH_SHORT).show();
                Intent c = new Intent (this, help.class);
                startActivity(c); // go to another page
                break;
            case R.id.settings_menu :
                Toast.makeText(MainActivity.this, "settings clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent (this, listActivity.class);
                startActivity(i);
                break;
            case R.id.logout_menu:

                Toast.makeText(MainActivity.this, "log out  clicked", Toast.LENGTH_SHORT).show();
                Intent y = new Intent (this, MainActivity.class);
                startActivity(y);
                break;

        }

        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Alert");
        dialog.setMessage("Are you sure you want to exit ?") ;
        dialog.setNegativeButton("No",null);
        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });
        dialog.setIcon(R.drawable.bookicon);
        AlertDialog alertDialog= dialog.create();
        alertDialog.show();   }


    public void register(View view) {
        Intent i_register = new Intent(this, RegisterActivity.class);
        startActivity(i_register);
    }


}