package com.example.moneydesk.ui.mainfragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneydesk.Client;
import com.example.moneydesk.Param;
import com.example.moneydesk.R;
import com.example.moneydesk.ui.items.Category;
import com.example.moneydesk.ui.items.Check;

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
    int type;
    int categoryid;
    int checkid;
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
        double sum;
        String date;
        if (mYear != 0 && mMonth != 0 && mDay != 0 && textSum.getText().length() != 0) {
            date = mYear + "-" + mMonth + "-" + mDay;
            sum = Double.parseDouble(textSum.getText().toString());
            if (sum > 0)
            {
                if (type == 1) {
                    Client client = new Client();
                    String data = client.update_check_income(sum,checkid);
                    if (Objects.equals(data, "true"))
                    {
                        client.add_income(sum, date, checkid, categoryid);
                        finish();
                    }
                    else
                    {
                        msg.setText("Недостаточно средств на счёте!");
                        msg.show();
                    }
                }
                else if (type == 2) {
                    Client client = new Client();
                    String data = client.update_check_expense(sum,checkid);
                    if (Objects.equals(data, "true"))
                    {
                        client.add_expense(sum, date, checkid, categoryid);
                        finish();
                    }
                    else
                    {
                        msg.setText("Не удалось долбавить доход!");
                        msg.show();
                    }
                }
            }
            else {
                msg.setText("Пожалуйста не вводите отрицательную сумму или ноль!");
                msg.show();
            }
        }
        else {
            msg.setText("Проверьте ввели ли вы сумму и дату!");
            msg.show();
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