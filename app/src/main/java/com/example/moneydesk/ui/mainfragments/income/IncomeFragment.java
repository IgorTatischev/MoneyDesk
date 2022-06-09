package com.example.moneydesk.ui.mainfragments.income;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.moneydesk.ui.items.Operation;
import com.example.moneydesk.ui.mainfragments.AddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;


public class IncomeFragment extends Fragment {

    RecyclerView rvIncome;
    IncomeAdapter adapter;
    ArrayList<Operation> incomes;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income,container,false);
        rvIncome = view.findViewById(R.id.listincome);
        fab = view.findViewById(R.id.addIncome);
        rvIncome.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvIncome.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ListIncome();
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddActivity.class);
            startActivity(intent);
        });
    }

    public void ListIncome()
    {
        incomes = new ArrayList<>();
        Client client = new Client();
        String data = client.get_all_income(Param.id_user);
        if (data != null) {
            try
            {
                JSONArray jsonArray = new JSONArray(data);
                for(int i = 0; i< jsonArray.length();i++) {
                    JSONObject rec = jsonArray.getJSONObject(i);
                    int id = rec.getInt("id");
                    String category = rec.getString("category");
                    BigDecimal amount = BigDecimal.valueOf(rec.getDouble("sum"));
                    int check_id = rec.getInt("check_id");
                    String check = rec.getString("checkname");
                    String date = rec.getString("date");
                    incomes.add(new Operation(id,category,amount,check_id,check,date));
                    adapter = new IncomeAdapter(incomes,getActivity());
                    rvIncome.setAdapter(adapter);
                }
            }
            catch (JSONException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}