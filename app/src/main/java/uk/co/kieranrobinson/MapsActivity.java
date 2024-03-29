package uk.co.kieranrobinson;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.kieranrobinson.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private int COARSE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private SQLiteDB sqliteDB;
    HashMap<String, Integer> markerIds;
    GeofenceHelper geofenceHelper;
    GeofencingClient geofencingClient;
    int geofenceId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        markerIds = new HashMap<>();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sqliteDB = new SQLiteDB(MapsActivity.this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geofenceHelper = new GeofenceHelper(this);
        geofencingClient = LocationServices.getGeofencingClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Runs when map is loaded and sets up geofences, location markers etc.

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_ACCESS_REQUEST_CODE);
            System.out.println("No permissions");
            return;
        }
        mMap.setMyLocationEnabled(true);


        LatLng newcastle = new LatLng(54.9783, 1.6178);

        placeLocationMarkers();
        placeAllGeofences();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(newcastle));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                //When a marker is clicked load the memory page associated with the marker
                String markerName = marker.getTitle();
                String markerId = marker.getId();
                //Remove alphabetic characters from marker id
                markerId = markerId.replaceAll("[^\\d.]", "");
                //Formatted marker id is equal to memory id
                int formattedMarkerId = Integer.parseInt(markerId)+1;

                //Setup intent extras so pressed marker opens related memory page
                Intent intent = new Intent(MapsActivity.this, Memory.class);
                intent.putExtra("memoryID",formattedMarkerId);
                intent.putExtra("memoryName", sqliteDB.getMemoryName(formattedMarkerId));
                //Load related memory page to pressed marker
                startActivity(intent);

                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == COARSE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Not enough permissions to enable location tracking");
                    return;
                }
                mMap.setMyLocationEnabled(true);
                System.out.println("Map Enabled");
            } else {
                //Permission Refused
                System.out.println("Permissions Refused");
            }

        }
    }

    public void placeLocationMarkers(){
        //Place map pins where memories have been added on map
        System.out.println("Last id is " + sqliteDB.getLastMemoryId());
        int noOfMarkers = sqliteDB.getLastMemoryId();
        for(int i=1; i<=noOfMarkers; i++){
            double longitude = sqliteDB.getMemoryLongitude(i);
            double latitude = sqliteDB.getMemoryLatitude(i);
            String memoryName = sqliteDB.getMemoryName(i);
            LatLng location = new LatLng(latitude, longitude);


            mMap.addMarker(new MarkerOptions().position(location).title(memoryName));
        }
    }

    private void placeAllGeofences(){
        //Place geofences around memories locations on the map, which is used to trigger notifications when a user is located within a geofence.
        ArrayList<Integer> memoryIds = sqliteDB.getAllMemoryID();
        for(int i=0; i<memoryIds.size(); i++){
            int memoryId = memoryIds.get(i);
            double memoryLatitude = sqliteDB.getMemoryLatitude(memoryId);
            double memoryLongitude = sqliteDB.getMemoryLongitude(memoryId);
            LatLng memoryLatLng = new LatLng(memoryLatitude,memoryLongitude);
            placeGeofence(memoryLatLng, 150);

            //Debugging method for geofences, which places a circle on the map so you can see the boundaries of geofences.
            //displayCircle(memoryLatLng, 150);
        }
    }

    private void displayCircle(LatLng latLng, float radius) {
        //Debugging method for geofences, which places a circle on the map so you can see the boundaries of geofences.
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255, 255, 0, 0));
        circleOptions.fillColor(Color.argb(100, 255, 0, 0));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }

    private void placeGeofence(LatLng latLng, float radius){
        //Place a geofence at specified location
        String geofenceID = Integer.toString(geofenceId);
        geofenceId++;

        Geofence geofence = geofenceHelper.getGeofence(geofenceID,latLng,radius);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            System.out.println("Not enough permissions");
            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("Geofence successfully inserted at Latitude:" + latLng.latitude + " Longitude:" + latLng.longitude);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Geofence failed to insert: " + e);
                    }
                });
    }
}