package com.example.fhome.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fhome.Adapter.Finance.SpendingPortfolioAdapter;
import com.example.fhome.DBHelper.SpendingDBH;
import com.example.fhome.Model.SpendingPortFolioItem;
import com.example.fhome.R;
import com.example.fhome.TabLayoutPortFolioActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SpendingActivity extends AppCompatActivity {
    Button  btnIncome, btnEditSpendingPortfolio, btnEnterSpending;
    ImageButton ibnCalender;
    EditText edtShowSpendingMoney;
    TextView tvShowDay;
    RecyclerView grListSpending;
    List<SpendingPortFolioItem> spendingPortFolioItemList;
    SpendingDBH spendingDBH;
    int parent = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);
        init();

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncomeActivity.class);
                startActivity(intent);
            }
        });

        btnEditSpendingPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TabLayoutPortFolioActivity.class);
                startActivity(intent);
            }
        });

        tvShowDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        SpendingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                tvShowDay.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
        spendingDBH = new SpendingDBH(this);
        grListSpending.setLayoutManager(new LinearLayoutManager(SpendingActivity.this, RecyclerView.HORIZONTAL,false));
        spendingPortFolioItemList = new ArrayList<>();
        spendingPortFolioItemList = spendingDBH.getAllSpendingPortFolios();
        SpendingPortfolioAdapter adapter = new SpendingPortfolioAdapter(spendingPortFolioItemList, SpendingActivity.this, parent);
        grListSpending.setAdapter(adapter);


        btnEnterSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("SELECTED_ITEM_SPENDING_PREF", Context.MODE_PRIVATE);
                String selectedItemText = sharedPreferences.getString("SPENDING", "");
                if (tvShowDay.getText().equals("")){
                    Toast.makeText(SpendingActivity.this, "Ngày không được để trống", Toast.LENGTH_SHORT).show();
                } else if (edtShowSpendingMoney.getText().toString().equals("")) {
                    Toast.makeText(SpendingActivity.this, "Tiền không được rỗng", Toast.LENGTH_SHORT).show();
                } else if (selectedItemText.equals("")||selectedItemText.isEmpty()) {
                    Toast.makeText(SpendingActivity.this, "Phải chọn danh mục", Toast.LENGTH_SHORT).show();
                }else {
                    String money = edtShowSpendingMoney.getText().toString().trim();
                    String date = tvShowDay.getText().toString();
                    spendingDBH.insertSpending(selectedItemText, money, formatDateToSQLite(date));
                    Toast.makeText(SpendingActivity.this, "Tạo khoản chi tiêu thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        spendingDBH = new SpendingDBH(this);
        spendingPortFolioItemList = new ArrayList<>();
        spendingPortFolioItemList = spendingDBH.getAllSpendingPortFolios();
        SpendingPortfolioAdapter adapter = new SpendingPortfolioAdapter(spendingPortFolioItemList, SpendingActivity.this, parent);
        grListSpending.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void init(){
        btnIncome = findViewById(R.id.btnIncomeS);
        btnEnterSpending = findViewById(R.id.btnEnterSpending);
        btnEditSpendingPortfolio = findViewById(R.id.btnEditSpendingPortfolioS);
        edtShowSpendingMoney = findViewById(R.id.edtShowSpendingMoney);
        ibnCalender = findViewById(R.id.btnCalenderS);
        tvShowDay = findViewById(R.id.tvShowDayS);
        grListSpending = (RecyclerView) findViewById(R.id.grList_spendingShow);
    }

    private String formatDateToSQLite(String dateToShow) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = sdf.parse(dateToShow);
            SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return dbDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // Return empty string or handle the exception as needed.
        }
    }
}