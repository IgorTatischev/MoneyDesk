package com.example.moneydesk.ui.mainfragments.income;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneydesk.Client;
import com.example.moneydesk.R;
import com.example.moneydesk.ui.items.Operation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Objects;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {


    private List<Operation> incomeList;
    private Context context;
    Toast msg;

    public IncomeAdapter(List<Operation> operations, Context context) {
        incomeList = operations;
        this.context = context;
        msg = Toast.makeText(context,"",Toast.LENGTH_SHORT);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.income_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapter.ViewHolder holder, int position) {
        Operation operation = incomeList.get(position);
        holder.categoryName.setText(operation.getCategory());
        holder.incomeAmount.setText("-" + operation.getAmount().toString());
        holder.incomeCheck.setText(operation.getCheck());
        holder.incomeDate.setText(operation.getDate());
        holder.operation = operation;
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;
        public  TextView incomeAmount;
        public  TextView incomeCheck;
        public  TextView incomeDate;
        public ImageButton btnDelete;
        Operation operation;
        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.textCategory);
            incomeAmount = itemView.findViewById(R.id.textIncome);
            incomeCheck = itemView.findViewById(R.id.textCheck);
            incomeDate = itemView.findViewById(R.id.textDate);
            btnDelete = itemView.findViewById(R.id.deleteIncome);
            btnDelete.setOnClickListener(v -> {
                Log.d("income_id", String.valueOf(operation.getID()));
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Удалить операцию")
                        .setMessage("Вы уверены что хотите удалить данный расход?")
                        .setNegativeButton("Отмена", null)
                        .setPositiveButton("Удалить", (dialog1, which) -> {
                            Client client = new Client();
                            Double amount = operation.getAmount().doubleValue();
                            int id_check = operation.getCheckID();
                            client.update_check_expense(amount,id_check);
                            String data = client.delete_income(operation.getID());
                            if (!Objects.equals(data, "false")) {
                                msg.setText("Операция успешно удалена!");
                                incomeList.remove(operation); //удаляем из листа
                                notifyDataSetChanged(); //вызоваем обновление
                            }
                            else {
                                msg.setText("Не удалось удалить операцию!");
                            }
                            msg.show();
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }
    }
}