package com.melek.myto_dolist.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.melek.myto_dolist.Category;
import com.melek.myto_dolist.Priority;
import com.melek.myto_dolist.R;

import java.util.ArrayList;
import java.util.List;

public class PriorityAdapter extends BaseAdapter {
    List<Priority> priorities;
    Context context;

    public PriorityAdapter(Context context, ArrayList<Priority> priorities){
        this.context=context;
        this.priorities=priorities;
    }
    @Override
    public int getCount() {
        return priorities.size();
    }

    @Override
    public Object getItem(int i) {
        return priorities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return priorities.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view=view.inflate(context, R.layout.lists,null);
        final TextView textView=view.findViewById(R.id.text);

        final Priority priority=priorities.get(i);
        textView.setText(priority.getPriority());

        return view;
    }
}
