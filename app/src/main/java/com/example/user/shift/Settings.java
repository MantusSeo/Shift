package com.example.user.shift;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.shift.System.AppSettings;

public class Settings extends AppCompatActivity {

    private AppSettings appSettings;
    EditText ipEdit;
    Button setIp;
    Button changeGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        appSettings = new AppSettings(getSharedPreferences(AppSettings.FILE_NAME,
                Context.MODE_PRIVATE));

        ipEdit = (EditText) findViewById(R.id.ip_edit);
        setIp = (Button) findViewById(R.id.set_ip);
        changeGroup = (Button) findViewById(R.id.change_group);

        ipEdit.setText(appSettings.getSetting(appSettings.IP_SERVER));
    }

    public void changeGroup(View view) {
        Intent intent = new Intent(Settings.this, Login.class);
        startActivity(intent);
    }

    public void setIP(View view) {
        appSettings.setSetting(appSettings.IP_SERVER,
                ipEdit.getText().toString());
    }
}
