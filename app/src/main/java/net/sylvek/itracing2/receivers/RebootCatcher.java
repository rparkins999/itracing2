package net.sylvek.itracing2.receivers;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import net.sylvek.itracing2.BluetoothLEService;
import net.sylvek.itracing2.Preferences;

import java.util.Objects;



public class RebootCatcher extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "RebootCatcher.BroadcastReceiver()");
        if (   (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED"))
            && Preferences.isRebootRestart(context))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, BluetoothLEService.class));
            } else {
                context.startService(new Intent(context, BluetoothLEService.class));
            }
        }
    }

    public static final String TAG = RebootCatcher.class.toString();
}
