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

import com.example.fhome.Adapter.Finance.SpendingPortfolioAdapter;
import com.example.fhome.DBHelper.SpendingDBH;
import com.example.fhome.Model.SpendingPortFolioItem;
import com.example.fhome.R;

import java.util.ArrayList;
import java.util.List;

public class SpendingPortfolioActivity extends AppCompatActivity {
    Button btnIncome, btnAddSpendingPortfolio;
    EditText edtNameSpending;
    RecyclerView grListSpendingEdit;
    List<SpendingPortFolioItem> spendingPortFolioItemList;
    int parent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_portfolio);
        init();

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncomePortfolioActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SpendingDBH spendingDBH = new SpendingDBH(SpendingPortfolioActivity.this);
        grListSpendingEdit.setLayoutManager(new LinearLayoutManager(SpendingPortfolioActivity.this, LinearLayoutManager.HORIZONTAL, false));
        spendingPortFolioItemList = new ArrayList<>();
        spendingPortFolioItemList = spendingDBH.getAllSpendingPortFolios();
        SpendingPortfolioAdapter spendingPortfolioAdapter = new SpendingPortfolioAdapter(spendingPortFolioItemList, SpendingPortfolioActivity.this, parent);
        grListSpendingEdit.setAdapter(spendingPortfolioAdapter);


        btnAddSpendingPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtNameSpending.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(SpendingPortfolioActivity.this, "tên danh mục không được để trổng", Toast.LENGTH_SHORT).show();
                }else {
                    SpendingDBH spendingDBH = new SpendingDBH(SpendingPortfolioActivity.this);
                    spendingDBH.insertSpendingPortFolio(name);
                    spendingPortFolioItemList = new ArrayList<>();
                    spendingPortFolioItemList = spendingDBH.getAllSpendingPortFolios();
                    SpendingPortfolioAdapter spendingPortfolioAdapter = new SpendingPortfolioAdapter(spendingPortFolioItemList, SpendingPortfolioActivity.this, parent);
                    grListSpendingEdit.setAdapter(spendingPortfolioAdapter);
                    edtNameSpending.setText("");
                }
            }
        });
    }
    public void init(){
        btnIncome = findViewById(R.id.btnIncomePortfolioS);
        btnAddSpendingPortfolio = findViewById(R.id.btnAddSpendingPortfolio);
        edtNameSpending = findViewById(R.id.edtNameSpending);
        grListSpendingEdit = findViewById(R.id.grList_spendingEdit);
    }
}