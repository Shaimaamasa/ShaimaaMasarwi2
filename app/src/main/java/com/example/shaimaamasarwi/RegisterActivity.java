package com.example.shaimaamasarwi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText etMail, etPassword, etName;
    private Button btnRegister, btCancel ;
    private TextView tvWelcome;
    private FirebaseAuth mAuth;
    SharedPreferences preferences;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
mAuth = FirebaseAuth.getInstance(); ////
        etName = findViewById(R.id.editTextTextPersonName2);
        etMail = findViewById(R.id.editTextTextEmailAddress2);
        etPassword = findViewById(R.id.editTextTextPassword2);
        btCancel = findViewById(R.id.btnRegister);
        btCancel = findViewById(R.id.btCancel);
        preferences = getSharedPreferences("Userinfo", 0);
    }

    public void signup(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FireBase", "createUserWithEmail:success"); ///////
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i_mail = new Intent(RegisterActivity.this, MainActivity2home.class);
                            startActivity(i_mail);// from --- to
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("HANEEN", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

            signup(input_mail, input_pass);//////////

        }
        else{
            Toast.makeText(this, "Empty values, please insert!", Toast.LENGTH_SHORT).show();
        }
    }
}