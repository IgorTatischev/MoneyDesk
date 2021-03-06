package com.example.moneydesk;

import org.json.JSONException;
import org.json.JSONObject;

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

    public String add_income(Double sum, String date, int check,int category) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("sum", sum);
            obj.put("date", date);
            obj.put("check_id", check);
            obj.put("category", category);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("add_income", obj.toString());
        return request.getData();
    }

    public String update_check_income(Double sum,int check) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("sum", sum);
            obj.put("check_id", check);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("update_check_income", obj.toString());
        return request.getData();
    }

    public String update_check_expense(Double sum,int check) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("sum", sum);
            obj.put("check_id", check);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("update_check_expense", obj.toString());
        return request.getData();
    }

    public String add_expense(Double sum, String date, int check,int category) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("sum", sum);
            obj.put("date", date);
            obj.put("check_id", check);
            obj.put("category", category);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("add_expense", obj.toString());
        return request.getData();
    }

    public String add_incomecategory(String name, int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("category_name", name);
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("add_incomecategory", obj.toString());
        return request.getData();
    }

    public String add_expensecategory(String name, int id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("category_name", name);
            obj.put("id_user", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.post("add_expensecategory", obj.toString());
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