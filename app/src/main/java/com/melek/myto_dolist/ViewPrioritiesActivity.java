package com.melek.myto_dolist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class ViewPrioritiesActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton fab;
    ArrayList<String> selectedPriorities;
    ArrayList<String> priorities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);
        setTitle("Öncelikler");
        listView=findViewById(R.id.list);

        fab=findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplication().getApplicationContext(),AddPriority.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final PopupMenu popupMenu=new PopupMenu(ViewPrioritiesActivity.this,listView);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.delete){
                            MainActivity.db.deletePriority(i+1);
                        }
                        if(item.getItemId()==R.id.update){

                        }
                        return true;
                    }
                });
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPriorities=new ArrayList<>();
                selectedPriorities.add(priorities.get(i));
                view.setBackgroundColor(Color.LTGRAY);
                return false;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        priorities=MainActivity.db.getAllPriorities();
        listView=findViewById(R.id.list);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.lists,R.id.text,priorities);
        listView.setAdapter(adapter);
    }
}
