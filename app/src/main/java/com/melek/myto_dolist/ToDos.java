package com.melek.myto_dolist;


public class ToDos  {

    private int Id;
    private String toDo;
    private int priority;
    private int category;
    private int status;
    public ToDos(){

    }
    public ToDos(String text, int priority, int category,int status){
        this.toDo=text;
        this.priority=priority;
        this.status=status;
        this.category=category;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return toDo;
    }
}
