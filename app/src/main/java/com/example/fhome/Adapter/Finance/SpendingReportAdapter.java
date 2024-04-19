package com.example.fhome.Adapter.Finance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fhome.DBHelper.IncomeDBH;
import com.example.fhome.DBHelper.SpendingDBH;
import com.example.fhome.Model.IncomeItem;
import com.example.fhome.Model.SpendingItem;
import com.example.fhome.R;

import java.util.List;

public class SpendingReportAdapter extends RecyclerView.Adapter<SpendingReportAdapter.SpendingReportVH>{

    List<SpendingItem> spendingItemList;
    Context context;

    public SpendingReportAdapter(List<SpendingItem> spendingItemList, Context context) {
        this.spendingItemList = spendingItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SpendingReportVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spendingreport, parent, false);
        return new SpendingReportAdapter.SpendingReportVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpendingReportVH holder, int position) {
        SpendingItem spendingItem = spendingItemList.get(position);
        holder.name.setText(spendingItem.getSpendName());
        holder.money.setText(spendingItem.getMoney());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SpendingDBH spendingDBH = new SpendingDBH(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa khoản chi");
                builder.setMessage("Bạn muốn xóa khoản chi này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spendingDBH.deleteSpending(spendingItem.getId());
                        spendingItemList.remove(spendingItem);
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
        return spendingItemList.size();
    }

    public class SpendingReportVH extends RecyclerView.ViewHolder{
        TextView name, money;

        public SpendingReportVH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_tvNameSpendingPortfolio);
            money = itemView.findViewById(R.id.textView);

        }
    }
}
