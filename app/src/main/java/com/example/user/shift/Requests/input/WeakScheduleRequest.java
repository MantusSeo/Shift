package com.example.user.shift.Requests.input;

import com.example.user.shift.Requests.Request;
import com.example.user.shift.Requests.input.System.DailySchedule;

import java.util.ArrayList;

public class WeakScheduleRequest extends Request {

    public ArrayList<DailySchedule> firstWeakSchedule = new ArrayList<>();
    public ArrayList<DailySchedule> secondWeakSchedule = new ArrayList<>();
    public String group;
    public String weak;

}
