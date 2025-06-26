package net.sylvek.itracing2.receivers;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import net.sylvek.itracing2.BluetoothLEService;

/**
 * Created by sylvek on 20/05/2015.
 */
public class LinkBackground extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
        Log.d(BluetoothLEService.TAG, "bluetooth change state: " + state);
        final Intent bleService = new Intent(context, BluetoothLEService.class);

        if (state == BluetoothAdapter.STATE_TURNING_OFF) {
            bleService.putExtra("connect",false);
            context.startService(bleService);
        } else if (state == BluetoothAdapter.STATE_ON) {
            bleService.putExtra("connect",true);
            context.startService(bleService);
        }
    }
}
