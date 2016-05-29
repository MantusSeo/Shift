package com.example.user.shift.loaders;


import android.os.AsyncTask;

import com.example.user.shift.Requests.input.AllGroupsRequest;
import com.example.user.shift.Requests.input.JsonParser;
import com.example.user.shift.Requests.output.OutputRequest;
import com.example.user.shift.System.AppSettings;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;


public class AllGroupLoader extends AsyncTask<Void, Void, Void> {

    private String answer;
    public AllGroupsRequest allGroupsRequest = new AllGroupsRequest(new ArrayList<String>());
    AppSettings appSettings;
    boolean isConnected = true;

    public AllGroupLoader(AppSettings appSettings) {
        this.appSettings = appSettings;
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
            isConnected = false;

        }
        return null;
    }

    private String getJson() {
        OutputRequest outputRequest = new OutputRequest();
        outputRequest.setNameRequest("GetAllGroups");
        outputRequest.setIdClient("0");

        return outputRequest.getJsonString();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (isConnected) {
            try {
                answer = new String(answer.getBytes("windows-1252"), "windows-1251");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            JsonParser jP = new JsonParser();
            allGroupsRequest = (AllGroupsRequest) jP.parseRequest(answer);
        }
    }
}
