package com.example.mayaboutiqueclient;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

      //  showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("messageL", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("messageL", "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                // scheduleJob();
            } else {
                // Handle message within 10 seconds
                //  handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("MessageL", "Message Notification Body: " + remoteMessage.getNotification().getBody());



            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());


        }

    }


    public void showNotification(String title, String message) {

//        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.notifica);

        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//        remoteViews.setTextViewText(R.id.name_text_layout,title);
//        remoteViews.setTextViewText(R.id.message_text_layout,message);
//        remoteViews.setImageViewResource(R.id.message_profile_layout,R.mipmap.ic_launcher_foreground);

//        ACCHA HAO
        final Notification notification = new NotificationCompat.Builder(this, "MyNotifications")
                .setSmallIcon(R.drawable.ic_attach_file_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.iclogo))
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.iclogo))
                        .bigLargeIcon(null))
                .build();

        NotificationManagerCompat manger = NotificationManagerCompat.from(this);
        manger.notify(999, notification);



    }

}
