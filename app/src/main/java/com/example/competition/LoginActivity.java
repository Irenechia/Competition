package com.example.competition;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    String username;
    String password;
    TextView tvUsername;
    EditText evPassword;
    int tb_num;
    List<String> usernameList = new ArrayList<String>();
    List<String> passwordList = new ArrayList<String>();
    List<String> tbnameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvUsername = findViewById(R.id.login_username);
        evPassword = findViewById(R.id.login_password);

        UserManager userManager = new UserManager(LoginActivity.this);
        for (UserItem item:userManager.listAll()){
            usernameList.add(item.getUsername());
            passwordList.add(item.getPassword());
            tbnameList.add(item.getTBNAME());
            Log.i(TAG, "run: 已从数据库中获得username:"+item.getUsername());
            Log.i(TAG, "run: 已从数据库中获得password:"+item.getPassword());
            Log.i(TAG, "run: 已从数据库中获得TBNAME:"+item.getTBNAME());
            Log.i(TAG, "run: 已从数据库中获得数据");
        }


//        Intent intent = new Intent(this,LoginActivity.class);
//        intent.putExtra("TBNAME",TB_name);
//        startActivity(intent);
    }

    //登录
    public void login(View btn){

        username = String.valueOf(tvUsername.getText());
        password = String.valueOf(evPassword.getText());
        int result = testUser(username,password);
        //检验是否正确
        if (result == 1){
            Intent user = new Intent(this,MyCom.class);
            user.putExtra("username",username);
            user.putExtra("password",password);
            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

            int indexNum = usernameList.indexOf(username);


            SharedPreferences sp = getSharedPreferences("TB", Activity.MODE_PRIVATE);
            tb_num = sp.getInt("TB_NUM",1);

            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("TB_NUM",indexNum+1);
            editor.commit();

            Log.i(TAG, "login: TB_NUM:"+indexNum);

            Intent toMy = new Intent(this,MyCom.class);
            startActivity(toMy);

        }else if (result == -1){
            Toast.makeText(LoginActivity.this,"该用户不存在，请注册",Toast.LENGTH_SHORT).show();
            tvUsername.setText("");
            evPassword.setText("");

        }else if (username == ""){
            Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        }else if (password == ""){
            Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(LoginActivity.this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
            evPassword.setText("");
        }

    }

//    public void openLoginto(View btn){
//        Log.i(TAG, "openOne:open ");
//        Intent toMy = new Intent(this,LoginActivity.class);
//        startActivity(toMy);
//    }

    public void openRrgister(View btn){
        Log.i(TAG, "openOne:open ");
        Intent toMy = new Intent(this,RegisterActivity.class);
        startActivity(toMy);
    }

    public int testUser(String username,String password){
        int indexNum = usernameList.indexOf(username);
        if (indexNum<0){
            return -1;
        }else{
            String pw = passwordList.get(indexNum);
            if (password.equals(pw)){
                return 1;
            }else {
                return 0;
            }
        }
    }
}