package com.melek.myto_dolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView toDosList;
    ArrayList<ToDos> toDos;
    ToDoAdapter toDoAdapter;
    static Database db;
    CheckBox checkBox;
    Toolbar toolbar;
    FloatingActionButton fab,fab_todo,fab_category;
    Animation fabOpen, fabClose,rotateForward,rotateBackward;
    boolean isOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getApplicationContext().deleteDatabase("sqllite_database");
        toDos=new ArrayList<ToDos>();
        toolbar= (Toolbar) findViewById(R.id.toolbar);

        toDosList = findViewById(R.id.todoList);
        if (getSupportActionBar()==null){
            setSupportActionBar(toolbar);
        }
        setTitle("Yapılacaklar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        fabOpen= AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotateForward= AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackward =AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        fab_todo=findViewById(R.id.fab_add_todo);
        fab_category=findViewById(R.id.fab_add_category);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate();
            }
        });
        fab_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplication().getApplicationContext(),AddToDoActivity.class);
                startActivity(intent);
            }
        });
        fab_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplication().getApplicationContext(),AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void animate(){
        if (isOpen) {
            fab.startAnimation(rotateBackward);
            fab_todo.startAnimation(fabClose);
            fab_todo.setClickable(false);
            fab_category.startAnimation(fabClose);
            fab_category.setClickable(false);
            isOpen = false;
        }else{
            fab.startAnimation(rotateForward);
            fab_todo.startAnimation(fabOpen);
            fab_todo.setClickable(true);
            fab_category.startAnimation(fabOpen);
            fab_category.setClickable(true);
            isOpen = true;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_categoies) {
            Intent intent=new Intent(getApplication().getApplicationContext(),ViewCategoriesActivity.class);
            startActivity(intent);
        }
        if (id==R.id.action_priorities){
            Intent intent=new Intent(getApplication().getApplicationContext(),ViewPrioritiesActivity.class);
            startActivity(intent);
        }
        if (id==R.id.action_done){
            toolbar.setTitle("Yapılacaklar");
            toDosList = findViewById(R.id.todoList);
            toDos=db.todosDone();
            toDoAdapter = new ToDoAdapter(getApplicationContext(),toDos);
            toDosList.setAdapter(toDoAdapter);
            toolbar.setTitle("Yapılanlar");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (toDos.get(0).getStatus()==0) {
            super.onBackPressed();
        }else{
            toDosList = findViewById(R.id.todoList);
            toDos=db.todos();
            toolbar.setTitle("Yapılacaklar");
            toDoAdapter = new ToDoAdapter(getApplicationContext(),toDos);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toDosList.setAdapter(toDoAdapter);
        }

        }

    @Override
    protected void onResume() {
        super.onResume();
        db = new Database(getApplicationContext()); // Db bağlantısı oluşturuyoruz. İlk seferde database oluşturulur.
        toDos=db.todos();
        if(toDos.size()==0){//konu listesi boşsa
            Toast.makeText(getApplicationContext(), db.getRowCount()+"To Do Listesi Boş", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), db.getRowCount()+" bulunuyor", Toast.LENGTH_SHORT).show();
            toDosList = findViewById(R.id.todoList);
            toDoAdapter = new ToDoAdapter(getApplicationContext(),toDos);
            toDosList.setAdapter(toDoAdapter);

        }
    }
}
