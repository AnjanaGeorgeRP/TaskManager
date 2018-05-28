package com.myapplicationdev.android.taskmanager;

import java.io.Serializable;

public class Task implements Serializable{
    private int id;
    private  String name;
    private  String description;
    private int time;

    public Task(int id, String name, String description, int time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
    }

    public Task(String name, String description, int time) {
        this.name = name;
        this.description = description;
        this.time = time;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
