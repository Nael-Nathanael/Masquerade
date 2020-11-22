package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.services;

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

public class ChatRoomSubscriptionNotificationService extends Service {

    NotificationCompat.Builder notificationBuilder;
    private FirebaseDatabase database;
    private int totalUnread = 0;
    private List<DatabaseReference> references;
    private boolean started = false;
    private ChildEventListener childEventListener;

    public ChatRoomSubscriptionNotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        started = false;
        totalUnread = 0;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                started = true;
                totalUnread = 0;

            }
        }, 2000);

        database = FirebaseDatabase.getInstance();

        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Masquerade")
                .setSmallIcon(R.drawable.ic_baseline_people_24);

        SubscribedChatroomRepository mRepository = new SubscribedChatroomRepository(getApplication());
        LiveData<List<SubscribedChatroom>> mAllSubscribedChatroom = mRepository.getmAllChatrooom();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                totalUnread++;
                refreshNotification();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mAllSubscribedChatroom.observeForever(subscribedChatroomList -> {
            references = new ArrayList<>();
            for (SubscribedChatroom sub : subscribedChatroomList) {
                DatabaseReference reference = database
                        .getReference()
                        .child("chatrooms")
                        .child("public")
                        .child(sub.id)
                        .child("messages");

                reference.addChildEventListener(childEventListener);

                references.add(reference);
            }
        });

        return START_STICKY;
    }

    private void refreshNotification() {
        if (started && totalUnread > 0) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationBuilder.setContentText("You have " + totalUnread + " unread messages on subscribed chat rooms");

            mNotificationManager.notify(1, notificationBuilder.build());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        totalUnread = 0;
        started = false;
        if (references != null) {
            for (DatabaseReference ref : references) {
                ref.removeEventListener(childEventListener);
            }
        }
    }
}