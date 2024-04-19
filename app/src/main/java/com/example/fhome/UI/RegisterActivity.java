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

public class RegisterActivity extends AppCompatActivity {

    EditText edtUserName, edtPassword, edtRePassword;
    Button btnRegister, btnSignIn;
    UserDBH DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }
    public void init(){
        edtUserName = (EditText) findViewById(R.id.register_userName);
        edtPassword = (EditText) findViewById(R.id.register_password);
        edtRePassword = (EditText) findViewById(R.id.register_rePassword);
        btnSignIn = (Button) findViewById(R.id.register_signIn);
        btnRegister = (Button) findViewById(R.id.register_register);
        DB = new UserDBH(this);
    }

    public void register_btnRegister_onClick(View view){
        String user = edtUserName.getText().toString();
        String pass = edtPassword.getText().toString();
        String repass = edtRePassword.getText().toString();

        if(user.equals("")||pass.equals("")||repass.equals(""))
            Toast.makeText(RegisterActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
        else{
            if(pass.equals(repass)){
                Boolean checkUser = DB.checkUserName(user);
                if(checkUser==false){
                    Boolean insert = DB.insertData(user, pass);
                    if(insert==true){
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("SAVED_USERNAME_KEY", user);
                        editor.apply();
                        startActivity(i);
                    }else{
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Tài khoản đã được đăng ký, vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void register_btnSignIn_onClick(View view){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
}