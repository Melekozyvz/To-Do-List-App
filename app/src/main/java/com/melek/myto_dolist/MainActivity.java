package com.melek.myto_dolist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.melek.myto_dolist.Adapter.ToDoAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView toDosList;
    ArrayList<ToDos> toDos;
    ToDoAdapter toDoAdapter;
    public static Database db;
    Toolbar toolbar;
    FloatingActionButton fab,fab_todo,fab_category;
    Animation fabOpen, fabClose,rotateForward,rotateBackward;
    boolean isOpen=false;
    private Spinner spCate;
    private Spinner spPrio;
    private Button btnMore;
    private LinearLayout lnrLayout;
    private ConstraintLayout consLayout;
    private Category selectedCategory=null;
    private Priority selectedPriority=null;
    private boolean isFilterMode=false;
    private int status=0;
    public static Context appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext=getApplicationContext();
        //Setting language file
        Locale locale=Locale.getDefault();
        locale.setDefault(locale);
        Configuration config=new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        db = new Database(getApplicationContext()); // Db bağlantısı oluşturuyoruz. İlk seferde database oluşturulur.

        setContentView(R.layout.activity_main);
       // getApplicationContext().deleteDatabase("sqllite_database");
        toDos=new ArrayList<ToDos>();
        toolbar= (Toolbar) findViewById(R.id.toolbar);

        lnrLayout=findViewById(R.id.filterContent);
        consLayout=findViewById(R.id.viewContent);
        final ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams)consLayout.getLayoutParams();
        btnMore=findViewById(R.id.btnMore);
        final int margin=params.topMargin;
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lnrLayout.getVisibility()==LinearLayout.INVISIBLE){
                    isFilterMode=true;
                    lnrLayout.setVisibility(View.VISIBLE);
                   params.setMargins(0,margin+lnrLayout.getHeight(),0,0);
                   filterMode();
                }else{
                    isFilterMode=false;
                    lnrLayout.setVisibility(View.INVISIBLE);
                    params.setMargins(0,margin,0,0);
                    filterMode();
                }
                view.requestLayout();
            }
        });

        toDosList = findViewById(R.id.todoList);
        if (getSupportActionBar()==null){
            setSupportActionBar(toolbar);
        }
        setTitle(getText(R.string.main_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final ArrayList<Category> CatArrayList = db.getAllCategories();
        spCate=findViewById(R.id.spCategory);
        ArrayAdapter<Category> arrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, CatArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCate.setAdapter(arrayAdapter);
        spCate.setSelected(false);

        final ArrayList<Priority> PrioArrayList = db.getAllPriorities();
        spPrio=findViewById(R.id.spPriority);
        ArrayAdapter<Priority> adapter = new ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, PrioArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPrio.setAdapter(adapter);
        spPrio.setSelected(false);

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

        toDosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final PopupMenu popupMenu=new PopupMenu(view.getContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.todo_popup_menu,popupMenu.getMenu());
                popupMenu.show();
                final ToDos todo=toDos.get(i);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.todo_delete) {
                            MainActivity.db.delete(todo.getId());
                            Toast.makeText(getApplicationContext(),todo.getToDo()+" Todo deleted.",Toast.LENGTH_LONG).show();
                            onResume();
                        }
                        return true;
                    }
                });

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
            status=1;
            toolbar.setTitle(getText(R.string.main_title));
            toDosList = findViewById(R.id.todoList);
            toDos=db.todosDone();
            toDoAdapter = new ToDoAdapter(getApplicationContext(),toDos);
            toDosList.setAdapter(toDoAdapter);
            toolbar.setTitle(getText(R.string.maded_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (status==0){
            super.onBackPressed();
        }else{
            toDosList = findViewById(R.id.todoList);
            status=0;
            toDos=db.todos();
            toolbar.setTitle(getText(R.string.main_title));
            toDoAdapter = new ToDoAdapter(getApplicationContext(),toDos);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toDosList.setAdapter(toDoAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toDos=db.todos();

        toDosList = findViewById(R.id.todoList);
        toDoAdapter = new ToDoAdapter(getApplicationContext(),toDos);
        toDosList.setAdapter(toDoAdapter);

     /*   toDosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final PopupMenu popupMenu=new PopupMenu(view.getContext(),toDosList);
                popupMenu.getMenuInflater().inflate(R.menu.todo_popup_menu,popupMenu.getMenu());
                popupMenu.show();
                final ToDos todo=toDos.get(i);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.todo_delete) {
                            MainActivity.db.delete(todo.getId());
                            Toast.makeText(getApplicationContext(),todo.getToDo()+" Todo deleted.",Toast.LENGTH_LONG).show();

                        }
                        return true;
                    }
                });

            }
        });*/

    }
    public void filterMode(){
        if (isFilterMode){
            spCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedCategory=(Category)spCate.getSelectedItem();

                    if (selectedPriority==null||selectedPriority.equals(""))
                        toDos=db.getFilteredToDos(selectedCategory,null,status);
                    else
                        toDos=db.getFilteredToDos(selectedCategory,selectedPriority,status);

                    toDoAdapter = new ToDoAdapter(getApplicationContext(),toDos);
                    toDosList.setAdapter(toDoAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spPrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedPriority=(Priority) spPrio.getSelectedItem();
                    if (selectedCategory==null||selectedCategory.equals(""))
                        toDos=db.getFilteredToDos(null,selectedPriority,status);
                    else
                        toDos=db.getFilteredToDos(selectedCategory,selectedPriority,status);

                    toDoAdapter = new ToDoAdapter(getApplicationContext(),toDos);
                    toDosList.setAdapter(toDoAdapter);            }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else{
            selectedCategory=null;
            selectedPriority=null;
            onResume();
        }
    }
    public void refresh(){
        onResume();
    }
}
