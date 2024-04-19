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

import com.example.fhome.Adapter.Finance.IncomePortfolioAdapter;
import com.example.fhome.DBHelper.IncomeDBH;
import com.example.fhome.Model.IncomePortFolioItem;
import com.example.fhome.R;
import com.example.fhome.TabLayoutPortFolioActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IncomeActivity extends AppCompatActivity {
    Button btnSpending, btnEditIncomePortfolio, btnEnterIncome;
    ImageButton ibnCalender;
    EditText edtShowIncomeMoney;
    TextView tvShowDay;
    RecyclerView grListIncome;
    List<IncomePortFolioItem> incomePortFolioItemList;
    IncomeDBH incomeDBH;
    int parent = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        init();

        btnSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SpendingActivity.class);
                startActivity(intent);
            }
        });

        btnEditIncomePortfolio.setOnClickListener(new View.OnClickListener() {
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
                        IncomeActivity.this,
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

        incomeDBH = new IncomeDBH(this);
        grListIncome.setLayoutManager(new LinearLayoutManager(IncomeActivity.this, RecyclerView.HORIZONTAL,false));
        incomePortFolioItemList = new ArrayList<>();
        incomePortFolioItemList = incomeDBH.getAllincomePortFolios();
        IncomePortfolioAdapter adapter = new IncomePortfolioAdapter(incomePortFolioItemList, IncomeActivity.this, parent);
        grListIncome.setAdapter(adapter);

        btnEnterIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("SELECTED_ITEM_INCOME_PREF", Context.MODE_PRIVATE);
                String selectedItemText = sharedPreferences.getString("INCOME", "");
                if (tvShowDay.getText().equals("")){
                    Toast.makeText(IncomeActivity.this, "Ngày không được để trống", Toast.LENGTH_SHORT).show();
                } else if (edtShowIncomeMoney.getText().toString().equals("")) {
                    Toast.makeText(IncomeActivity.this, "Tiền không được rỗng", Toast.LENGTH_SHORT).show();
                } else if (selectedItemText.equals("")||selectedItemText.isEmpty()) {
                    Toast.makeText(IncomeActivity.this, "Phải chọn danh mục", Toast.LENGTH_SHORT).show();
                }else {
                    String money = edtShowIncomeMoney.getText().toString().trim();
                    String date = tvShowDay.getText().toString();
                    incomeDBH.insertIncome(selectedItemText, money, formatDateToSQLite(date));
                    Toast.makeText(IncomeActivity.this, "Tạo khoản thu nhập thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void init(){
        btnEnterIncome = findViewById(R.id.btnEnterIncome);
        btnSpending = findViewById(R.id.btnSpendingI);
        btnEditIncomePortfolio = findViewById(R.id.btnEditIncomePortfolio);
        edtShowIncomeMoney = findViewById(R.id.edtShowIncomeMoney);
        ibnCalender = findViewById(R.id.btnCalenderI);
        tvShowDay = findViewById(R.id.tvShowDayI);
        grListIncome = (RecyclerView) findViewById(R.id.grList_incomeShow);
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