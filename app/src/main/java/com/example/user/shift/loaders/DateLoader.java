package com.example.user.shift.loaders;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.user.shift.Requests.input.AllGroupsRequest;
import com.example.user.shift.Requests.input.DateRequest;
import com.example.user.shift.Requests.input.JsonParser;
import com.example.user.shift.Requests.output.OutputRequest;
import com.example.user.shift.System.AppSettings;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;


public class DateLoader extends AsyncTask<Void, Void, Void> {

    private String answer;
    public DateRequest dateRequest = new DateRequest();
    AppSettings appSettings;
    TextView date1;
    TextView date2;

    public DateLoader(AppSettings appSettings, TextView date1, TextView date2){
        this.appSettings = appSettings;
        this.date1 = date1;
        this.date2 = date2;
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
            out.println("close");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getJson() {
        OutputRequest outputRequest = new OutputRequest();
        outputRequest.setNameRequest("GetDate");
        outputRequest.setIdClient("0");

        return outputRequest.getJsonString();
    }


    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        try {
            System.out.println(answer);
            answer = new String(answer.getBytes("windows-1252"), "windows-1251");
            System.out.println(answer);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JsonParser jP = new JsonParser();
        dateRequest = (DateRequest) jP.parseRequest(answer.replace('\'', '’'));

        date1.setText(dateRequest.date1);
        date2.setText(dateRequest.date2);
    }
}
