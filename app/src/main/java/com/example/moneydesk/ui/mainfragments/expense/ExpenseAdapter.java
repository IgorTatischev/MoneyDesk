package com.example.moneydesk.ui.mainfragments.expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneydesk.R;
import com.example.moneydesk.ui.mainfragments.Operation;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private Context context;
    private List<Operation> expenseList;
    public ExpenseAdapter(List<Operation> operations, Context context) {
        expenseList = operations;
        this.context = context;
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
        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.textCategory);
            expenseAmount = itemView.findViewById(R.id.textExpense);
            expenseCheck = itemView.findViewById(R.id.textCheck);
            expenseDate = itemView.findViewById(R.id.textDate);
            btnDelete = itemView.findViewById(R.id.deleteExpense);
            final EditText editText = new EditText(context);

//            MaterialAlertDialogBuilder dialog = new
//                    MaterialAlertDialogBuilder(context)
//                    .setTitle("New map")
//                    .setView(editText)
//                    .setNegativeButton("Cancel", null)
//                    .setPositiveButton("Edit", (dialog1, which) -> {
//                        //...
//                    });
            btnDelete.setOnClickListener(v -> {
                notifyDataSetChanged();
                //body deleting
            });
        }
    }
}