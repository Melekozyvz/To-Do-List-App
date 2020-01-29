package com.melek.myto_dolist;

public class Priority {
    private int id;
    private String priority;
    public Priority(){

    }
    public Priority(int id,String priority){
        this.id=id;
        this.priority=priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return priority;
    }
}
