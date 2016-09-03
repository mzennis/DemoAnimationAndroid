package com.mzennis.demo.utilities;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mzennis.demo.MainApplication;

/**
 * Created by docotel on 3/8/16.
 */
public class ApplicationHelper {

    private static final String EMULATOR = "9774d56d682e549c";

    public static Context context() {
        return MainApplication.CONTEXT;
    }

    public static String getPackageName() {
        return context().getPackageName();
    }

    public static Resources getResources() {
        return context().getResources();
    }

    public static AssetManager getAssetManager() {
        return context().getAssets();
    }

    public static String getLastPackageName(){
        return getPackageName().substring(getPackageName().lastIndexOf(".") + 1);
    }

    @Nullable
    public static String getName() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LoggerHelper.warning("PackageManager was NULL");
                return null;
            }

            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            if (applicationInfo == null) {
                LoggerHelper.warning("ApplicationInfo was NULL");
                return null;
            }

            final CharSequence label = applicationInfo.loadLabel(packageManager);
            if (label == null) {
                LoggerHelper.warning("Label was NULL");
                return null;
            }

            return label.toString();
        }
        catch (final Exception e) {
            LoggerHelper.wtf(e);
            return null;
        }
    }

    @Nullable
    public static Bitmap getIcon() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LoggerHelper.warning("PackageManager was NULL");
                return null;
            }

            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            if (applicationInfo == null) {
                LoggerHelper.warning("ApplicationInfo was NULL");
                return null;
            }

            final Drawable drawable = applicationInfo.loadIcon(packageManager);
            return BitmapHelper.fromDrawable(drawable);
        }
        catch (final Exception e) {
            LoggerHelper.wtf(e);
            return null;
        }
    }

    @Nullable
    public static String getVersion() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LoggerHelper.warning("PackageManager was NULL");
                return null;
            }

            final PackageInfo applicationInfo = packageManager.getPackageInfo(getPackageName(), 0);
            if (applicationInfo == null) {
                LoggerHelper.warning("ApplicationInfo was NULL");
                return null;
            }

            return applicationInfo.versionName;

        } catch (Exception e) {
            LoggerHelper.wtf(e);
            return null;
        }
    }

    public static PackageManager packageManager() {
        return context().getPackageManager();
    }

    @Nullable
    public static String androidId() {
        final String androidId = Settings.Secure.getString(context().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidId)) {
            LoggerHelper.warning("AndroidId was NULL");
            return null;
        }
        if (androidId.equals(EMULATOR)) {
            LoggerHelper.warning("EMULATOR");
        }
        return androidId.toLowerCase();
    }
}