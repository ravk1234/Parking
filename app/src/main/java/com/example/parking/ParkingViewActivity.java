package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ParkingViewActivity extends AppCompatActivity {

    static Button  button1,button2,button3,button4,nextbutton;
    TextView location_text;
    String selected="";
     DatabaseReference reference,ref1,reference1;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_view);

        //Getting location from mapsActivity
        Intent intent = getIntent();
        final String location = intent.getStringExtra("Location");

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        nextbutton = findViewById(R.id.next_button);
        location_text = findViewById(R.id.location_text);

        location_text.setText(location);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // payment activity opens
                Intent intent1 = new Intent(ParkingViewActivity.this, Details.class);
                startActivity(intent1);
            }
        });

        reference1 = FirebaseDatabase.getInstance().getReference("status1");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String  value = dataSnapshot.getValue().toString();
                if(value.equals("1"))

                    button1.setText("Occupied");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref1 = FirebaseDatabase.getInstance().getReference("status2");

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String  value =dataSnapshot.getValue().toString();
                if(value.equals("1"))
                    //Log.i("spark","creating");
                    button2.setText("Occupied");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "A101";
                reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("hasticket",s);
                button1.setClickable(false);
                Intent intent2 = new Intent(ParkingViewActivity.this, Details.class);
                intent2.putExtra("Slot",s);
                intent2.putExtra("Location",location);
                intent2.putExtra(selected, "a1");
                startActivity(intent2);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "A102";
                reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("hasticket",s);

                button2.setClickable(false);

                Intent intent2 = new Intent(ParkingViewActivity.this, Details.class);
                intent2.putExtra("Slot",s);
                intent2.putExtra("a102", 1);

                intent2.putExtra(selected,"a2");
                startActivity(intent2);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "B101";
                button3.setClickable(false);
                Intent intent2 = new Intent(ParkingViewActivity.this, Details.class);
                intent2.putExtra("Slot",s);
                intent2.putExtra("b101", 1);

                intent2.putExtra(selected,"b1");

                startActivity(intent2);

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "B102";
                button4.setClickable(false);

                Intent intent2 = new Intent(ParkingViewActivity.this, Details.class);
                intent2.putExtra("Slot",s);
                intent2.putExtra("b102", 1);

                intent2.putExtra(selected,"b2");
                startActivity(intent2);

            }
        });
    }
}
