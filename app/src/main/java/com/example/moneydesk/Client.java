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
import java.math.BigDecimal;
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
    //расходы за месяц
    public String get_totalincome_month(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_totalincome_month", obj.toString());
        return request.getData();
    }
    //доходы за месяц
    public String get_totalexpense_month(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_totalexpense_month", obj.toString());
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
    //расходы с конкретного счета
    public String get_check_income(int id,int id_check) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
            obj.put("id_check", id_check);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_check_income", obj.toString());
        return request.getData();
    }
    //доходы с конкретного счета
    public String get_check_expenses(int id,int id_check) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_user", id);
            obj.put("id_check", id_check);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("get_check_expenses", obj.toString());
        return request.getData();
    }

    public String add_check(String name, int id, Double sum) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name_check", name);
            obj.put("id_user", id);
            obj.put("sum", sum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("add_check", obj.toString());
        return request.getData();
    }

    public String delete_check(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_check", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("delete_check", obj.toString());
        return request.getData();
    }

    public String delete_income(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_operation", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("delete_income", obj.toString());
        return request.getData();
    }

    public String delete_expense(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_operation", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("delete_expense", obj.toString());
        return request.getData();
    }

    public String delete_incomecategory(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_category", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("delete_incomecategory", obj.toString());
        return request.getData();
    }

    public String delete_expensecategory(int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_category", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("delete_expensecategory", obj.toString());
        return request.getData();
    }
}