package com.example.competition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "RegisterActivity";
    String username;
    String password1;
    String password2;
    EditText tvUsername;
    EditText evPassword1;
    EditText evPassword2;
    int flag ;
    private final String  FLAG_SP_KEY = "tb_num";
    List<String> usernameList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvUsername = findViewById(R.id.register_username);
        evPassword1 = findViewById(R.id.register_password1);
        evPassword2 = findViewById(R.id.register_password2);

        UserManager userManager = new UserManager(RegisterActivity.this);
        for (UserItem item:userManager.listAll()){
            usernameList.add(item.getUsername());
            Log.i(TAG, "run: 已从数据库中获得name"+item.getUsername());
            Log.i(TAG, "run: 已从数据库中获得数据");
        }
    }

    public void openLogin(View btn){
        Log.i(TAG, "openOne:open ");

        username = String.valueOf(tvUsername.getText());
        password1 = String.valueOf(evPassword1.getText());
        password2 = String.valueOf(evPassword2.getText());

        Log.i(TAG, "openLogin: username："+username);

        //检验是否注册成功
        if (username!=null && password1.equals(password2) && usernameList.indexOf(username)==-1 && password1.length()>=6){
             UserManager userManager = new UserManager(RegisterActivity.this);

            //获取数据库名称
            SharedPreferences sp = getSharedPreferences("tbNum", Context.MODE_PRIVATE);
            flag = sp.getInt(FLAG_SP_KEY,1);

            //添加到数据库
            userManager.add(new UserItem(username,password1,"tb_c"+flag));
            Log.i(TAG, "openLogin: username:"+username);


            flag =flag+1;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(FLAG_SP_KEY,flag);
            editor.commit();
            Log.i(TAG, "更新数据结束:flag= "+flag);
            Log.i(TAG, "run: 数据库已更新");

            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

            Intent login = new Intent(this,LoginActivity.class);
            startActivity(login);

        }else if (!password1.equals(password2)){
            Toast.makeText(RegisterActivity.this,"两次密码不同，请重新设置",Toast.LENGTH_SHORT).show();
            evPassword1.setText("");
            evPassword2.setText("");
            Log.i(TAG, "openLogin: 密码不同");
        }else if (usernameList.indexOf(username)>=0){
            Toast.makeText(RegisterActivity.this,"用户名重复，请重新输入",Toast.LENGTH_SHORT).show();
            tvUsername.setText("");
            evPassword1.setText("");
            evPassword2.setText("");
            Log.i(TAG, "openLogin: 用户名重复");
        }else if (username.length()== 0){
            Toast.makeText(RegisterActivity.this,"用用户名不可为空，请输入用户名",Toast.LENGTH_SHORT).show();
            evPassword1.setText("");
            evPassword2.setText("");
            Log.i(TAG, "openLogin: 用户名为空");
        }
        else {
            Toast.makeText(RegisterActivity.this,"密码长度必须不少于6位",Toast.LENGTH_SHORT).show();
            evPassword1.setText("");
            evPassword2.setText("");
            Log.i(TAG, "openLogin: 密码长度小于6位");
        }
    }

}