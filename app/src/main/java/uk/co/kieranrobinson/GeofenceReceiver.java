package uk.co.kieranrobinson;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if(geofencingEvent.hasError()){
            String error = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.getErrorCode());
            Log.d("Error: ",error);
            return;
        }
        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
        Toast.makeText(context, "Geofence Accessed", Toast.LENGTH_SHORT).show();
    }
}
