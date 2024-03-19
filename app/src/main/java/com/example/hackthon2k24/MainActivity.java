package com.example.hackthon2k24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner fromSpinner, toSpinner;
    private Button searchButton;
    private List<String> locations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);

        locations = new ArrayList<>();

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference locationsRef = database.getReference("locations");

        // Read data from Firebase
        locationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue(String.class);
                    locations.add(location);
                }

                // Populate From spinner
                ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_item, locations);
                fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                fromSpinner.setAdapter(fromAdapter);

                // Populate To spinner
                ArrayAdapter<String> toAdapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_item, locations);
                toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                toSpinner.setAdapter(toAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        searchButton=(Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected locations from spinners
                String fromLocation = fromSpinner.getSelectedItem().toString();
                String toLocation = toSpinner.getSelectedItem().toString();

                // Merge the locations as "from_to" format
                String path = fromLocation + "_" + toLocation;
                GlobalData.pack=toLocation;
                GlobalData.loc=path;
                Intent intent = new Intent(MainActivity.this,RoutesList.class);
                startActivity(intent);
            }
        });
    }
}