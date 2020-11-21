package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.services.ChatRoomSubscriptionNotificationService;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.services.OneTimeRequestingAccessService;

public class MasqBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, OneTimeRequestingAccessService.class));
        context.startService(new Intent(context, ChatRoomSubscriptionNotificationService.class));
        Log.d("NaelReceiver", "Received!");
    }
}