package com.example.moneydesk.ui.mainfragments.income;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moneydesk.Client;
import com.example.moneydesk.Param;
import com.example.moneydesk.R;
import com.example.moneydesk.databinding.FragmentCheckBinding;
import com.example.moneydesk.databinding.FragmentIncomeBinding;
import com.example.moneydesk.ui.mainfragments.Operation;
import com.example.moneydesk.ui.mainfragments.income.IncomeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;
import java.util.ArrayList;


public class IncomeFragment extends Fragment {

    Toast msg;
    RecyclerView rvIncome;
    IncomeAdapter adapter;
    ArrayList<Operation> incomes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income,container,false);
        msg = Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT);
        rvIncome = view.findViewById(R.id.listincome);
        ListIncome();
        return view;
    }

    public void ListIncome()
    {
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
                    String check = rec.getString("checkname");
                    String date = rec.getString("date");
                    incomes.add(new Operation(id,category,amount,check,date));
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
        else
        {
            msg.setText("Нет записей расходов!");
            msg.show();
        }
    }
}