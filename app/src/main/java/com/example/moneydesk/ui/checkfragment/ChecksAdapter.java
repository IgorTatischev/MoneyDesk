package com.example.moneydesk.ui.checkfragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneydesk.Client;
import com.example.moneydesk.R;
import com.example.moneydesk.ui.items.Check;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Objects;

public class ChecksAdapter extends RecyclerView.Adapter<ChecksAdapter.ViewHolder> {

    private List<Check> chList;
    private Context context;
    Toast msg;

    public ChecksAdapter(List<Check> checks, Context context) {
        chList = checks;
        this.context = context;
        msg = Toast.makeText(context,"",Toast.LENGTH_SHORT);
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
        holder.check = check;
    }

    @Override
    public int getItemCount() {
        return chList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView checkName;
        public  TextView checkAmount;
        public Button btnDelete;
        Check check;
        public ViewHolder(View itemView) {
            super(itemView);
            checkName = itemView.findViewById(R.id.nameCheck);
            checkAmount = itemView.findViewById(R.id.textAmount);
            btnDelete = itemView.findViewById(R.id.deleteCheck);
            btnDelete.setOnClickListener(v -> {
                Log.d("check_id", String.valueOf(check.getID()));
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Удалить счёт")
                        .setMessage("Вместе со счётом удалятся все его записи, вы уверены?")
                        .setNegativeButton("Отмена", null)
                        .setPositiveButton("Удалить", (dialog1, which) -> {
                            Client client = new Client();
                            String data = client.delete_check(check.getID());
                            if (!Objects.equals(data, "false")) {
                                msg.setText("Счёт успешно удалён!");
                                chList.remove(check); //удаляем из листа
                                notifyDataSetChanged(); //вызоваем обновление
                            }
                            else {
                                msg.setText("Не удалось удалить счёт!");
                            }
                            msg.show();
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }
    }
}