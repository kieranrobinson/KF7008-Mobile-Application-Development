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

        //Store currently triggering geofences in a list
        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        //Generate notification is user is dwelling in geofence area. Dwell used to avoid notification spam around geofence borders.
        if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL){
            Notification notification = new Notification(context);

            assert triggeringGeofences != null;
            for(int i=0; i<triggeringGeofences.size(); i++){
                System.out.println("Number " + i + " = " + triggeringGeofences.get(i));
            }
            int triggeredGeofenceID = Integer.parseInt(triggeringGeofences.get(0).getRequestId()) + 1;

            notification.sendNotification(triggeredGeofenceID, "Memory Nearby", "View Memory Information");
        }
    }
}
