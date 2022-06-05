package com.example.moneydesk.ui.checkfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moneydesk.Client;
import com.example.moneydesk.Param;
import com.example.moneydesk.R;
import com.example.moneydesk.ui.items.Check;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;


public class CheckFragment extends Fragment {

    RecyclerView rvChecks;
    ChecksAdapter adapter;
    ArrayList<Check> checks;
    Toast msg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_check, container, false);
        rvChecks = root.findViewById(R.id.listchecks);
        msg = Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT);
        ListChecks();
        //добавление счёта
        View customLayout = getLayoutInflater().inflate(R.layout.check_add, null,false);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        final EditText textName = customLayout.findViewById(R.id.editName);
        final EditText textAmount = customLayout.findViewById(R.id.editAmount);
        builder.setView(customLayout)
                .setTitle("Новый счёт")
                .setNegativeButton("Отмена", null)
                .setPositiveButton("Добавить", (dialog, which) -> {
                    if (textName.getText().length() == 0 || textAmount.getText().length() == 0)
                    {
                        msg.setText("Что-то не ввели!");
                    }
                    else {
                        String name = String.valueOf(textName.getText());
                        Double amount = Double.valueOf(textAmount.getText().toString());
                        Client client = new Client();
                        String data = client.add_check(name,Param.id_user,amount);
                        ListChecks();
                        if (!Objects.equals(data, "false")) {
                            msg.setText("Счёт успешно добавлен!");
                        }
                        else {
                            msg.setText("Не удалось добавить счёт!");
                        }
                    }
                    msg.show();
                });
        AlertDialog dialog = builder.create();
        FloatingActionButton fab = root.findViewById(R.id.addCheck);
        fab.setOnClickListener(view -> dialog.show());
        return root;
    }

    public void ListChecks()
    {
        checks = new ArrayList<>();
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
}