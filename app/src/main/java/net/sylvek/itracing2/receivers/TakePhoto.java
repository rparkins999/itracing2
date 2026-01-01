/*
 * Copyright © 2025. Richard P. Parkins, M. A.
 * Released under GPL V3 or later
 */

package net.sylvek.itracing2.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.sylvek.itracing2.PhotoActivity;

public class TakePhoto extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Intent it = new Intent(
                context.getApplicationContext(), PhotoActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }
}
