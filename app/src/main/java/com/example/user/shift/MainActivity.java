package com.example.user.shift;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.user.shift.Requests.input.DateRequest;
import com.example.user.shift.System.AppSettings;
import com.example.user.shift.loaders.DailyScheduleLoader;
import com.example.user.shift.loaders.DateLoader;
import com.example.user.shift.loaders.WeakScheduleLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private AppSettings appSettings;

    Button [] todayLessonsView = new Button[5];
    Button [] tomorrowLessonsView = new Button[5];
    TextView date1;
    TextView date2;

    DailyScheduleLoader todaySchedule;
    DailyScheduleLoader tomorrowSchedule;
    WeakScheduleLoader weakScheduleLoader;
    DateLoader dateLoader;

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                switch (v.getId()) {
                    case R.id.b0:
                        for (int i = 0; i < todaySchedule.dailyScheduleRequest.replacements.size(); i++)
                            if (todaySchedule.dailyScheduleRequest.replacements.get(i).getNumber().equals("1"))
                                todayLessonsView[0].setText(todaySchedule.getLessonString(0));
                        break;
                    case R.id.b1:
                        for (int i = 0; i < todaySchedule.dailyScheduleRequest.replacements.size(); i++)
                            if (todaySchedule.dailyScheduleRequest.replacements.get(i).getNumber().equals("2"))
                                todayLessonsView[1].setText(todaySchedule.getLessonString(1));
                        break;
                    case R.id.b2:
                        for (int i = 0; i < todaySchedule.dailyScheduleRequest.replacements.size(); i++)
                            if (todaySchedule.dailyScheduleRequest.replacements.get(i).getNumber().equals("3"))
                                todayLessonsView[2].setText(todaySchedule.getLessonString(2));
                        break;
                    case R.id.b3:
                        for (int i = 0; i < todaySchedule.dailyScheduleRequest.replacements.size(); i++)
                            if (todaySchedule.dailyScheduleRequest.replacements.get(i).getNumber().equals("4"))
                                todayLessonsView[3].setText(todaySchedule.getLessonString(3));
                        break;
                    case R.id.b4:
                        for (int i = 0; i < todaySchedule.dailyScheduleRequest.replacements.size(); i++)
                            if (todaySchedule.dailyScheduleRequest.replacements.get(i).getNumber().equals("5"))
                                todayLessonsView[4].setText(todaySchedule.getLessonString(4));
                        break;


                    case R.id.b5:
                        for (int i = 0; i < tomorrowSchedule.dailyScheduleRequest.replacements.size(); i++)
                            if (tomorrowSchedule.dailyScheduleRequest.replacements.get(i).getNumber().equals("1"))
                                tomorrowLessonsView[0].setText(tomorrowSchedule.getLessonString(0));
                        break;
                    case R.id.b6:
                        for (int i = 0; i < tomorrowSchedule.dailyScheduleRequest.replacements.size(); i++)
                            if (tomorrowSchedule.dailyScheduleRequest.replacements.get(i).getNumber().equals("2"))
                                tomorrowLessonsView[1].setText(tomorrowSchedule.getLessonString(1));
                        break;
                    case R.id.b7:
                        for (int i = 0; i < tomorrowSchedule.dailyScheduleRequest.replacements.size(); i++)
                            if (tomorrowSchedule.dailyScheduleRequest.replacements.get(i).getNumber().equals("3"))
                                tomorrowLessonsView[2].setText(tomorrowSchedule.getLessonString(2));
                        break;
                    case R.id.b8:
                        for (int i = 0; i < tomorrowSchedule.dailyScheduleRequest.replacements.size(); i++)
                            if (tomorrowSchedule.dailyScheduleRequest.replacements.get(i).getNumber().equals("4"))
                                tomorrowLessonsView[3].setText(tomorrowSchedule.getLessonString(3));
                        break;
                    case R.id.b9:
                        for (int i = 0; i < tomorrowSchedule.dailyScheduleRequest.replacements.size(); i++)
                            if (tomorrowSchedule.dailyScheduleRequest.replacements.get(i).getNumber().equals("5"))
                                tomorrowLessonsView[4].setText(tomorrowSchedule.getLessonString(4));
                        break;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todayLessonsView[0] = (Button) findViewById(R.id.b0);
        todayLessonsView[1] = (Button) findViewById(R.id.b1);
        todayLessonsView[2] = (Button) findViewById(R.id.b2);
        todayLessonsView[3] = (Button) findViewById(R.id.b3);
        todayLessonsView[4] = (Button) findViewById(R.id.b4);

        tomorrowLessonsView[0] = (Button) findViewById(R.id.b5);
        tomorrowLessonsView[1] = (Button) findViewById(R.id.b6);
        tomorrowLessonsView[2] = (Button) findViewById(R.id.b7);
        tomorrowLessonsView[3] = (Button) findViewById(R.id.b8);
        tomorrowLessonsView[4] = (Button) findViewById(R.id.b9);

        date1 = (TextView) findViewById(R.id.d1);
        date2 = (TextView) findViewById(R.id.d2);

        appSettings = new AppSettings(getSharedPreferences(AppSettings.FILE_NAME,
                Context.MODE_PRIVATE));

        if (!appSettings.isSetting(AppSettings.CURRENT_GROUP)) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
        else updateSchedule();

        if (!appSettings.isSetting(AppSettings.IP_SERVER))
            appSettings.setSetting(AppSettings.IP_SERVER, "192.168.0.100");

        for (int i = 0; i<5; i++)
            todayLessonsView[i].setOnClickListener(this);

        for (int i = 0; i<5; i++)
            tomorrowLessonsView[i].setOnClickListener(this);

        for (int i = 0; i<5; i++)
            todayLessonsView[i].setOnTouchListener(onTouchListener);

        for (int i = 0; i<5; i++)
            tomorrowLessonsView[i].setOnTouchListener(onTouchListener);

        TabHost tabs = (TabHost) findViewById(R.id.tabHost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Сегодня");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Завтра");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Полное");
        tabs.addTab(spec);
        tabs.setCurrentTab(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (appSettings.isSetting(AppSettings.CURRENT_GROUP))
            updateSchedule();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.update) {
            updateSchedule();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < 5; i++)
            todayLessonsView[i].setText(todaySchedule.getLessonString(i));


        for (int i = 0; i < todaySchedule.dailyScheduleRequest.replacements.size(); i++){
            int j = Integer.valueOf(todaySchedule.dailyScheduleRequest.replacements.get(i).getNumber())-1;
            todayLessonsView[j].setText(todaySchedule.getReplacementString(i));
        }

        for (int i = 0; i < 5; i++)
            tomorrowLessonsView[i].setText(tomorrowSchedule.getLessonString(i));


        for (int i = 0; i < tomorrowSchedule.dailyScheduleRequest.replacements.size(); i++){
            int j = Integer.valueOf(tomorrowSchedule.dailyScheduleRequest.replacements.get(i).getNumber())-1;
            tomorrowLessonsView[j].setText(tomorrowSchedule.getReplacementString(i));
        }
    }

    private void updateSchedule(){
        todaySchedule = new DailyScheduleLoader(appSettings, todayLessonsView, "today");
        todaySchedule.execute();

        tomorrowSchedule = new DailyScheduleLoader(appSettings, tomorrowLessonsView, "tomorrow");
        tomorrowSchedule.execute();

        weakScheduleLoader = new WeakScheduleLoader(appSettings, this);
        weakScheduleLoader.execute();

        dateLoader = new DateLoader(appSettings, date1, date2);
        dateLoader.execute();
    }
}
