/*
 * Copyright © 2025. Richard P. Parkins, M. A.
 * Released under GPL V3 or later
 */

package net.sylvek.itracing2;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

public class VersionActivity extends Activity {
    @Override
    protected void onResume() {
        super.onResume();
        StringBuilder s = new StringBuilder();
        s.append(getString(R.string.app_name));
        try
        {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(
                    getApplicationContext().getPackageName(), 0);
            s.append(" ")
             .append(pi.versionName);
        }
        catch (PackageManager.NameNotFoundException ignored)
        {}
        s.append("\nbuilt ")
         .append(getString(R.string.build_time))
         .append("\n")
         .append(getString(R.string.build_git));
        TextView tv = new TextView(this);
        tv.setText(s.toString());
        setContentView(tv);
    }
}
