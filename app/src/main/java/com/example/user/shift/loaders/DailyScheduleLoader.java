package com.example.user.shift.loaders;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.example.user.shift.MainActivity;
import com.example.user.shift.R;
import com.example.user.shift.Requests.input.DailyScheduleRequest;
import com.example.user.shift.Requests.input.JsonParser;
import com.example.user.shift.Requests.output.OutputRequest;
import com.example.user.shift.System.AppSettings;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DailyScheduleLoader extends AsyncTask<Void, Void, Void> {

    private String answer;
    public DailyScheduleRequest dailyScheduleRequest;
    AppSettings appSettings;
    Button b[];
    String day;
    boolean isConnected = true;

    public DailyScheduleLoader(AppSettings appSettings, Button b[], String day) {
        this.appSettings = appSettings;
        this.b = b;
        this.day = day;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Socket stream;
        PrintWriter out;
        InputStream inStream;
        DataInputStream in;
        try {
            stream = new Socket(java.net.InetAddress.getByName(
                    appSettings.getSetting(appSettings.IP_SERVER)), 4444);
            inStream = stream.getInputStream();
            in = new DataInputStream(inStream);
            out = new PrintWriter(stream.getOutputStream(),true);


            out.println(getJson());
            answer = in.readLine();
            System.out.println(answer);
            answer = new String(answer.getBytes("windows-1252"), "windows-1251").replace('\'', '’');
            out.println("close");

        } catch (IOException e) {
            e.printStackTrace();
            isConnected = false;
        }
        return null;
    }

    private String getJson() {
        OutputRequest outputRequest = new OutputRequest();
        outputRequest.setNameRequest("GetDailySchedule");
        outputRequest.setIdClient("0");

        outputRequest.addArg(appSettings.getSetting(appSettings.CURRENT_GROUP));
        outputRequest.addArg(day);
        String s = outputRequest.getJsonString();
        return s;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (isConnected) {
            JsonParser jP = new JsonParser();
            dailyScheduleRequest = (DailyScheduleRequest) jP.parseRequest(answer);

            for (int i = 0; i < 5; i++)
                b[i].setText(getLessonString(i));


            for (int i = 0; i < dailyScheduleRequest.replacements.size(); i++) {
                int j = new Integer(dailyScheduleRequest.replacements.get(i).getNumber()) - 1;
                b[j].setText(getReplacementString(i));
            }
        }
    }

    public String getLessonString(int i){
        return dailyScheduleRequest.lessons.get(i).getName() + "\n" +
                dailyScheduleRequest.lessons.get(i).getRoom() + " " +
                dailyScheduleRequest.lessons.get(i).getTeacher();
    }

    public String getReplacementString(int i){
        return dailyScheduleRequest.replacements.get(i).getName() + "\n" +
                dailyScheduleRequest.replacements.get(i).getRoom() + " " +
                dailyScheduleRequest.replacements.get(i).getTeacher();
    }
}
