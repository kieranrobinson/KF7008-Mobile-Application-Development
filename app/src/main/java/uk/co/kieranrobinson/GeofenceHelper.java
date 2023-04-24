package uk.co.kieranrobinson;

import static com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL;

import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;

public class GeofenceHelper extends ContextWrapper {
    PendingIntent pendingIntent;
    public GeofenceHelper(Context base) {
        super(base);
    }
    public GeofencingRequest getGeofencingRequest(Geofence geofence){
        return new GeofencingRequest.Builder()
                .addGeofence(geofence)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();
    }
//    public Geofence getGeofence(String id, LatLng latLng, float radius, int transitionTypes){
//        return new Geofence.Builder()
//                .setCircularRegion(latLng.latitude, latLng.longitude, radius)
//                .setRequestId(id)
//                .setTransitionTypes(transitionTypes)
//                .setExpirationDuration(Geofence.NEVER_EXPIRE)
//                .setLoiteringDelay(20000)
//                .build();
//    }

    public Geofence getGeofence(String id, LatLng latLng, float radius){
        return new Geofence.Builder()
                .setCircularRegion(latLng.latitude, latLng.longitude, radius)
                .setRequestId(id)
                .setTransitionTypes(GEOFENCE_TRANSITION_DWELL)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setLoiteringDelay(20000)
                .build();
    }

    public PendingIntent getPendingIntent(){
        if(pendingIntent != null){
            return pendingIntent;
        } else {
            Intent intent = new Intent(this, GeofenceReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,
                    2607,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            return pendingIntent;
        }

    }


}
