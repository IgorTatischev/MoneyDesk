package com.example.moneydesk.ui.mainfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.moneydesk.ViewPagerAdapter;
import com.example.moneydesk.ui.mainfragments.income.IncomeFragment;
import com.example.moneydesk.ui.mainfragments.expense.ExpensesFragment;
import com.example.moneydesk.databinding.FragmentMainBinding;
import com.example.moneydesk.ui.viewmodel.MainViewModel;
import com.google.android.material.tabs.TabLayout;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tabLayout = binding.tablayout;
        viewPager = binding.viewPager;

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new PieChartFragment(),"Главная");
        adapter.addFragment(new IncomeFragment(),"Расходы");
        adapter.addFragment(new ExpensesFragment(),"Доходы");
        viewPager.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}