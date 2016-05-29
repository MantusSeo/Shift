package com.example.user.shift.Requests.input;

import com.example.user.shift.Requests.Request;
import com.example.user.shift.Requests.input.System.Lesson;
import com.example.user.shift.Requests.input.System.Replacement;

import java.util.ArrayList;

public class DailyScheduleRequest extends Request {

    public ArrayList<Lesson> lessons = new ArrayList<>();
    public ArrayList<Replacement> replacements = new ArrayList<>();
    public ArrayList<String> time = new ArrayList<>();
    public String group;
    public String date;

}
