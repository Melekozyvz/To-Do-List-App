package com.melek.myto_dolist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddPriority extends AddActivity {
    EditText editText;
    Button btnSave;
    Button btnCancel;
    TextView textView;
    TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editText=findViewById(R.id.edtCategory);

        textView=findViewById(R.id.lblAdd);
        textView.setText(getText(R.string.priority));

        titleText=findViewById(R.id.title);
        titleText.setText(getText(R.string.add_priority));

        btnSave=findViewById(R.id.btnSave);
        btnCancel=findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String txtPrio=edtCategory.getText().toString();
                if (txtPrio==null&&txtPrio.isEmpty()&&txtPrio=="")
                    edtCategory.setError(getText(R.string.lblempty_error));
                else{

                    MainActivity.db.addPRIO(txtPrio);
                    MainActivity.db.close();
                    Toast.makeText(getApplicationContext(), getText(R.string.added_priority), Toast.LENGTH_LONG).show();
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

    @Override
    public EditText getEdtCategory() {
        return editText;
    }

    @Override
    public void setEdtCategory(EditText edtCategory) {
       this.editText=edtCategory;
    }

    @Override
    public Button getBtnSave() {
        return btnSave;
    }

    @Override
    public void setBtnSave(Button btnSave) {
       this.btnSave=btnSave;
    }

    @Override
    public Button getBtnCancel() {
       return btnCancel;
    }

    @Override
    public void setBtnCancel(Button btnCancel) {
       this.btnCancel=btnCancel;
    }
}
