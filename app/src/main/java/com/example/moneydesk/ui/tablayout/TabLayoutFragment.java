package com.example.moneydesk.ui.tablayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.moneydesk.ui.mainfragments.OperationsFragment;
import com.example.moneydesk.ui.mainfragments.ChartFragment;
import com.example.moneydesk.ui.mainfragments.MainFragment;
import com.example.moneydesk.databinding.FragmentTablayoutBinding;
import com.google.android.material.tabs.TabLayout;

public class TabLayoutFragment extends Fragment {

    private FragmentTablayoutBinding binding;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TabLayoutViewModel tabLayoutViewModel = new ViewModelProvider(this).get(TabLayoutViewModel.class);
        binding = FragmentTablayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tabLayout = binding.tablayout;
        viewPager = binding.viewPager;

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new MainFragment(),"Главная");
        adapter.addFragment(new OperationsFragment(),"Операции");
        adapter.addFragment(new ChartFragment(),"Обзор");
        viewPager.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}