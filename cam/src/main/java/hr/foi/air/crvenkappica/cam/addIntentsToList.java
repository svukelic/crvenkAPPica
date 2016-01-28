package hr.foi.air.crvenkappica.cam;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.List;

import hr.foi.air.crvenkappica.core.OnImageReturn;

/**
 * Created by Mario on 28.01.2016..
 */
public  class addIntentsToList implements OnImageReturn {
    public static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
            //Log.d(TAG, "Intent: " + intent.getAction() + " package: " + packageName);
        }
        return list;
    }
    @Override
    public String GetPath() {
        return null;
    }
}
