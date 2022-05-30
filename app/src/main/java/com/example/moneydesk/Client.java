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

    public String get_checks(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_checks", obj.toString());
        return request.getData();
    }

    public String get_category_income(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_category_income", obj.toString());
        return request.getData();
    }

    public String get_category_expense(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_category_expense", obj.toString());
        return request.getData();
    }
    //расходы по категориям за месяц
    public String get_sumincome_month(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_sumincome_month", obj.toString());
        return request.getData();
    }
    //расходы по категориям за неделю
    public String get_sumincome_week(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_sumincome_week", obj.toString());
        return request.getData();
    }
    //расходы по категориям за сегодня
    public String get_sumincome_today(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_sumincome_today", obj.toString());
        return request.getData();
    }
    //расходы со всех счетов
    public String get_all_income(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_all_income", obj.toString());
        return request.getData();
    }
    //доходы со всех счетов
    public String get_all_expenses(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_all_expenses", obj.toString());
        return request.getData();
    }
}