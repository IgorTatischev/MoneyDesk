package com.example.moneydesk.ui.entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneydesk.Client;
import com.example.moneydesk.R;

import java.util.Objects;

public class Registration extends AppCompatActivity {

    EditText user;
    EditText pass;
    Toast msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        user = findViewById(R.id.editRegUser);
        pass = findViewById(R.id.editRegPassword);
        msg = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }

    public void onRegClick(View view) {
        if (user.getText().length() == 0 || pass.getText().length() == 0)
        {
            msg.setText("Что-то не ввели!");
            msg.show();
        }
        else {
            Client client = new Client();
            String data = client.reg_user(user.getText().toString(), pass.getText().toString());
            if (!Objects.equals(data, "false")) {
                Intent intent = new Intent(Registration.this, Authorization.class);
                intent.putExtra("user",user.getText().toString());
                intent.putExtra("password", pass.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                msg.setText("Неудачно!");
                msg.show();
            }
        }
    }

    public void onCancelClick(View v) { finish(); }
}