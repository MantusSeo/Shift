package com.example.user.shift.Requests.input;

import com.example.user.shift.Requests.Request;
import com.example.user.shift.Requests.input.AllGroupsRequest;
import com.example.user.shift.Requests.input.DailyScheduleRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser {

    public static Request parseRequest(String jsonString){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try{
            switch (gson.fromJson(jsonString, Request.class).getNameRequest()) {
                case "AllGroupsRequest": return gson.fromJson(jsonString, AllGroupsRequest.class);
                case "DailyScheduleRequest": return gson.fromJson(jsonString, DailyScheduleRequest.class);
                case "WeakScheduleRequest": return gson.fromJson(jsonString, WeakScheduleRequest.class);
                case "DateRequest": return gson.fromJson(jsonString, DateRequest.class);
            }
        }
        catch (com.google.gson.JsonSyntaxException e){
            System.out.println("Клиент передал запрос не правильный запрос");
        }
        return null;
    }
}
