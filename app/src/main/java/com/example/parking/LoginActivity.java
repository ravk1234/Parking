package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class LoginActivity extends AppCompatActivity {

    static public int currentViewId=-1;
    TextView tv,new_register;
    EditText email,password;
    Button login_button, utt;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setCurrentViewById(R.layout.activity_login);
        email =findViewById(R.id.email);
//        utt = findViewById(R.id.butt);
//
//        utt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, PayPal.class);
//                startActivity(intent);
//            }
//        });
        password = (EditText) findViewById(R.id.password);
        login_button = findViewById(R.id.login_btn);
        new_register = findViewById(R.id.sign_up_text);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();

        new_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String u_email = email.getText().toString();
                String u_password = password.getText().toString();
                if (TextUtils.isEmpty(u_email) || TextUtils.isEmpty(u_password)) {
                    Toast.makeText(LoginActivity.this, "All credentials are required!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(u_email, u_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Failed to Login with these credentials", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });



    }

    /*public void setCurrentViewById(int id){
        setContentView(id);
        currentViewId=id;
    }

    static public int getCurrentViewById(){
        return currentViewId;
    }*/




}
