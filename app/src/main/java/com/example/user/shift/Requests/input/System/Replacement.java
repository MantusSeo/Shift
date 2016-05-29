package com.example.user.shift.Requests.input.System;

public class Replacement extends Lesson{

    private String number;
    private String group;

    public Replacement(String group, String number, String name, String room, String teacher) {
        super(name, room, teacher);
        this.number = number;
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public String getNumber() {
        return number;
    }
}
