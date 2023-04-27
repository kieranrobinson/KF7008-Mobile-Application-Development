package uk.co.kieranrobinson;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification extends ContextWrapper {
    private String channelName = "NotificationName";
    private String channelID = "NotificationID";

    public Notification(Context base){
        super(base);
        if (Build.VERSION.SDK_INT >= 26){
            createNotificationChannels();
        }
    }

    private void createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);


            Intent notificationIntent = new Intent(this, Memory.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        }
    }

    public void sendNotification(int memoryID){
        Intent intent = new Intent(this, Memory.class);
        intent.putExtra("memoryID",memoryID);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification notification = new NotificationCompat.Builder(this, channelID)
                .setContentTitle("You are near a memory")
                .setContentText("Memory test text")
                .setStyle(new NotificationCompat.BigTextStyle().setSummaryText("Summary Text").setBigContentTitle("Title").bigText("Body"))
                //Set notification icon
                .setSmallIcon(com.google.android.gms.base.R.drawable.common_google_signin_btn_text_light_focused)
                //Notification priority required for devices that are below API 25
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManagerCompat.from(this).notify(1, notification);
    }
}
