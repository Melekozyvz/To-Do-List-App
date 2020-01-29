package com.melek.myto_dolist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.melek.myto_dolist.Adapter.PriorityAdapter;

import java.util.ArrayList;

public class ViewPrioritiesActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton fab;
    ArrayList<String> selectedPriorities;
    ArrayList<Priority> priorities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);
        setTitle(getText(R.string.priorities_title));
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
                final PopupMenu popupMenu=new PopupMenu(getApplicationContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.show();
                final Priority priority=priorities.get(i);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.delete){
                            MainActivity.db.deletePriority(priority.getId());
                            onResume();
                        }
                        if(item.getItemId()==R.id.update){
                            Intent intent=new Intent(getApplicationContext(),UpdatePriority.class);
                            intent.putExtra("updatePriorityName",priority.getPriority());
                            intent.putExtra("updatePriorityId",priority.getId());
                            startActivity(intent);
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
                selectedPriorities.add(priorities.get(i).getPriority());
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
        PriorityAdapter adapter=new PriorityAdapter(getApplicationContext(),priorities);
        listView.setAdapter(adapter);
    }
}
