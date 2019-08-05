package com.melek.myto_dolist;

import android.support.annotation.Nullable;

public class ToDos  {
    enum Priority{
        HIGH,
        NORMAL,
        LOW
    }
    enum Status{
        DONE,
        NOTDONE
    }
    private int Id;
    private String toDo;
    private Priority priority;
    private String category;
    private Status status;
    public ToDos(String text, Priority priority, @Nullable String category,Status status){
        this.toDo=text;
        this.priority=priority;
        this.status=status;

        if(category!=null){
            this.category=category;
        }
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
