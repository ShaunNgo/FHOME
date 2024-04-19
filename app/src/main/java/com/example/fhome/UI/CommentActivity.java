package com.example.fhome.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fhome.Adapter.Blog.CommentAdapter;
import com.example.fhome.DBHelper.UserDBH;
import com.example.fhome.Model.Comment;
import com.example.fhome.R;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    EditText edtComment;
    ImageButton btnAddComment;
    RecyclerView rvComment;
    String title, userID, blogID;
    TextView titleComment;
    CommentAdapter commentAdapter;
    List<Comment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        edtComment = findViewById(R.id.edtComment);
        btnAddComment = findViewById(R.id.btnAddComment);
        titleComment = findViewById(R.id.blogTitleComment);

        title = getIntent().getStringExtra("title");
        titleComment.setText(title);
        userID = getIntent().getStringExtra("userID");
        blogID = getIntent().getStringExtra("blogID");


        rvComment = findViewById(R.id.rvComment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rvComment.setLayoutManager(layoutManager);
        commentList = new ArrayList<>();
        UserDBH dbHelper = new UserDBH(this);
        commentList = dbHelper.showComments(Integer.parseInt(blogID));
        commentAdapter = new CommentAdapter(commentList, CommentActivity.this);
        rvComment.setAdapter(commentAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userIDComment = sharedPreferences.getString("SAVED_USERNAME_KEY","");

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentContent = edtComment.getText().toString();

                if (!commentContent.isEmpty() && commentContent!=""){
                    dbHelper.addComment(userIDComment, Integer.parseInt(blogID), commentContent);
                    commentList = new ArrayList<>();
                    commentList = dbHelper.showComments(Integer.parseInt(blogID));
                    commentAdapter = new CommentAdapter(commentList, CommentActivity.this);
                    rvComment.setAdapter(commentAdapter);

                    edtComment.setText("");
                }else {
                    Toast.makeText(CommentActivity.this, "Không thể comment nội dung trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}