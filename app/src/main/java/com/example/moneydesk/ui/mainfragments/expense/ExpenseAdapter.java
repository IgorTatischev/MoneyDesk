package com.example.moneydesk.ui.mainfragments.expense;

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
import com.example.moneydesk.ui.mainfragments.Operation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Objects;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private List<Operation> expenseList;
    private Context context;
    Toast msg;

    public ExpenseAdapter(List<Operation> operations, Context context) {
        expenseList = operations;
        this.context = context;
        msg = Toast.makeText(context,"",Toast.LENGTH_SHORT);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.expense_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ViewHolder holder, int position) {
        Operation operation = expenseList.get(position);
        holder.categoryName.setText(operation.getCategory());
        holder.expenseAmount.setText("+" + operation.getAmount().toString());
        holder.expenseCheck.setText(operation.getCheck());
        holder.expenseDate.setText(operation.getDate());
        holder.operation = operation;
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;
        public  TextView expenseAmount;
        public  TextView expenseCheck;
        public  TextView expenseDate;
        public ImageButton btnDelete;
        Operation operation;
        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.textCategory);
            expenseAmount = itemView.findViewById(R.id.textExpense);
            expenseCheck = itemView.findViewById(R.id.textCheck);
            expenseDate = itemView.findViewById(R.id.textDate);
            btnDelete = itemView.findViewById(R.id.deleteExpense);
            btnDelete.setOnClickListener(v -> {
                Log.d("expense_id", String.valueOf(operation.getID()));
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Удалить операцию")
                        .setMessage("Вы уверены что хотите удалить данный доход?")
                        .setNegativeButton("Отмена", null)
                        .setPositiveButton("Удалить", (dialog1, which) -> {
                            Client client = new Client();
                            String data = client.delete_expense(operation.getID());
                            if (!Objects.equals(data, "false")) {
                                msg.setText("Операция успешно удалена!");
                                expenseList.remove(operation); //удаляем из листа
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