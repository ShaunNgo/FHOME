package com.example.fhome.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fhome.DBHelper.UserDBH;
import com.example.fhome.R;

public class CreateBlogActivity extends AppCompatActivity {

    private EditText edtBlogContent, edtBlogTitle;
    private Button btnSubmitBlog;
    private UserDBH userDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_blog);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userID = sharedPreferences.getString("SAVED_USERNAME_KEY", "");

        userDBH = new UserDBH(this);
        edtBlogContent = findViewById(R.id.etBlogContent);
        edtBlogTitle = findViewById(R.id.etBlogTitle);
        btnSubmitBlog = findViewById(R.id.btnSubmitBlog);

        btnSubmitBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blogContent = edtBlogContent.getText().toString().trim();
                String blogTitle = edtBlogTitle.getText().toString().trim();
                if (blogContent.isEmpty()) {
                    Toast.makeText(CreateBlogActivity.this, "Điền đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (blogTitle.isEmpty()) {
                    Toast.makeText(CreateBlogActivity.this, "Điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    insertBlog(blogContent, blogTitle, userID);
                }
            }
        });
    }
    private void insertBlog(String blogContent, String blogTitle, String userID) {

        UserDBH dbHelper = new UserDBH(CreateBlogActivity.this);
        long result = dbHelper.insertBlog(blogContent, userID, blogTitle);
        if (result != -1) {
            Toast.makeText(this, "Tạo blog thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Tạo blog thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}