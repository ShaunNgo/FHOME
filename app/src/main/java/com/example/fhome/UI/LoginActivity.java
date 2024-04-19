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

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName, edtPassword;
    Button btnLogin, btnRegister;
    UserDBH DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    public void init(){
        edtUserName = (EditText) findViewById(R.id.login_userName);
        edtPassword = (EditText) findViewById(R.id.login_passWord);
        btnLogin = (Button) findViewById(R.id.login_signIn);
        btnRegister = (Button) findViewById(R.id.login_register);
        DB = new UserDBH(this);
    }
    public void login_btnLogin_onClick(View view){
        String user = edtUserName.getText().toString();
        String pass = edtPassword.getText().toString();

        if(user.equals("")||pass.equals(""))
            Toast.makeText(LoginActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
        else{
            Boolean checkUserPass = DB.checkUserNamePassword(user, pass);
            if(checkUserPass==true){
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("SAVED_USERNAME_KEY", user);
                editor.apply();

                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void login_btnRegister_onClick(View view){
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }
}