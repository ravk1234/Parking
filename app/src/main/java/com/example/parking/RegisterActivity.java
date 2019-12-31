package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

//import static com.example.parking.LoginActivity.currentViewId;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button button_register;
    boolean val = true;
    String email;
    EditText email1, password,ffirstName,flastName;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    TextView login_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //setCurrentViewById(R.layout.activity_register);
        Log.i("regis","line 1");
        reference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        Log.i("regis","line 2");
        email1 =findViewById(R.id.register_email);
        password =findViewById(R.id.register_password);
        ffirstName = findViewById(R.id.register_firstname);
        flastName = findViewById(R.id.register_lastname);

        login_text=findViewById(R.id.register_text3);

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        button_register = (Button)findViewById(R.id.register_btn);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (val) {

                    email = email1.getText().toString();
                    String u_password = password.getText().toString();
                    final String firstName = ffirstName.getText().toString();
                    final String lastName = flastName.getText().toString();
                    if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(u_password)) {
                        Toast.makeText(RegisterActivity.this, "All credentials are required!", Toast.LENGTH_SHORT).show();
                    } else if (u_password.length() < 6) {
                        Toast.makeText(RegisterActivity.this, "Password must contain atleast 6 characters", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registering", Toast.LENGTH_SHORT).show();

                        //register(u_firstName,u_lastName, u_email, u_password);


                        mAuth.createUserWithEmailAndPassword(email,u_password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.i("spark","creat");
                           if (task.isSuccessful()){
                               Log.i("spark","creating");
                               final Users users = new Users(firstName,email,lastName, "0");
                               FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                       .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       Log.i("sparky",users.getEmail());
                                       Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                   }
                               });
                           }else{
                               Toast.makeText(RegisterActivity.this, "Not Regstered", Toast.LENGTH_SHORT).show();
                           }
                            }
                        });


//                        mAuth.createUserWithEmailAndPassword(u_email,u_password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()){
//                                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
//                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
//                                    assert firebaseUser != null;
//                                    String userUId = firebaseUser.getUid();
//                                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userUId);
//                                    HashMap<String,String> hashMap = new HashMap<>();
//                                    hashMap.put("id",userUId);
//                                    hashMap.put("firstname",u_firstName);
//                                    hashMap.put("lastname",u_lastName);
//                                    //hashMap.put("imgUrl","default");
//                                    //hashMap.put("status","offline");
//                                    //hashMap.put("search",firstName.toLowerCase());
//
//                                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Intent intent = new Intent(RegisterActivity.this,MapsActivity.class);
//                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                startActivity(intent);
//                                                finish();
//                                            }
//                                        }
//                                    });
//                                }else{
//                                    Toast.makeText(RegisterActivity.this, "Unable to signin", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

                    }
                }
                    button_register.setEnabled(false);
            }
        });

    }

    public void register(final String firstName, final String lastName, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userUId = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userUId);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",userUId);
                    hashMap.put("firstname",firstName);
                    hashMap.put("lastname",lastName);
                    hashMap.put("hasticket","0");
                    //hashMap.put("imgUrl","default");
                    //hashMap.put("status","offline");
                    //hashMap.put("search",firstName.toLowerCase());

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this,MapsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Unable to signin", Toast.LENGTH_SHORT).show();
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
