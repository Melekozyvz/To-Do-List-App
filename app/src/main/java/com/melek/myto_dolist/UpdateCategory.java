package com.melek.myto_dolist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateCategory extends AddActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt("updateCategoryId");
        String name = extras.getString("updateCategoryName");

        editText=getEdtCategory();
        editText.setText(name);

        Button save=getBtnSave();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtCategory=editText.getText().toString();
                if (txtCategory.equals(null)||txtCategory.isEmpty()||txtCategory.equals(""))
                    editText.setError(getText(R.string.lblempty_error));
                else{
                    Toast.makeText(getApplicationContext(),txtCategory+" "+id,Toast.LENGTH_SHORT).show();
                    MainActivity.db.updateCat(txtCategory,id);
                    MainActivity.db.close();
                    Toast.makeText(getApplicationContext(), getText(R.string.updated_category), Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        });




    }

}
