package com.melek.myto_dolist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends BaseAdapter {
    List<ToDos> toDos;
    Context context;

    public ToDoAdapter(Context context, ArrayList<ToDos> toDos){
        this.context=context;
        this.toDos=toDos;
    }
    @Override
    public int getCount() {
        return toDos.size();
    }

    @Override
    public Object getItem(int i) {
        return toDos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return toDos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view=view.inflate(context,R.layout.activity_list,null);
        CheckBox checkBox=view.findViewById(R.id.checkbox);
        TextView textView=view.findViewById(R.id.Todo);

        ToDos todo=toDos.get(i);
        textView.setText(todo.getToDo());
        return null;
    }
}
