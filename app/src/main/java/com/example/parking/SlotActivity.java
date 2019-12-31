package com.example.parking;
import com.example.parking.UserProfile;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SlotActivity extends AppCompatActivity {

    TextView person_name,email_text,slot_no,location_text;
    String first_name,last_name;
    TextView vehi, dura, total;
    Button login;
    String vt;
    String fire;
    String ht;
    FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase;
    DataSnapshot dataSnapshot;
     DatabaseReference reference, demo;
    String uid;
    private String TAG = "MainActivity";
    String payeeAddress = "akg0753@okaxis";
    String payeeName = "Arpit";
    String transactionNote = "Payment for Parking";
    String amount = "";
    String currencyUnit = "INR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);
        //Getting location from ParkingViewActivity
        Intent intent = getIntent();
        login = findViewById(R.id.login_btn);
        vehi = findViewById(R.id.dot);
        dura = findViewById(R.id.dotted);
        total = findViewById(R.id.dott);
        vt = getIntent().getStringExtra("vehiType");
        ht = getIntent().getStringExtra("nohour");
        fire = getIntent().getStringExtra("selected");
        Log.i("gert",ht.toString());
        vehi.setText(vt);
        dura.setText(ht);

        if (vt.toLowerCase().equals("bike")|| vt.toLowerCase().equals("bikes")){
            int k=10*Integer.valueOf(ht);
            total.setText("Rs. "+String.valueOf(k));
            amount=String.valueOf(total);
        }else if (vt.toLowerCase().equals("car") || vt.toLowerCase().equals("cars")){
            int k=20*Integer.valueOf(ht);
            total.setText("Rs. "+String.valueOf(k));
            amount=String.valueOf(total);
        }

        String slot = intent.getStringExtra("Slot");
        String location = intent.getStringExtra("Location");
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            //Go to login
            Intent intent1 = new Intent(SlotActivity.this,LoginActivity.class);
            //intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
        else {
            String uid = user.getUid();
        }
        //final String id = Users
        //DatabaseReference databaseReference = firebaseDatabase.getReference(uid);
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());



        person_name = findViewById(R.id.person_name);
        email_text = findViewById(R.id.email_text);
        location_text = findViewById(R.id.location);
        slot_no = findViewById(R.id.slot_payment);
        slot_no.setText(slot); // Set Slot No
        location_text.setText(location);//Set location

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(mAuth.getCurrentUser()!=null){
                    Users userProfile = dataSnapshot.getValue(Users.class);
                    //for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                       // Users userProfile = childSnapshot.getValue(Users.class);


                        if (userProfile != null) {
                            first_name = userProfile.getFirstname();
                            last_name = userProfile.getLastname();
                        }


                    String full_name = first_name + last_name;
                    String email = user.getEmail();
                    email_text.setText(email);
                    person_name.setText(full_name);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SlotActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SD","dfd");
                Uri uri = Uri.parse("upi://pay?pa="+payeeAddress+"&pn="+payeeName+"&tn="+transactionNote+
                        "&am="+amount+"&cu="+currencyUnit);
                Log.d(TAG, "onClick: uri: "+uri);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivityForResult(intent,1);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Log.d(TAG, "onActivityResult: requestCode: "+requestCode);
//        Log.d(TAG, "onActivityResult: resultCode: "+resultCode);
        //txnId=UPI20b6226edaef4c139ed7cc38710095a3&responseCode=00&ApprovalRefNo=null&Status=SUCCESS&txnRef=undefined
        //txnId=UPI608f070ee644467aa78d1ccf5c9ce39b&responseCode=ZM&ApprovalRefNo=null&Status=FAILURE&txnRef=undefined

        if(data!=null) {
          //  Log.d(TAG, "onActivityResult: data: " + data.getStringExtra("response"));
            String res = data.getStringExtra("response");
            String search = "SUCCESS";
            if (res.toLowerCase().contains(search.toLowerCase())) {
                //reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                //HashMap<String,Object> hashMap = new HashMap<>();
                //hashMap.put("hasticket",1);
                //reference.updateChildren(hashMap);
                if (fire.equals("a1")){
                    ParkingViewActivity.button1.setEnabled(false);
                }if (fire.equals("a2")){
                    ParkingViewActivity.button2.setEnabled(false);
                }if (fire.equals("b1")){
                    ParkingViewActivity.button3.setEnabled(false);
                }if (fire.equals("b2")){
                    ParkingViewActivity.button4.setEnabled(false);
                }
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
