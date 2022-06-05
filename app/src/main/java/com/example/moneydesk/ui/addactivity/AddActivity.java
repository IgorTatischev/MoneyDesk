package com.example.moneydesk.ui.addactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneydesk.Client;
import com.example.moneydesk.MainActivity;
import com.example.moneydesk.Param;
import com.example.moneydesk.R;
import com.example.moneydesk.ui.entry.Authorization;
import com.example.moneydesk.ui.entry.Registration;
import com.example.moneydesk.ui.items.Category;
import com.example.moneydesk.ui.items.Check;
import com.example.moneydesk.ui.mainfragments.expense.ExpensesFragment;
import com.example.moneydesk.ui.mainfragments.income.IncomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    EditText textSum;
    EditText textDate;
    Spinner spTypes;
    Spinner spCategory;
    Spinner spChecks;
    ArrayList<Category> categories;
    ArrayList<Check> checks;
    Toast msg;
    double sum;
    int type;
    int categoryid;
    int checkid;
    String date;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Добавление");
        msg = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        textSum = findViewById(R.id.textSum);
        textDate = findViewById(R.id.dateDialog);
        spTypes = findViewById(R.id.typeSpinner);
        spCategory = findViewById(R.id.categorySpinner);
        spChecks = findViewById(R.id.checkSpinner);
        String[] items = {"Расход","Доход"};
        ArrayAdapter<String> adapterType = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTypes.setAdapter(adapterType);
        spTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                if (item == "Расход") {
                    setIncomeAdapter();
                    type = 1;
                }
                else if (item == "Доход") {
                    setExpenseAdapter();
                    type = 2;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        setChecksAdapter();
        spChecks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Check check = checks.get(position);
                checkid = check.getID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category = categories.get(position);
                categoryid = category.getID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        textDate.setOnClickListener(v -> {
            if (v == textDate) {
                // устанавливает настоящее время для создания диалога
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                //выбор даты
                DatePickerDialog picker = new DatePickerDialog(AddActivity.this,
                        (view, year, monthOfYear, dayOfMonth) -> {
                            textDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            mDay = dayOfMonth;
                            mMonth = monthOfYear + 1;
                            mYear = year;
                        }, mYear, mMonth, mDay);
                picker.show();
            }
        });
    }

    public void onAddClick(View v) {
        if (textSum.getText().length() == 0)
        {
            msg.setText("Введите сумму!");
            msg.show();
        }
        else{
            sum = Double.parseDouble(textSum.getText().toString());
            date = mYear + "-" + mMonth + "-" + mDay;
            if (type == 1) {
                Client client = new Client();
                client.update_check_income(sum,checkid);
                String data = client.add_income(sum, date, checkid, categoryid);
                if (!Objects.equals(data, "false")) {
                    Intent intent = new Intent(AddActivity.this, IncomeFragment.class);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    msg.setText("Не удалось добавить расход");
                    msg.show();
                }
            }
            else if (type == 2) {
                Client client = new Client();
                client.update_check_expense(sum,checkid);
                String data = client.add_expense(sum, date, checkid, categoryid);
                if (!Objects.equals(data, "false")) {
                    Intent intent = new Intent(AddActivity.this, ExpensesFragment.class);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    msg.setText("Не удалось добавить доход");
                    msg.show();
                }
            }
        }
    }

    public void setIncomeAdapter()
    {
        categories = new ArrayList<>();
        Client client = new Client();
        String data = client.get_category_income(Param.id_user);
        try
        {
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i< jsonArray.length();i++) {
                JSONObject rec = jsonArray.getJSONObject(i);
                int id = rec.getInt("id");
                String name = rec.getString("name");
                categories.add(new Category(id, name));
            }
            ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,
                    categories);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategory.setAdapter(categoryAdapter);
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    public void setExpenseAdapter()
    {
        categories = new ArrayList<>();
        Client client = new Client();
        String data = client.get_category_expense(Param.id_user);
        try
        {
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i< jsonArray.length();i++) {
                JSONObject rec = jsonArray.getJSONObject(i);
                int id = rec.getInt("id");
                String name = rec.getString("name");
                categories.add(new Category(id, name));
            }
            ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,
                    categories);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategory.setAdapter(categoryAdapter);
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    public void setChecksAdapter()
    {
        checks = new ArrayList<>();
        Client client = new Client();
        String data = client.get_checks(Param.id_user);
        try
        {
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i< jsonArray.length();i++) {
                JSONObject rec = jsonArray.getJSONObject(i);
                int id = rec.getInt("id");
                String name = rec.getString("name");
                BigDecimal amount = BigDecimal.valueOf(rec.getDouble("amount"));
                checks.add(new Check(id, name,amount));
            }
            ArrayAdapter<Check> checkArrayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,
                    checks);
            checkArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spChecks.setAdapter(checkArrayAdapter);
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    public void onCancelClick(View v) { finish(); }
}