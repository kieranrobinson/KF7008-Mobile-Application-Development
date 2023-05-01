package uk.co.kieranrobinson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.io.Console;
import java.util.Calendar;

public class AddMemoryActivity extends AppCompatActivity {
    private SQLiteDB sqliteDB;
    FusedLocationProviderClient fusedLocationProviderClient;
    int PERMISSIONID;
    static double recentLongitude;
    static double recentLatitude;
    private Button buttonDatePicker;
    private TextView textViewSelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);

        sqliteDB = new SQLiteDB(AddMemoryActivity.this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        getSupportActionBar().setTitle("Add Memory");

        buttonDatePicker = findViewById(R.id.buttonDatePicker);
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);

        buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatePicker to allow user to select date of memory
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddMemoryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                textViewSelectedDate.setText(dayOfMonth + "-" + (month+1) + "-" + year);
                            }
                        },
                        year,
                        month,
                        day
                );
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    public void addMemory(View view){
        EditText memoryName = (EditText) findViewById(R.id.editTextMemoryName);
        EditText memoryDescription = (EditText) findViewById(R.id.editTextMemoryDescription);
        int lastMemoryId = sqliteDB.getLastMemoryId();

        TextView memoryNameError = findViewById(R.id.textViewTitleError);
        memoryNameError.setVisibility(View.INVISIBLE);
        TextView memoryDescriptionError = findViewById(R.id.textViewDescriptionError);
        memoryDescriptionError.setVisibility(View.INVISIBLE);
        TextView memoryDateError = findViewById(R.id.textViewDateError);
        memoryDateError.setVisibility(View.INVISIBLE);

        getLastLocation();

        //Check that all form fields are filled to validate inputs, before adding to database
        if(!memoryName.getText().toString().equals("") && !memoryDescription.getText().toString().equals("") && !textViewSelectedDate.getText().toString().equals("UNSELECTED")){
            sqliteDB.addNewMemory(memoryName.getText().toString(), memoryDescription.getText().toString(), textViewSelectedDate.getText().toString());
            sqliteDB.addNewLocation(lastMemoryId, recentLongitude, recentLatitude);
            finish();
        } else {
            if(memoryName.getText().toString().equals("")){
                memoryNameError.setText("Memory name cannot be empty");
                memoryNameError.setVisibility(View.VISIBLE);
            } else {
                memoryNameError.setVisibility(View.INVISIBLE);
            }


            if(memoryDescription.getText().toString().equals("")){
                memoryDescriptionError.setText("Memory description cannot be empty");
                memoryDescriptionError.setVisibility(View.VISIBLE);
            } else {
                memoryDescriptionError.setVisibility(View.INVISIBLE);
            }


            if(textViewSelectedDate.getText().toString().equals("UNSELECTED")){
                memoryDateError.setText("Memory date cannot be empty");
                memoryDateError.setVisibility(View.VISIBLE);
            } else {
                memoryDateError.setVisibility(View.INVISIBLE);
            }


            //TODO: Implement error message TextViews
            System.out.println("ERROR");
        }


    }

    //Missing permission warning suppressed as if statement checks for this already
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        Double longitude = null;
        Double latitude = null;

        //Get last location if associated permissions are enabled
        if(checkPermissions()){
            if(isLocationEnabled()){
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

                            AddMemoryActivity.recentLongitude = location.getLongitude();
                            AddMemoryActivity.recentLatitude = location.getLatitude();
                        }
                    }
                });
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