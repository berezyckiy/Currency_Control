package maks.dev.diplom.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by berezyckiy on 2/14/17.
 */

public class PreferenceUtils {

    public static void saveString(@NonNull Activity activity, @NonNull String key, @NonNull String value) {
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(@NonNull Activity activity, @NonNull String key, @NonNull String defaultValue) {
        return activity.getPreferences(MODE_PRIVATE).getString(key, defaultValue);
    }

    public static void saveInteger(@NonNull Activity activity, @NonNull String key, @NonNull Integer value) {
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInteger(@NonNull Activity activity, @NonNull String key, @NonNull Integer defaultValue) {
        return activity.getPreferences(MODE_PRIVATE).getInt(key, defaultValue);
    }

    public static Boolean isContainsKey(@NonNull Activity activity, @NonNull String key) {
        return activity.getPreferences(MODE_PRIVATE).contains(key);
    }

}
