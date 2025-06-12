package net.sylvek.itracing2.receivers;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import net.sylvek.itracing2.R;
import net.sylvek.itracing2.database.Devices;
import net.sylvek.itracing2.database.Events;

import java.util.List;
import java.util.Objects;

/**
 * Created by sylvek on 12/06/2015.
 */
public class CapturePosition extends BroadcastReceiver {
    private static Intent callback = new Intent();

    static final int NOTIFICATION_ID = 453436;

    public static final String NAME = "position";
    public static final String TAG = CapturePosition.class.toString();
    static final String CALLBACKACTION = "net.sylvek.itracing2.action.GOT_LOCATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        final String name = intent.getStringExtra(Devices.NAME);
        final String address = intent.getStringExtra(Devices.ADDRESS);
        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        callback.setComponent(ComponentName.unflattenFromString("net.sylvek.itracing2.receivers.CapturePosition"));
        callback.setAction(CALLBACKACTION);
        callback.putExtra(Devices.NAME, name);
        callback.putExtra(Devices.ADDRESS, address);
        PendingIntent pi = PendingIntent.getBroadcast(
                context, 0, callback,
                PendingIntent.FLAG_UPDATE_CURRENT);
        if (Objects.equals(action, "net.sylvek.itracing2.action.CAPTURE_POSITION")) {
            Log.d(TAG, "Capture position for " + name);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Location permission should be requested
                // in the UI when user checks "capture my position"
                // Hard to do because the logic is buried
                // inside a MultiSelectListPreference.
            }
            List<String> ls = lm.getProviders(true);
            try {
                if (ls.contains("gps"))
                {
                    /* If GPS is available use GPS only. Other providers need
                     * a Google server which has privacy and security issues.
                     * Also the Google server relies on Google's list of locations
                     * of cell towers and Wi-Fi hotspots, which are privately
                     *  owned and can be moved, so Google's list can be out of date.
                     */
                    Log.d(TAG, "requestLocationUpdates from gps");
                    lm.requestLocationUpdates(
                            "gps", 0, 0, pi);
                }
                else
                {
                    // If we don't have GPS,
                    // we use the best that the device can give us.
                    Criteria cr = new Criteria();
                    cr.setAccuracy(Criteria.ACCURACY_FINE);
                    Log.d(TAG, "no gps, requestLocationUpdates from best available");
                    lm.requestLocationUpdates(0, 0, cr, pi);
                }
            } catch (SecurityException e) {
                Events.insert(context, "No location permission", address, "Please Allow Location permission in Settings");
                Log.d(TAG, "No location permission");
            }
        } else if (Objects.equals(action, CALLBACKACTION)) {
            Location here = intent.getParcelableExtra(
                    LocationManager.KEY_LOCATION_CHANGED);
            if (here != null) {
                lm.removeUpdates(pi);
                final String position = here.getLatitude() + "," + here.getLongitude();
                Log.d(TAG, "got location " + position);
                Events.insert(context, "position", address, position);
                final Intent mapIntent = getMapIntent(position);
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                final Notification.Builder nfb = new Notification.Builder(context);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    nfb.setChannelId("channel-itracing2");
                }
                nfb.setContentText(context.getString(R.string.display_last_position))
                   .setContentTitle(context.getString(R.string.app_name))
                   .setSmallIcon(R.drawable.ic_launcher)
                   .setAutoCancel(false)
                   .setContentIntent(PendingIntent.getActivity(context, 0, mapIntent, PendingIntent.FLAG_UPDATE_CURRENT));
                notificationManager.notify(NOTIFICATION_ID, nfb.build());
            } else {
                // Otherwise we don't know what to do
                Log.d(TAG, "CapturePosition bad action " + action);
            }
        }
    }

    public static Intent getMapIntent(String position)
    {
        final Uri uri = Uri.parse("geo:" + position + "?z=17&q=" + position);
        return new Intent(Intent.ACTION_VIEW, uri);
    }
}
