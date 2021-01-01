package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.services.ChatRoomSubscriptionNotificationService;

public class MasqBroadcastReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ChatRoomSubscriptionNotificationService.class));
    }
}