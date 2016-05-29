package com.example.user.shift.Requests.output;


import com.example.user.shift.Requests.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class OutputRequest extends Request {

    private ArrayList<String> args = new ArrayList<>();

    public ArrayList<String> getArgs() {
        return args;
    }

    public void addArg(String value) {
        args.add(value);
    }

    public String getJsonString(){
        Gson gson = new Gson();
        GsonBuilder builder= new GsonBuilder();

        return gson.toJson(this);
    }
}