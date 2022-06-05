package com.example.moneydesk.ui.mainfragments.income;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneydesk.ui.addactivity.AddActivity;
import com.example.moneydesk.Client;
import com.example.moneydesk.Param;
import com.example.moneydesk.R;
import com.example.moneydesk.ui.items.Operation;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income,container,false);
        rvIncome = view.findViewById(R.id.listincome);
        ListIncome();
        FloatingActionButton fab = view.findViewById(R.id.addIncome);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddActivity.class);
            startActivityForResult(intent,1);
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
               ListIncome();
            }
        }
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
                    rvIncome.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvIncome.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                }
            }
            catch (JSONException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}