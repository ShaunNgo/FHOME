package com.example.fhome.Fragment;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fhome.Adapter.Finance.IncomeReportAdapter;
import com.example.fhome.Adapter.Finance.SpendingReportAdapter;
import com.example.fhome.DBHelper.IncomeDBH;
import com.example.fhome.DBHelper.SpendingDBH;
import com.example.fhome.Model.IncomeItem;
import com.example.fhome.Model.SpendingItem;
import com.example.fhome.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SpendingReportFragment extends Fragment {

    TextView tvDateStart, tvDateEnd;
    Button btnStatistic;
    RecyclerView rvSpending;

    List<SpendingItem> spendingItemList;
    SpendingDBH spendingDBH;
    SpendingReportAdapter spendingReportAdapter;
    public SpendingReportFragment() {
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
        return inflater.inflate(R.layout.fragment_spending_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        tvDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                tvDateStart.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                if (!tvDateEnd.getText().toString().isEmpty()) {
                                    checkAndUpdateEndDate();
                                }
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        tvDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                tvDateEnd.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                checkAndUpdateEndDate();
                            }
                        },
                        year, month, day);
                datePickerDialog.show();

            }
        });

        rvSpending.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        spendingItemList = new ArrayList<>();
        spendingDBH = new SpendingDBH(getContext());
        spendingItemList = spendingDBH.getAllSpendingItems();
        spendingReportAdapter = new SpendingReportAdapter(spendingItemList, getContext());
        rvSpending.setAdapter(spendingReportAdapter);

        btnStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = tvDateStart.getText().toString();
                String end = tvDateEnd.getText().toString();
                if (start.equals("Ngày bắt đầu")){
                    Toast.makeText(getContext(), "Ngày bắt đầu không được rỗng", Toast.LENGTH_SHORT).show();
                } else if (end=="Ngày kết thúc") {
                    Toast.makeText(getContext(), "Ngày kết thúc không được rỗng", Toast.LENGTH_SHORT).show();
                }else {
                    spendingItemList = new ArrayList<>();
                    spendingDBH = new SpendingDBH(getContext());
                    spendingItemList = spendingDBH.getSpendItemsBetweenDates(formatDateToSQLite(start), formatDateToSQLite(end));
                    spendingReportAdapter = new SpendingReportAdapter(spendingItemList, getContext());
                    rvSpending.setAdapter(spendingReportAdapter);
                }
            }
        });
    }

    void init(View view){
        tvDateStart = view.findViewById(R.id.tvShowDayStart);
        tvDateEnd = view.findViewById(R.id.tvShowDayEnd);
        btnStatistic = view.findViewById(R.id.btnStatistic);
        rvSpending = view.findViewById(R.id.rvSpendingReport);
    }

    void checkAndUpdateEndDate() {
        String startDateStr = tvDateStart.getText().toString();
        String endDateStr = tvDateEnd.getText().toString();
        if (!startDateStr.isEmpty() && !endDateStr.isEmpty()) {
            // Parse the dates to compare
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                Date startDate = sdf.parse(startDateStr);
                Date endDate = sdf.parse(endDateStr);
                // Compare the dates
                if (endDate.before(startDate)) {
                    Toast.makeText(getContext(), "Ngày kết thúc phải lớn hơn ngày bắt đầu", Toast.LENGTH_SHORT).show();
                    tvDateEnd.setText("Ngày kết thúc");
                    tvDateStart.setText("Ngày bắt đầu");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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