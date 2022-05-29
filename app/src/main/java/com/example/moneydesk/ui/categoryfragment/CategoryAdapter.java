package com.example.moneydesk.ui.categoryfragment;

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

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Category> categoryList;
    public CategoryAdapter(List<Category> category, Context context) {
        categoryList = category;
        this.context = context;
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
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.checkName.setText(category.getName());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView checkName;
        public ImageButton btnDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            checkName = itemView.findViewById(R.id.textCategory);
            btnDelete = itemView.findViewById(R.id.deleteCategory);
            final EditText editText = new EditText(context);

//            MaterialAlertDialogBuilder dialog = new
//                    MaterialAlertDialogBuilder(context)
//                    .setTitle("")
//                    .setView(editText)
//                    .setNegativeButton("Cancel", null)
//                    .setPositiveButton("", (dialog1, which) -> {
//                        //...
//                    });

            btnDelete.setOnClickListener(v -> {
                notifyDataSetChanged();
                //body deleting
            });
        }
    }
}