package com.example.shaimaamasarwi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText etMail, etPassword, etName;
    private Button btnRegister, btCancel ;
    private TextView tvWelcome;
    SharedPreferences preferences;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.editTextTextPersonName2);
        etMail = findViewById(R.id.editTextTextEmailAddress2);
        etPassword = findViewById(R.id.editTextTextPassword2);
        btCancel = findViewById(R.id.btnRegister);
        btCancel = findViewById(R.id.btCancel);
        preferences = getSharedPreferences("Userinfo", 0);
    }

    public void cancel(View view) {
        Intent intent_main = new Intent(this, MainActivity.class);
        startActivity(intent_main);
    }

    public void register(View view) {
        String input_mail = etMail.getText().toString();
        String input_pass = etPassword.getText().toString();
        String input_name = etName.getText().toString();
        if(input_mail.length()>0){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Email", input_mail);
            editor.putString("password", input_pass);
            editor.putString("Name", input_name);

            editor.apply();
            Toast.makeText(this, "User registered", Toast.LENGTH_LONG).show();
            Intent intent_main =new Intent(this, MainActivity2home.class);
            startActivity(intent_main);//
        }
        else{
            Toast.makeText(this, "Empty values, please insert!", Toast.LENGTH_SHORT).show();
        }
    }
}