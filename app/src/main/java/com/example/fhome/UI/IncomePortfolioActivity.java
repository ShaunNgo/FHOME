package com.example.fhome.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.fhome.Adapter.Finance.IncomePortfolioAdapter;
import com.example.fhome.Adapter.Finance.SpendingPortfolioAdapter;
import com.example.fhome.DBHelper.IncomeDBH;
import com.example.fhome.DBHelper.SpendingDBH;
import com.example.fhome.Model.IncomePortFolioItem;
import com.example.fhome.Model.SpendingPortFolioItem;
import com.example.fhome.R;

import java.util.ArrayList;
import java.util.List;

public class IncomePortfolioActivity extends AppCompatActivity {
    Button btnSpending, btnAddIncomePortfolio;
    EditText edtNameIncome;
    RecyclerView grListIncomeEdit;
    List<IncomePortFolioItem> incomePortFolioItemList;
    int parent = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_portfolio);
        init();

        btnSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SpendingPortfolioActivity.class);
                startActivity(intent);
                finish();
            }
        });

        IncomeDBH incomeDBH = new IncomeDBH(IncomePortfolioActivity.this);
        grListIncomeEdit.setLayoutManager(new LinearLayoutManager(IncomePortfolioActivity.this, LinearLayoutManager.HORIZONTAL, false));
        incomePortFolioItemList = new ArrayList<>();
        incomePortFolioItemList = incomeDBH.getAllincomePortFolios();
        IncomePortfolioAdapter incomePortfolioAdapter = new IncomePortfolioAdapter(incomePortFolioItemList, IncomePortfolioActivity.this, parent);
        grListIncomeEdit.setAdapter(incomePortfolioAdapter);


        btnAddIncomePortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtNameIncome.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(IncomePortfolioActivity.this, "tên danh mục không được để trổng", Toast.LENGTH_SHORT).show();
                }else {
                    IncomeDBH incomeDBH = new IncomeDBH(IncomePortfolioActivity.this);
                    incomeDBH.insertincomePortFolio(name);
                    incomePortFolioItemList = new ArrayList<>();
                    incomePortFolioItemList = incomeDBH.getAllincomePortFolios();
                    IncomePortfolioAdapter incomePortfolioAdapter = new IncomePortfolioAdapter(incomePortFolioItemList, IncomePortfolioActivity.this, 0);
                    grListIncomeEdit.setAdapter(incomePortfolioAdapter);
                    edtNameIncome.setText("");
                }
            }
        });
    }
    public void init(){
        btnSpending = findViewById(R.id.btnSpendingPortfolioI);
        btnAddIncomePortfolio = findViewById(R.id.btnAddIncomePortfolio);
        edtNameIncome = findViewById(R.id.edtNameIncome);
        grListIncomeEdit = findViewById(R.id.grList_incomeEdit);
    }
}