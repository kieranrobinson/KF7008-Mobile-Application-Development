package uk.co.kieranrobinson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.io.Console;

public class AddMemoryActivity extends AppCompatActivity {
    private SQLiteDB sqliteDB;
    FusedLocationProviderClient fusedLocationProviderClient;
    int PERMISSIONID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);

        sqliteDB = new SQLiteDB(AddMemoryActivity.this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    public void addMemory(View view){
        EditText memoryName = (EditText) findViewById(R.id.editTextMemoryName);
        EditText memoryDescription = (EditText) findViewById(R.id.editTextMemoryDescription);

        sqliteDB.addNewMemory(memoryName.getText().toString(), memoryDescription.getText().toString());

        //TODO: Add current location to memory with highest ID using addNewLocation()
        getLastLocation();
    }

    //Missing permission warning suppressed as if statement checks for this already
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                //TODO: Do location logic
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if(location == null){
                            System.out.println("Was null: Requesting update");
                            requestLocationUpdates();
                        } else {
                            System.out.println("Latitude: " + location.getLatitude());
                            System.out.println("Longitude: " + location.getLongitude());
                        }
                    }
                });

                System.out.println("LOCATION ENABLED");
            }
        }
        else{
            requestPermissions();
        }
    }

    //Missing permission warning suppressed as if statement checks for this already
    @SuppressLint("MissingPermission")
    public void requestLocationUpdates(){
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setIntervalMillis(500)
                .setMaxUpdateDelayMillis(1000)
                .build();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location last = locationResult.getLastLocation();
            System.out.println("Location callback set longitude to " + last.getLongitude());
            System.out.println("Location callback set latitude to " + last.getLatitude());
        }
    };

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean checkPermissions(){
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, PERMISSIONID);
    }

}