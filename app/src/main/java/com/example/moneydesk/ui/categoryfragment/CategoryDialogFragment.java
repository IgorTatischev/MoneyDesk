package com.example.moneydesk.ui.categoryfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.moneydesk.Client;
import com.example.moneydesk.Param;
import com.example.moneydesk.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class CategoryDialogFragment extends DialogFragment {

    private OnContinueClick continueClick;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Toast msg = Toast.makeText(getContext(),"",Toast.LENGTH_SHORT);
        View customLayout = getLayoutInflater().inflate(R.layout.category_add, null,false);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        final EditText textName = customLayout.findViewById(R.id.editCategory);
        final Spinner spinner = customLayout.findViewById(R.id.spinnerCategory);
        String[] items = {"Расход","Доход"};
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return builder.setView(customLayout)
                .setTitle("Добавление категории")
                .setMessage("Введите название категории")
                .setNegativeButton("Отмена", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String name = String.valueOf(textName.getText());
                    Client client = new Client();
                    if(spinner.getSelectedItem().equals("Расход"))
                    {
                        String data = client.add_incomecategory(name,Param.id_user);
                        if (!Objects.equals(data, "false")) {
                            msg.setText("Категория расхода успешно добавлена!");
                            continueClick.onContinueClicked();

                        }
                        else {
                            msg.setText("Не удалось добавить категорию!");
                        }
                        msg.show();
                    }
                    else if(spinner.getSelectedItem().equals("Доход"))
                    {
                        String data = client.add_expensecategory(name,Param.id_user);
                        if (!Objects.equals(data, "false")) {
                            msg.setText("Категория дохода успешно добавлена!");
                            continueClick.onContinueClicked();
                        }
                        else {
                            msg.setText("Не удалось добавить категорию!");
                        }
                        msg.show();
                    }
                })
                .create();
    }

    public void setOnContinueClick(OnContinueClick continueCancelClick) {
        this.continueClick = continueCancelClick;
    }

    public interface OnContinueClick {
        void onContinueClicked();
    }
}
