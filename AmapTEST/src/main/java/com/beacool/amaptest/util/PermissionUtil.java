package com.beacool.amaptest.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Package com.beacool.baidumaptest.util.
 * Created by JoshuaYin on 2016/11/18.
 * <p>
 * Description:
 */

public class PermissionUtil {
    private static final String TAG = "PermissionUtil";

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermission(Context context, String permission) {
        if(Build.VERSION.SDK_INT >= 23)
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        return true;
    }

    public static void requestPermissions(Activity activity, List<String> permissions, int requestCode) {
        if (permissions == null || permissions.isEmpty())
            return;

        if (Build.VERSION.SDK_INT >= 23) {
            String[] permission = permissions.toArray(new String[permissions.size()]);
            for (String per : permission) {
                LogTool.LogI(TAG, "requestPermissions--->" + per);
            }
            ActivityCompat.requestPermissions(activity, permission, requestCode);
        }
    }

    private void requestPermission(Activity activity, String permission, int requestCode) {
        if (TextUtils.isEmpty(permission))
            return;

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        }
    }

    public static List<String> makePermission(Context context, String[] permission) {
        List<String> permissions = new ArrayList<>();

        if (permission == null || permission.length == 0)
            return permissions;

        for (String per : permission) {
            if (!checkPermission(context, per))
                permissions.add(per);
        }

        return permissions;
    }
}
