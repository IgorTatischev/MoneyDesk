package com.example.moneydesk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    EditText textName;
    EditText textDate;
    Spinner spTypes;
    Spinner spCategory;
    Spinner spCheks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Добавление");
        textName = findViewById(R.id.textName);
        textDate = findViewById(R.id.dateDialog);
        spTypes = findViewById(R.id.typeSpinner);
        spCategory = findViewById(R.id.categorySpinner);
        spCheks = findViewById(R.id.checkSpinner);
    }

    public void onAddClick(View v) {
    }

    public void onCancelClick(View v) { finish(); }
}