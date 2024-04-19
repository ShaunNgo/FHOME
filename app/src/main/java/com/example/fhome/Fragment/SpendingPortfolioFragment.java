package com.example.fhome.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fhome.Adapter.Finance.SpendingPortfolioAdapter;
import com.example.fhome.DBHelper.SpendingDBH;
import com.example.fhome.Model.SpendingPortFolioItem;
import com.example.fhome.R;
import com.example.fhome.UI.IncomePortfolioActivity;
import com.example.fhome.UI.SpendingPortfolioActivity;

import java.util.ArrayList;
import java.util.List;

public class SpendingPortfolioFragment extends Fragment {

    Button btnIncome, btnAddSpendingPortfolio;
    EditText edtNameSpending;
    RecyclerView grListSpendingEdit;
    List<SpendingPortFolioItem> spendingPortFolioItemList;
    int parent=0;
    public SpendingPortfolioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spending_portfolio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        SpendingDBH spendingDBH = new SpendingDBH(getContext());
        grListSpendingEdit.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        spendingPortFolioItemList = new ArrayList<>();
        spendingPortFolioItemList = spendingDBH.getAllSpendingPortFolios();
        SpendingPortfolioAdapter spendingPortfolioAdapter = new SpendingPortfolioAdapter(spendingPortFolioItemList, getContext(), parent);
        grListSpendingEdit.setAdapter(spendingPortfolioAdapter);


        btnAddSpendingPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtNameSpending.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(getContext(), "tên danh mục không được để trổng", Toast.LENGTH_SHORT).show();
                }else {
                    SpendingDBH spendingDBH = new SpendingDBH(getContext());
                    spendingDBH.insertSpendingPortFolio(name);
                    spendingPortFolioItemList = new ArrayList<>();
                    spendingPortFolioItemList = spendingDBH.getAllSpendingPortFolios();
                    SpendingPortfolioAdapter spendingPortfolioAdapter = new SpendingPortfolioAdapter(spendingPortFolioItemList, getContext(), parent);
                    grListSpendingEdit.setAdapter(spendingPortfolioAdapter);
                    edtNameSpending.setText("");
                }
            }
        });
    }

    public void init(View view){
        btnAddSpendingPortfolio = view.findViewById(R.id.btnAddSpendingPortfolio);
        edtNameSpending = view.findViewById(R.id.edtNameSpending);
        grListSpendingEdit = view.findViewById(R.id.grList_spendingEdit);
    }
}