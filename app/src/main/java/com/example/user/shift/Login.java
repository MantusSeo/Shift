package com.example.user.shift;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.shift.System.AppSettings;
import com.example.user.shift.loaders.AllGroupLoader;

public class Login extends AppCompatActivity {

    Button select;
    Button ok;
    String[] group;
    AllGroupLoader groupLoader;
    AppSettings appSettings;
    String currentGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        select = (Button) findViewById(R.id.select);
        ok = (Button) findViewById(R.id.ok);

        appSettings = new AppSettings(getSharedPreferences(AppSettings.FILE_NAME,
                Context.MODE_PRIVATE));

        groupLoader = new AllGroupLoader(appSettings);
        groupLoader.execute();

        if (appSettings.isSetting(appSettings.CURRENT_GROUP))
            select.setText(appSettings.getSetting(appSettings.CURRENT_GROUP));
    }

    protected void onResume() {
        super.onResume();
        if (!appSettings.isSetting(appSettings.CURRENT_GROUP))
            ok.setEnabled(false);
    }


    public void ok(View view){
        appSettings.setSetting(appSettings.CURRENT_GROUP, currentGroup);
        finish();
    }

    public void selectGroup(View view){
        group = new String[groupLoader.allGroupsRequest.groups.size()];
        for (int i = 0; i< groupLoader.allGroupsRequest.groups.size(); i++)
            group[i] = groupLoader.allGroupsRequest.groups.get(i);

        showDialog(1);
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.select_group);
        adb.setItems(group, myClickListener);

        return adb.create();
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            currentGroup = group[which];
            select.setText(currentGroup);
            ok.setEnabled(true);
        }
    };

}
