package id.ac.ui.cs.mobileprogramming.nathanael.masquerade;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class App extends Application {

    public static final String CHANNEL_ID = "masquerade";

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID,
                "Masquerade Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
    }


}
