package com.example.moneydesk.ui.checkfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moneydesk.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.List;

public class ChecksAdapter extends RecyclerView.Adapter<ChecksAdapter.ViewHolder> {

    private Context context;
    private List<Check> chList;
    public ChecksAdapter(List<Check> checks, Context context) {
        chList = checks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.cheks_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Check check = chList.get(position);
        holder.checkName.setText(check.getName());
        holder.checkAmount.setText(check.getAmount().toString());
    }

    @Override
    public int getItemCount() {
        return chList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView checkName;
        public  TextView checkAmount;
        public Button btnDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            checkName = itemView.findViewById(R.id.nameCheck);
            checkAmount = itemView.findViewById(R.id.textAmount);
            btnDelete = itemView.findViewById(R.id.buttonDelete);

            final EditText editText = new EditText(context);
            MaterialAlertDialogBuilder dialog = new
                    MaterialAlertDialogBuilder(context)
                    .setTitle("New map")
                    .setView(editText)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Edit", (dialog1, which) -> {
                        //...
                    });


            btnDelete.setOnClickListener(v -> {
                notifyDataSetChanged();
                //body deleting
            });
        }
    }
}