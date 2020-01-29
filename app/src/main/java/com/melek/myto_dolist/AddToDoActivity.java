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
        final ArrayList<Category> CatArrayList = MainActivity.db.getAllCategories();

        ArrayAdapter<Category> arrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, CatArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        final ArrayList<Priority> PrioArrayList = MainActivity.db.getAllPriorities();

        ArrayAdapter<Priority> prioArrayAdapter = new ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, PrioArrayList);
        prioArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(prioArrayAdapter);


        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ToDos toDos=new ToDos();
                String txtTodo=editText.getText().toString();
                if (txtTodo.equals(null)&&txtTodo.isEmpty()||txtTodo.equals(""))
                    editText.setError(getText(R.string.lblempty_error));
                else{
                    Category selectedCat=(Category) spinner.getSelectedItem();
                    Priority selectedPrio=(Priority) spinner1.getSelectedItem();
                    toDos.setToDo(txtTodo);
                    toDos.setCategory(selectedCat.getId());
                    toDos.setPriority(selectedPrio.getId());
                    toDos.setStatus(0);
                    MainActivity.db.addTODO(toDos);
                    MainActivity.db.close();
                    Toast.makeText(getApplicationContext(), getText(R.string.added_todo), Toast.LENGTH_LONG).show();
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