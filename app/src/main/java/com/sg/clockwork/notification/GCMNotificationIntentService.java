package com.sg.clockwork.notification;

/**
 * Created by jiabao.tan.2012 on 20/10/2015.
 */
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sg.clockwork.R;
import com.sg.clockwork.view.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class GCMNotificationIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1000;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GCMNotificationIntentService() {
        super(GCMNotificationIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (!extras.isEmpty()) {
            // read extras as sent from server
            String title = extras.getString("title");
            String message = extras.getString("message");
            sendNotification(message, title);
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String message, String title) {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Previous", "dashboard");

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String messageTitle = title;
        if (messageTitle == null) {
            messageTitle = "Clockwork";
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.cw)
                .setContentTitle(messageTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}