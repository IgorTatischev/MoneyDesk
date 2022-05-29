package com.example.moneydesk.ui.mainfragments.expense;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneydesk.Client;
import com.example.moneydesk.Param;
import com.example.moneydesk.R;
import com.example.moneydesk.ui.mainfragments.Operation;
import com.example.moneydesk.ui.mainfragments.income.IncomeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;


public class ExpensesFragment extends Fragment {

    RecyclerView rvExpenses;
    ExpenseAdapter adapter;
    ArrayList<Operation> expenses = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses,container,false);
        rvExpenses = view.findViewById(R.id.listexpenses);
        ListExpenses();
        return view;
    }

    public void ListExpenses()
    {
        Client client = new Client();
        String data = client.get_all_expenses(Param.id_user);
        try
        {
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i< jsonArray.length();i++) {
                JSONObject rec = jsonArray.getJSONObject(i);
                int id = rec.getInt("id");
                String category = rec.getString("category");
                BigDecimal amount = BigDecimal.valueOf(rec.getDouble("sum"));
                String check = rec.getString("checkname");
                String date = rec.getString("date");
                expenses.add(new Operation(id,category,amount,check,date));
                adapter = new ExpenseAdapter(expenses,getActivity());
                rvExpenses.setAdapter(adapter);
                rvExpenses.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvExpenses.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            }
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
    }
}