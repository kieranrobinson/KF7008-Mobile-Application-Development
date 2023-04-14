package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

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
    }

    public void addMemory(View view){
        EditText memoryName = (EditText) findViewById(R.id.editTextMemoryName);
        EditText memoryDescription = (EditText) findViewById(R.id.editTextMemoryDescription);

        sqliteDB.addNewMemory(memoryName.getText().toString(), memoryDescription.getText().toString());

        //TODO: Add current location to memory with highest ID using addNewLocation()
        getLastLocation();
    }

    private void getLastLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                //TODO: Do location logic
                System.out.println("LOCATION ENABLED");
            }
        }
        else{
            requestPermissions();
        }
    }

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