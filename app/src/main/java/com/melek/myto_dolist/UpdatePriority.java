package com.melek.myto_dolist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePriority extends AddPriority {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt("updatePriorityId");
        String name = extras.getString("updatePriorityName");

        editText=getEdtCategory();
        editText.setText(name);

        Button save=getBtnSave();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtPriority=editText.getText().toString();
                if (txtPriority.equals(null)||txtPriority.isEmpty()||txtPriority.equals(""))
                    editText.setError(getText(R.string.lblempty_error));
                else{
                    Toast.makeText(getApplicationContext(),txtPriority+" "+id,Toast.LENGTH_SHORT).show();
                    MainActivity.db.updatePrio(txtPriority,id);
                    MainActivity.db.close();
                    Toast.makeText(getApplicationContext(), getText(R.string.updated_priority), Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        });
    }
}
