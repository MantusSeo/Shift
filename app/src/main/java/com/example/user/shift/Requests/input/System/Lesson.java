package com.example.user.shift.Requests.input.System;

public class Lesson {
    private String name;
    private String teacher;
    private String room;

    public Lesson(String name, String room, String teacher) {
        this.name = name;
        this.teacher = teacher;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public String getTeacher() {
        return teacher;
    }
}