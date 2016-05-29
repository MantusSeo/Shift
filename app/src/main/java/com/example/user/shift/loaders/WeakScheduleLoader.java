package com.example.user.shift.loaders;

import android.os.AsyncTask;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.example.user.shift.MainActivity;
import com.example.user.shift.R;
import com.example.user.shift.Requests.input.DailyScheduleRequest;
import com.example.user.shift.Requests.input.JsonParser;
import com.example.user.shift.Requests.input.WeakScheduleRequest;
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


public class WeakScheduleLoader extends AsyncTask<Void, Void, Void> {

    WeakScheduleRequest weakScheduleRequest = new WeakScheduleRequest();
    private String answer;
    AppSettings appSettings;
    MainActivity activity;
    private String[] dayOfWeak = new String[] {"Понедельник","Вторник","Среда","Четверг", "Пятница"};
    private String[] mondaySchedule = new String[5];
    private String[] tuesdaySchedule = new String[5];
    private String[] wednesdaySchedule = new String[5];
    private String[] thursdaySchedule = new String[5];
    private String[] fridaySchedule = new String[5];
    boolean isConnected = true;

    public WeakScheduleLoader(AppSettings appSettings, MainActivity activity){
        this.activity = activity;
        this.appSettings = appSettings;
    }

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
        outputRequest.setNameRequest("GetAllGroupSchedule");
        outputRequest.setIdClient("0");

        outputRequest.addArg(appSettings.getSetting(appSettings.CURRENT_GROUP));
        String s = outputRequest.getJsonString();
        return s;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (isConnected) {
            JsonParser jP = new JsonParser();
            weakScheduleRequest = (WeakScheduleRequest) jP.parseRequest(answer);

            for (int i = 0; i < 5; i++)
                mondaySchedule[i] = getLessonString(0, i);
            for (int i = 0; i < 5; i++)
                tuesdaySchedule[i] = getLessonString(1, i);
            for (int i = 0; i < 5; i++)
                wednesdaySchedule[i] = getLessonString(2, i);
            for (int i = 0; i < 5; i++)
                thursdaySchedule[i] = getLessonString(3, i);
            for (int i = 0; i < 5; i++)
                fridaySchedule[i] = getLessonString(4, i);

            updateWeakView();
        }
    }

    private String getLessonString(int j, int i){
        String s = new String();
        s = weakScheduleRequest.firstWeakSchedule.get(j).lessons.get(i).getName() + "\n" +
                weakScheduleRequest.firstWeakSchedule.get(j).lessons.get(i).getRoom() + " " +
                weakScheduleRequest.firstWeakSchedule.get(j).lessons.get(i).getTeacher() + " ||\n" +
                weakScheduleRequest.secondWeakSchedule.get(j).lessons.get(i).getName() + "\n" +
                weakScheduleRequest.secondWeakSchedule.get(j).lessons.get(i).getRoom() + " " +
                weakScheduleRequest.secondWeakSchedule.get(j).lessons.get(i).getTeacher();
        return s;
    }


    private void updateWeakView(){
        Map<String, String> map;
        ArrayList<Map<String, String>> groupDataList = new ArrayList<>();
        for (String group : dayOfWeak) {
            map = new HashMap<>();
            map.put("groupName", group); // время года
            groupDataList.add(map);
        }
        String groupFrom[] = new String[] { "groupName" };
        int groupTo[] = new int[] { android.R.id.text1 };
        ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();
        ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();
        for (String month : mondaySchedule) {
            map = new HashMap<>();
            map.put("monthName", month); // название месяца
            сhildDataItemList.add(map);
        }
        сhildDataList.add(сhildDataItemList);
        сhildDataItemList = new ArrayList<>();
        for (String month : tuesdaySchedule) {
            map = new HashMap<>();
            map.put("monthName", month);
            сhildDataItemList.add(map);
        }
        сhildDataList.add(сhildDataItemList);
        сhildDataItemList = new ArrayList<>();
        for (String month : wednesdaySchedule) {
            map = new HashMap<>();
            map.put("monthName", month);
            сhildDataItemList.add(map);
        }
        сhildDataList.add(сhildDataItemList);
        сhildDataItemList = new ArrayList<>();
        for (String month : thursdaySchedule) {
            map = new HashMap<>();
            map.put("monthName", month);
            сhildDataItemList.add(map);
        }
        сhildDataList.add(сhildDataItemList);
        сhildDataItemList = new ArrayList<>();
        for (String month : fridaySchedule) {
            map = new HashMap<>();
            map.put("monthName", month); // название месяца
            сhildDataItemList.add(map);
        }
        сhildDataList.add(сhildDataItemList);
        String childFrom[] = new String[] { "monthName" };
        int childTo[] = new int[] { android.R.id.text1 };
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                activity, groupDataList,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, сhildDataList, android.R.layout.simple_list_item_1,
                childFrom, childTo);
        ExpandableListView expandableListView = (ExpandableListView) activity.findViewById(R.id.expListView);
        expandableListView.setAdapter(adapter);
    }
}
