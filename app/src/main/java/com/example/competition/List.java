package com.example.competition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class List extends AppCompatActivity implements View.OnLongClickListener {

    final String TAG = "List";
    Handler handler;
    java.util.List<String> liList = new ArrayList<String>();
    java.util.List<String> herfList = new ArrayList<String>();
    java.util.List<String> liList2 = new ArrayList<String>();
    java.util.List<String> herfList2 = new ArrayList<String>();
    java.util.List<String> liList3 = new ArrayList<String>();
    java.util.List<String> herfList3 = new ArrayList<String>();
    private int page = 0;
    private int page2 = 0;
    private int page3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    //菜单操作
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.academy,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.Account){
            Intent toA = new Intent(this, AccountCom.class);
            startActivity(toA);
            Log.i(TAG, "onOptionsItemSelected: 打开会计学院");
        }else if (item.getItemId()==R.id.BA){
            Intent toB = new Intent(this, ForeignCom.class);
            startActivity(toB);
            Log.i(TAG, "onOptionsItemSelected: 打开工商管理学院");
        }
        return super.onOptionsItemSelected(item);
    }

    //打开界面
    public void openLFinance(View btn) {
        Log.i(TAG, "openOne:open ");
        Intent toF = new Intent(this, FinanceCom.class);
        startActivity(toF);
    }

    public void openMath(View btn) {
        Log.i(TAG, "openOne:open ");
        Intent tomath = new Intent(this, MathCom.class);
        startActivity(tomath);
    }

    public void openInfo(View btn) {
        Log.i(TAG, "openOne:open ");
        Intent toInfo = new Intent(this, InfoCom.class);
        startActivity(toInfo);
    }

    @Override
    public boolean onLongClick(View btn) {

        Log.i(TAG, "onLongClick: 长按");
        return false;
    }
}
