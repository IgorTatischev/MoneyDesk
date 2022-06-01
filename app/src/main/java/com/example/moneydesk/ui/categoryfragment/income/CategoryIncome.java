package com.example.moneydesk.ui.categoryfragment.income;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moneydesk.Client;
import com.example.moneydesk.Param;
import com.example.moneydesk.databinding.FragmentCategoryIncomeBinding;
import com.example.moneydesk.ui.categoryfragment.Category;
import com.example.moneydesk.ui.categoryfragment.CategoryDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CategoryIncome extends Fragment {

    private FragmentCategoryIncomeBinding binding;
    RecyclerView rvCategoryInc;
    CategoryIncomeAdapter adapter;
    ArrayList<Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryIncomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvCategoryInc = binding.listcategoryinc;
        ListCategoryInc();
        FloatingActionButton fab = binding.addCategoryInc;
        fab.setOnClickListener(v -> {
            CategoryDialogFragment dialog = new CategoryDialogFragment();
            dialog.show(getActivity().getSupportFragmentManager(), "custom");
            dialog.setOnContinueClick(() -> {
                ListCategoryInc();
            });
        });
        return root;
    }

    public void ListCategoryInc()
    {
        categories = new ArrayList<>();
        Client client = new Client();
        String data = client.get_category_income(Param.id_user);
        try
        {
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i< jsonArray.length();i++) {
                JSONObject rec = jsonArray.getJSONObject(i);
                int id = rec.getInt("id");
                String name = rec.getString("name");
                categories.add(new Category(id, name));
            }
            adapter = new CategoryIncomeAdapter(categories,getActivity());
            rvCategoryInc.setAdapter(adapter);
            rvCategoryInc.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvCategoryInc.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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