package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
//import android.widget.SearchView;

public class MapsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView list;
    private ListViewAdapter adapter;
    private SearchView searchView;
    private String[] locationList;
    public static ArrayList<Locations> locationsArrayList = new ArrayList<Locations>();
    TextView logout;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final String TAG = "AccountFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setBackgroundResource(R.drawable.searchview_rounded);
        logout =findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //Logout
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        //setupFireBaseListener();

        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                //sendUserToLoginActivity();
            }
        });*/

        locationList = new String[]{"Hexagon Canteen", "NR1", "NR2","LHTC","New Academic Building"};

        // Locate the ListView in listview_main.xml
        list = (ListView)findViewById(R.id.listview1);

        locationsArrayList = new ArrayList<>();

        for (int i = 0; i < locationList.length; i++) {
            Locations movieNames = new Locations(locationList[i]);
            // Binds all strings into an array
            locationsArrayList.add(movieNames);
        }

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Sending selected location to parking View Activity
                Toast.makeText(MapsActivity.this,locationsArrayList.get(position).getAnimalName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MapsActivity.this, ParkingViewActivity.class);
                String s = locationsArrayList.get(position).getAnimalName();

                intent.putExtra("Location",s);
                startActivity(intent);

            }
        });



    }



    /*public void sendUserToLoginActivity() {     //Logout

        FirebaseAuth.getInstance().signOut();
        //finish();
        //startActivity(new Intent(this,LoginActivity.class));


    }*/


    /*@Override
    public void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthStateListener!=null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }

    }*/




    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String text = newText;
        adapter.filter(text);
        return false;
    }
}
