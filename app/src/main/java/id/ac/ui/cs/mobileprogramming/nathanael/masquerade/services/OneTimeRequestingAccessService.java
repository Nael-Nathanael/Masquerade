package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.SubscribedChatroom;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.repository.SubscribedChatroomRepository;

import static id.ac.ui.cs.mobileprogramming.nathanael.masquerade.App.CHANNEL_ID;

public class OneTimeRequestingAccessService extends Service {

    public OneTimeRequestingAccessService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("NaelReceiver", "command init");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Masquerade")
                .setContentText("Let's Visit Masquerade Chatroom Again")
                .setSmallIcon(R.drawable.ic_baseline_people_24);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, notificationBuilder.build());

        return START_NOT_STICKY;
    }
}