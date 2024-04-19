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

import com.example.fhome.Adapter.Finance.IncomePortfolioAdapter;
import com.example.fhome.DBHelper.IncomeDBH;
import com.example.fhome.Model.IncomePortFolioItem;
import com.example.fhome.R;
import com.example.fhome.UI.IncomePortfolioActivity;
import com.example.fhome.UI.SpendingPortfolioActivity;

import java.util.ArrayList;
import java.util.List;


public class IncomePortfolioFragment extends Fragment {

    Button btnSpending, btnAddIncomePortfolio;
    EditText edtNameIncome;
    RecyclerView grListIncomeEdit;
    List<IncomePortFolioItem> incomePortFolioItemList;

    public IncomePortfolioFragment() {
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
        return inflater.inflate(R.layout.fragment_income_portfolio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        IncomeDBH incomeDBH = new IncomeDBH(getContext());
        grListIncomeEdit.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        incomePortFolioItemList = new ArrayList<>();
        incomePortFolioItemList = incomeDBH.getAllincomePortFolios();
        IncomePortfolioAdapter incomePortfolioAdapter = new IncomePortfolioAdapter(incomePortFolioItemList, getContext(), 0);
        grListIncomeEdit.setAdapter(incomePortfolioAdapter);


        btnAddIncomePortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtNameIncome.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(getContext(), "tên danh mục không được để trổng", Toast.LENGTH_SHORT).show();
                }else {
                    IncomeDBH incomeDBH = new IncomeDBH(getContext());
                    incomeDBH.insertincomePortFolio(name);
                    incomePortFolioItemList = new ArrayList<>();
                    incomePortFolioItemList = incomeDBH.getAllincomePortFolios();
                    IncomePortfolioAdapter incomePortfolioAdapter = new IncomePortfolioAdapter(incomePortFolioItemList, getContext(), 0);
                    grListIncomeEdit.setAdapter(incomePortfolioAdapter);
                    edtNameIncome.setText("");
                }
            }
        });

    }

    public void init(View view){
        btnAddIncomePortfolio = view.findViewById(R.id.btnAddIncomePortfolio);
        edtNameIncome = view.findViewById(R.id.edtNameIncome);
        grListIncomeEdit = view.findViewById(R.id.grList_incomeEdit);
    }
}