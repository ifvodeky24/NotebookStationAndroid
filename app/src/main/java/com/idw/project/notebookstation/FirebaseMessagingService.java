package com.idw.project.notebookstation;


import android.util.Log;

import com.freshchat.consumer.sdk.Freshchat;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (Freshchat.isFreshchatNotification(remoteMessage)) {
            Freshchat.handleFcmMessage(getApplicationContext(), remoteMessage);
        } else {
//            String title = remoteMessage.getNotification().getTitle();
//            String message = remoteMessage.getNotification().getBody();
//
//            Log.e("NOTIFICATION", title+" "+message);
        }
        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//          String title = remoteMessage.getNotification().getTitle();
//          String message = remoteMessage.getNotification().getBody();
//
//            System.out.println("Notif"+ title+" "+ message);
//            Log.e("NOTIFICATION", title+" "+message);
//        }

    }


}
