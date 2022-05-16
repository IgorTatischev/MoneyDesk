package com.example.moneydesk.ui.checkfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.moneydesk.databinding.FragmentCheckBinding;
import com.example.moneydesk.databinding.FragmentCheckBinding;

public class CheckFragment extends Fragment {

    private FragmentCheckBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CheckViewModel checkViewModel =
                new ViewModelProvider(this).get(CheckViewModel.class);

        binding = FragmentCheckBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}