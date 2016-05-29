package com.example.user.shift.System;

import android.content.SharedPreferences;

public class AppSettings {

    public static final String FILE_NAME = "file_settings";
    public static final String CURRENT_GROUP = "current_group";
    public static final String IP_SERVER = "ip_server";

    private SharedPreferences settings;

    public AppSettings(SharedPreferences appSettings) {
        settings = appSettings;
    }

    public void setSetting(String setting, String value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(setting, value);
        editor.apply();
    }

    public String getSetting(String setting){
        if (settings.contains(setting)||!settings.getString(setting, "").equals(""))
            return settings.getString(setting, "");
        return "";
    }

    public boolean isSetting(String setting){
        return settings.contains(setting) ||
                !settings.getString(setting, "").equals("");
    }

}
