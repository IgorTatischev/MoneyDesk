package com.example.moneydesk.ui.categoryfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.moneydesk.ViewPagerAdapter;
import com.example.moneydesk.databinding.FragmentCategoryBinding;
import com.example.moneydesk.ui.categoryfragment.expense.CategoryExpenses;
import com.example.moneydesk.ui.categoryfragment.income.CategoryIncome;
import com.google.android.material.tabs.TabLayout;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tabLayout = binding.tablayout2;
        viewPager = binding.viewPager2;
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new CategoryIncome(),"Расходы");
        adapter.addFragment(new CategoryExpenses(),"Доходы");
        viewPager.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
