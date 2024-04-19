package com.example.fhome.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fhome.DBHelper.UserDBH;
import com.example.fhome.Model.BlogItem;
import com.example.fhome.R;

public class EditBlogActivity extends AppCompatActivity {

    private EditText edtBlogContent, edtBlogTitle;
    private Button btnSubmitBlog;
    private UserDBH dbHelper;

    private String blogID;

    private BlogItem blogItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blog);

        blogID = getIntent().getStringExtra("blogID");

        edtBlogContent = findViewById(R.id.etBlogContent);
        edtBlogTitle = findViewById(R.id.etBlogTitle);
        btnSubmitBlog = findViewById(R.id.btnSubmitBlog);

        dbHelper = new UserDBH(EditBlogActivity.this);
        blogItem = new BlogItem();
        blogItem = dbHelper.getBlogById(Integer.parseInt(blogID));
        edtBlogContent.setText(blogItem.getContent());
        edtBlogTitle.setText(blogItem.getTitle());

        btnSubmitBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blogContent = edtBlogContent.getText().toString().trim();
                String blogTitle = edtBlogTitle.getText().toString().trim();
                if (blogContent.isEmpty()) {
                    Toast.makeText(EditBlogActivity.this, "Điền đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (blogTitle.isEmpty()) {
                    Toast.makeText(EditBlogActivity.this, "Điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    updateBlog(blogContent, blogTitle);
                }
            }
        });
    }

    private void updateBlog(String blogContent, String blogTitle) {
        dbHelper = new UserDBH(EditBlogActivity.this);
        long result = dbHelper.updateBlog(Integer.parseInt(blogID), blogContent, blogTitle);
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