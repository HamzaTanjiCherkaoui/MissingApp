package com.apps.hamza.missingapp;

/**
 * Created by Asus on 05/01/2016.
 */

public class Todo {
    int id;
    String task ;
    boolean state;

    public Todo() {

        // TODO Auto-generated constructor stub
    }
    public Todo(int id,String task, boolean state) {
        super();
        this.id =id;
        this.task = task;
        this.state = state;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }

    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
}
