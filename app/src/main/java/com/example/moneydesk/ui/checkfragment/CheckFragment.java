package com.example.moneydesk.ui.checkfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneydesk.Client;
import com.example.moneydesk.Param;
import com.example.moneydesk.databinding.FragmentCheckBinding;
import com.example.moneydesk.ui.viewmodel.CheckViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;


public class CheckFragment extends Fragment {

    private FragmentCheckBinding binding;
    RecyclerView rvChecks;
    ChecksAdapter adapter;
    ArrayList<Check> checks = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CheckViewModel checkViewModel = new ViewModelProvider(this).get(CheckViewModel.class);
        binding = FragmentCheckBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvChecks = binding.listchecks;
        ListChecks();
        return root;
    }

    public void ListChecks()
    {
        Client client = new Client();
        String data = client.get_checks(Param.id_user);
        try
        {
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i< jsonArray.length();i++) {
                JSONObject rec = jsonArray.getJSONObject(i);
                int id = rec.getInt("id");
                String name = rec.getString("name");
                BigDecimal amount = BigDecimal.valueOf(rec.getDouble("amount"));
                checks.add(new Check(id, name,amount));
            }
            adapter = new ChecksAdapter(checks,getActivity());
            rvChecks.setAdapter(adapter);
            rvChecks.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvChecks.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}