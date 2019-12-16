package com.melek.myto_dolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddToDoActivity extends AppCompatActivity {

    Button btnSave;
    Button btnCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        final Spinner spinner=findViewById(R.id.category);
        final Spinner spinner1=findViewById(R.id.priority);
        final EditText editText=findViewById(R.id.ToDoText);

        spinner.setSelected(false);
        spinner1.setSelected(false);
        final ArrayList<String> CatArrayList = MainActivity.db.getAllCategories();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CatArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        final ArrayList<String> PrioArrayList = MainActivity.db.getAllPriorities();

        ArrayAdapter<String> prioArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PrioArrayList);
        prioArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(prioArrayAdapter);


        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ToDos toDos=new ToDos();
                String txtTodo=editText.getText().toString();
                if (txtTodo==null&&txtTodo.isEmpty()&&txtTodo=="")
                    editText.setError("Bu alan boş olamaz");
                else{
                    toDos.setToDo(txtTodo);
                    toDos.setCategory((int) spinner.getSelectedItemId()+1);
                    toDos.setPriority((int)spinner1.getSelectedItemId()+1);
                    toDos.setStatus(0);
                    MainActivity.db.addTODO(toDos);
                    MainActivity.db.close();
                    Toast.makeText(getApplicationContext(), "To-Do Eklendi.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        });
        btnCancel=findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}