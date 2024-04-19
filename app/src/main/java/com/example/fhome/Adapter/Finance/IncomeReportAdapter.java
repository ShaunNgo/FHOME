package com.example.fhome.Adapter.Finance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.fhome.Model.IncomeItem;
import com.example.fhome.R;

import java.util.List;

public class IncomeReportAdapter extends RecyclerView.Adapter<IncomeReportAdapter.IncomeReportVH>{

    List<IncomeItem> incomeItemList;
    Context context;

    public IncomeReportAdapter(List<IncomeItem> incomeItemList, Context context) {
        this.incomeItemList = incomeItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public IncomeReportVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incomereport, parent, false);
        return new IncomeReportAdapter.IncomeReportVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeReportVH holder, int position) {
        IncomeItem incomeItem = incomeItemList.get(position);
        holder.name.setText(incomeItem.getIncomeName());
        holder.money.setText(incomeItem.getMoney());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                IncomeDBH incomeDBH = new IncomeDBH(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa thu nhập");
                builder.setMessage("Bạn muốn xóa thu nhập này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        incomeDBH.deleteIncome(incomeItem.getId());
                        incomeItemList.remove(incomeItem);
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
    }

    @Override
    public int getItemCount() {
        return incomeItemList.size();
    }

    public class IncomeReportVH extends RecyclerView.ViewHolder{
        TextView name, money;

        public IncomeReportVH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_tvNameIncomePortfolio);
            money = itemView.findViewById(R.id.item_moneyIncome);

        }
    }
}
