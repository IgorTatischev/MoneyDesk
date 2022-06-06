package com.example.moneydesk.ui.entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneydesk.Client;
import com.example.moneydesk.MainActivity;
import com.example.moneydesk.Param;
import com.example.moneydesk.R;

public class Authorization extends AppCompatActivity {

    EditText user;
    EditText pass;
    Toast msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        user = findViewById(R.id.editUser);
        pass = findViewById(R.id.editPassword);
        msg = Toast.makeText(this,"",Toast.LENGTH_SHORT);

        user.setText("igor@gmail.com");
        pass.setText("igor123");
    }

    public void onLogIn(View v)
    {
        if (user.getText().length() == 0 || pass.getText().length() == 0)
        {
            msg.setText("Что-то не ввели!");
            msg.show();
        }
        else {
            Client client = new Client();
            String data = client.get_user(user.getText().toString(), pass.getText().toString());
            if (data != null) {
                Param.id_user = Integer.parseInt(data);
                Intent intent = new Intent(Authorization.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                msg.setText("Пользователь не существует или введены неверные данные!");
                msg.show();
            }
        }
    }

    public void onRegister(View v)
    {
        Intent intent = new Intent(this, Registration.class);
        someActivityResultLauncher.launch(intent);
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent data = result.getData();
                        user.setText(data.getStringExtra("user"));
                        pass.setText(data.getStringExtra("password"));
                    }
                }
            });
}