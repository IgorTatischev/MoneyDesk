package com.example.moneydesk.ui.categoryfragment.income;

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
import com.example.moneydesk.ui.items.Category;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Objects;

public class CategoryIncomeAdapter extends RecyclerView.Adapter<CategoryIncomeAdapter.ViewHolder> {

    private List<Category> categoryList;
    private Context context;
    Toast msg;

    public CategoryIncomeAdapter(List<Category> category, Context context) {
        categoryList = category;
        this.context = context;
        msg = Toast.makeText(context,"",Toast.LENGTH_SHORT);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryName.setText(category.getName());
        holder.category = category;
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;
        public ImageButton btnDelete;
        Category category;
        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.textCategory);
            btnDelete = itemView.findViewById(R.id.deleteCategory);
            btnDelete.setOnClickListener(v -> {
                Log.d("category_id", String.valueOf(category.getID()));
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Удалить категорию")
                        .setMessage("Вместе с категорией удалятся все ёё записи, вы уверены?")
                        .setNegativeButton("Отмена", null)
                        .setPositiveButton("Удалить", (dialog1, which) -> {
                            Client client = new Client();
                            String data = client.delete_incomecategory(category.getID());
                            if (!Objects.equals(data, "false")) {
                                msg.setText("Категория успешно удалена!");
                                categoryList.remove(category); //удаляем из листа
                                notifyDataSetChanged(); //вызоваем обновление
                            }
                            else {
                                msg.setText("Не удалось удалить категорию!");
                            }
                            msg.show();
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }
    }
}