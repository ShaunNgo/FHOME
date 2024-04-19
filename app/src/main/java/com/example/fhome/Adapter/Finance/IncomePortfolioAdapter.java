package com.example.fhome.Adapter.Finance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fhome.DBHelper.IncomeDBH;
import com.example.fhome.DBHelper.SpendingDBH;
import com.example.fhome.Model.IncomePortFolioItem;
import com.example.fhome.Model.SpendingPortFolioItem;
import com.example.fhome.R;

import java.util.List;

public class IncomePortfolioAdapter extends RecyclerView.Adapter<IncomePortfolioAdapter.IncomePortfolioVH>{

    private List<IncomePortFolioItem> incomePortFolioItemList;
    Context context;
    private int isParent;
    private int selectedItemPosition = RecyclerView.NO_POSITION;
    private int defaultItemColor;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "SELECTED_ITEM_INCOME_PREF";
    private static final String KEY_SELECTED_ITEM_TEXT = "INCOME";

    public IncomePortfolioAdapter(List<IncomePortFolioItem> incomePortFolioItemList, Context context, int isParent) {
        this.incomePortFolioItemList = incomePortFolioItemList;
        this.context = context;
        this.isParent = isParent;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public IncomePortfolioVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incomeportfolio, parent, false);
        return new IncomePortfolioAdapter.IncomePortfolioVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomePortfolioVH holder, int position) {
        IncomePortFolioItem incomePortFolioItem = incomePortFolioItemList.get(position);
        holder.item_tvIncome.setText(incomePortFolioItem.getIncomePortFolioName());

        if (isParent==0){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    IncomeDBH incomeDBH = new IncomeDBH(context);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xóa danh mục");
                    builder.setMessage("Bạn muốn xóa danh mục " + incomePortFolioItem.getIncomePortFolioName() + " ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            incomeDBH.deleteincomePortFolio(incomePortFolioItem.getId());
                            incomePortFolioItemList.remove(incomePortFolioItem);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                    return true;
                }
            });
        }else {
            String selectedText = incomePortFolioItem.getIncomePortFolioName();
            if (selectedText.equals(getSelectedItemText())) {
                holder.bg.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
                holder.item_tvIncome.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
            else if (selectedItemPosition == position) {
                holder.bg.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
                holder.item_tvIncome.setTextColor(ContextCompat.getColor(context,R.color.white));
            } else {
                holder.bg.setBackgroundColor(defaultItemColor);
                holder.item_tvIncome.setTextColor(ContextCompat.getColor(context,R.color.black));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int previouslySelected = selectedItemPosition;
                    String selectedText = incomePortFolioItem.getIncomePortFolioName();

                    if (selectedItemPosition == position) {
                        selectedItemPosition = RecyclerView.NO_POSITION;
                        removeSelectedItemFromSharedPreferences();
                    } else {
                        selectedItemPosition = position;
                        saveSelectedItemText(selectedText);
                    }

                    notifyItemChanged(position);
                    if (previouslySelected != RecyclerView.NO_POSITION) {
                        notifyItemChanged(previouslySelected);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return incomePortFolioItemList.size();
    }

    public class IncomePortfolioVH extends RecyclerView.ViewHolder{
        TextView item_tvIncome;
        ConstraintLayout bg;

        public IncomePortfolioVH(@NonNull View itemView) {
            super(itemView);

            item_tvIncome = itemView.findViewById(R.id.item_tvIncome);
            bg = itemView.findViewById(R.id.bg);

            defaultItemColor = ContextCompat.getColor(context, R.color.white);
        }
    }

    private void saveSelectedItemText(String text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SELECTED_ITEM_TEXT, text);
        editor.apply();
    }

    public String getSelectedItemText() {
        return sharedPreferences.getString(KEY_SELECTED_ITEM_TEXT, "");
    }

    private void removeSelectedItemFromSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_SELECTED_ITEM_TEXT);
        editor.apply();
    }
}
