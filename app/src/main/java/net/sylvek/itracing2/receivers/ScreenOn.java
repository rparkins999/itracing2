package net.sylvek.itracing2.receivers;

import static net.sylvek.itracing2.BluetoothLEService.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import net.sylvek.itracing2.Preferences;
import net.sylvek.itracing2.database.Devices;
import net.sylvek.itracing2.database.Events;

public class ScreenOn extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(TAG, "Screen on");
        final Cursor cursor = Devices.findDevices(context);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                final String address = cursor.getString(0);
                if (Devices.isEnabled(context, address)) {
                    for (String action : Preferences.getActionScreenOn(context, address)) {
                        final Intent bi = new Intent("net.sylvek.itracing2.action." + action);
                        bi.putExtra(Devices.ADDRESS, address);
                        bi.putExtra(Devices.SOURCE, Preferences.Source.screen_on.name());
                        bi.addCategory("android.intent.category.DEFAULT");
                        context.sendBroadcast(bi);
                        Events.insert(context, Preferences.Source.screen_on.name(), address, action);
                    }
                }
            } while (cursor.moveToNext());
        }
    }
}
