package com.melek.myto_dolist.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.melek.myto_dolist.Category;
import com.melek.myto_dolist.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends BaseAdapter{
    List<Category> categories;
    Context context;

    public CategoryAdapter(Context context, ArrayList<Category> categories){
        this.context=context;
        this.categories=categories;
    }
    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categories.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view=view.inflate(context, R.layout.lists,null);

        final TextView textView=view.findViewById(R.id.text);

        final Category category=categories.get(i);
        textView.setText(category.getCategory());

        return view;
    }
}
