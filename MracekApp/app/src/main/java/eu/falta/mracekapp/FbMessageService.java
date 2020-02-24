package eu.falta.mracekapp;

import android.util.Log;

public class FbMessageService extends FirebaseMessagingService {
    String TAG = "firebaseoznam";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "Nazov: „ + remoteMessage.getNotification().getTitle());
        Log.d(TAG, "správa: „ + remoteMessage.getNotification().getBody());
    }

}
