package com.melek.myto_dolist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view=view.inflate(context,R.layout.activity_list,null);
        CheckBox checkBox=view.findViewById(R.id.checkbox);
        final TextView  textView=view.findViewById(R.id.Todo);

        final ToDos todo=toDos.get(i);
        textView.setText(todo.getToDo()+" s: "+todo.getStatus()+" id: "+todo.getId());

        if (todo.getStatus()==1)
            checkBox.setChecked(true);

      checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (todo.getStatus()==0) {
                    todo.setStatus(1);
                }else{
                    todo.setStatus(0);
                }
                MainActivity.db.updateToDo(todo);
                textView.setText(todo.getToDo()+" s: "+todo.getStatus()+" id: "+todo.getId());
            }

        });


        return view;
    }
}
