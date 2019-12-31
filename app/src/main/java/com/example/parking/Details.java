package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Details extends AppCompatActivity {
    EditText vtype;
    Button next;
    EditText noHour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        vtype = findViewById(R.id.vid);
        noHour = findViewById(R.id.hid);
        next = findViewById(R.id.next);
      //  String vt = vtype.getText().toString();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vt = vtype.getText().toString();
                String ht = noHour.getText().toString();
                Log.i("great",vt);
                if (!vt.isEmpty()&&!ht.isEmpty()) {
                    Intent intent = new Intent(Details.this, SlotActivity.class);
                    intent.putExtra("vehiType", vt);
                    intent.putExtra("nohour", ht);
                    startActivity(intent);
                }else
                    Toast.makeText(Details.this, "Please specify vehicle type and number of hours", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
