package com.example.fhome.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fhome.DBHelper.UserDBH;
import com.example.fhome.R;
import com.example.fhome.TabLayoutReportActivity;

public class AccountActivity extends AppCompatActivity {
    TextView tvAccount_name;
    EditText medt_oldPassword, medt_newPassword;
    Button mbtn_changePassword, btnlogOut;
    UserDBH userDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tvAccount_name = (TextView) findViewById(R.id.tv_account_name);
        medt_oldPassword = (EditText)findViewById(R.id.edt_oldPassword);
        medt_newPassword = (EditText) findViewById(R.id.edt_newPassword);
        mbtn_changePassword = (Button) findViewById(R.id.btn_changePassword);
        btnlogOut = (Button) findViewById(R.id.btn_logOut);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userID = sharedPreferences.getString("SAVED_USERNAME_KEY","");

        userDBH = new UserDBH(this);
        tvAccount_name.setText(userID);

        mbtn_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = medt_oldPassword.getText().toString();
                String newPassword = medt_newPassword.getText().toString();

                Boolean changePassword1 = userDBH.changePassword(userID,oldPassword,newPassword);

                if(changePassword1){
                    Toast.makeText(AccountActivity.this,"Mật khẩu đã được thay đổi",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AccountActivity.this,"[Error]Đổi mật khẩu không thành công[Error]",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}