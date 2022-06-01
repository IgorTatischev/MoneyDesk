package com.example.moneydesk.ui.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneydesk.Client;
import com.example.moneydesk.MainActivity;
import com.example.moneydesk.Param;
import com.example.moneydesk.R;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void onCancelClick(View v) { finish(); }
}