package com.example.moneydesk.ui.mainfragments.income;

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

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {

    private Context context;
    private List<Operation> incomeList;
    public IncomeAdapter(List<Operation> operations, Context context) {
        incomeList = operations;
        this.context = context;
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
        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.textCategory);
            incomeAmount = itemView.findViewById(R.id.textIncome);
            incomeCheck = itemView.findViewById(R.id.textCheck);
            incomeDate = itemView.findViewById(R.id.textDate);
            btnDelete = itemView.findViewById(R.id.deleteIncome);
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