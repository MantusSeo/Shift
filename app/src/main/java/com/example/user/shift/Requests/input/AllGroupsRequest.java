package com.example.user.shift.Requests.input;

import com.example.user.shift.Requests.Request;

import java.util.ArrayList;

public class AllGroupsRequest extends Request {

    public ArrayList<String> groups = new ArrayList<>();

    public AllGroupsRequest(ArrayList<String> groups) {
        this.groups = groups;
    }

}
