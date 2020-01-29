package com.melek.myto_dolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText edtCategory;
    Button btnSave;
    Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtCategory=findViewById(R.id.edtCategory);

        btnSave=findViewById(R.id.btnSave);
        btnCancel=findViewById(R.id.btnCancel);

        btnSave=findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String txtCategory=edtCategory.getText().toString();
                if (txtCategory.equals(null)||txtCategory.isEmpty()||txtCategory.equals(""))
                    edtCategory.setError(getText(R.string.lblempty_error));
                else{
                    MainActivity.db.addCAT(txtCategory);
                    MainActivity.db.close();
                    Toast.makeText(getApplicationContext(), getText(R.string.added_category), Toast.LENGTH_LONG).show();
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
    public EditText getEdtCategory() {
        return edtCategory;
    }


    public void setEdtCategory(EditText edtCategory) {
        this.edtCategory = edtCategory;
    }

    public Button getBtnSave() {
        return btnSave;
    }

    public void setBtnSave(Button btnSave) {
        this.btnSave = btnSave;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }
}
