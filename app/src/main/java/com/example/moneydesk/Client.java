package com.example.moneydesk;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static android.content.ContentValues.TAG;

public class Client {
    private HttpRequest request;

    public Client(){
        this.request = new HttpRequest();
    }

    public String get_user(String usr, String pass) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("login", usr);
            obj.put("secret", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_user", obj.toString());
        return request.getData();
    }

    public String reg_user(String log, String pass) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("login", log);
            obj.put("secret", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("reg_user", obj.toString());
        return request.getData();
    }
}