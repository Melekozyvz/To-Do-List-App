package com.melek.myto_dolist;

import android.os.Bundle;
import android.widget.EditText;

public class UpdatePriority extends AddPriority {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editText=findViewById(R.id.edtCategory);

    }
}
