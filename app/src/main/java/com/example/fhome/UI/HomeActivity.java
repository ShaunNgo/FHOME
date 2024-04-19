package com.example.fhome.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.fhome.Adapter.Blog.BlogAdapter;
import com.example.fhome.DBHelper.UserDBH;
import com.example.fhome.Model.BlogItem;
import com.example.fhome.R;
import com.example.fhome.TabLayoutReportActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    ImageButton vFinance, vBolg, vAccount, vReport;
    RecyclerView rvBlog;
    private BlogAdapter blogAdapter;
    private List<BlogItem> blogItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        blogItemList = new ArrayList<>();
        UserDBH dbHelper = new UserDBH(this);
        blogItemList = dbHelper.getAllBlogs();
        blogAdapter = new BlogAdapter(blogItemList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rvBlog.setLayoutManager(layoutManager);
        rvBlog.setAdapter(blogAdapter);
        blogAdapter.notifyDataSetChanged();

        vBolg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateBlogActivity.class);
                startActivity(intent);
            }
        });

        vFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SpendingActivity.class);
                startActivity(intent);
            }
        });

        vReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TabLayoutReportActivity.class);
                startActivity(intent);
            }
        });

        vAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),AccountActivity.class);
                startActivity(intent2);
            }
        });
    }
    public void init(){
        vFinance = findViewById(R.id.home_finance);
        vBolg = findViewById(R.id.home_blog);
        vAccount = findViewById(R.id.home_account);
        vReport = findViewById(R.id.home_report);
        rvBlog = (RecyclerView) findViewById(R.id.rvBlog);
    }


    @Override
    protected void onResume() {
        super.onResume();
        blogAdapter.notifyDataSetChanged();
    }
}