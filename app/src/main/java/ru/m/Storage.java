package ru.m;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.jetbrains.annotations.Nullable;


public class Storage {

    public static void setCurrentUser(Context context, int user) {
        SharedPreferences defsettings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor eddef = defsettings.edit();
        Log.d("jop", "setCurrentUser: "+ String.valueOf(user));
        eddef.putInt("currentUser", user);
        eddef.apply();
    }

    public static int getCurrentUser(Context c) {
        SharedPreferences defsettings = PreferenceManager.getDefaultSharedPreferences(c);
        Log.d("jop", "currentUser int = "+ String.valueOf(defsettings.getInt("currentUser",0)));
        return defsettings.getInt("currentUser",0);
    }

    public static void store(Context c, String key, String value) {
        int currentUser = getCurrentUser(c);
        SharedPreferences settings = c.getSharedPreferences(String.valueOf(currentUser), 0);
        SharedPreferences.Editor ed = settings.edit();
        ed.putString(key, value);
        ed.apply();
    }

    public static String restore(Context c, String key) {
        int currentUser = getCurrentUser(c);
        SharedPreferences settings = c.getSharedPreferences(String.valueOf(currentUser), 0);
        Log.d("jop", key+" str= "+ settings.getString(key, ""));
        return settings.getString(key, "");
    }


    public static void store(Context c, String key, Integer value) {
        int currentUser = getCurrentUser(c);
        SharedPreferences settings = c.getSharedPreferences(String.valueOf(currentUser), 0);
        SharedPreferences.Editor ed = settings.edit();
        ed.putInt(key, value);
        ed.apply();
    }

    public static Integer restoreint(Context c, String key) {
        int currentUser = getCurrentUser(c);
        SharedPreferences settings = c.getSharedPreferences(String.valueOf(currentUser), 0);
        Log.d("jop", key+" int= "+ String.valueOf(settings.getInt(key, 0)));
        return settings.getInt(key, 0);
    }

}
