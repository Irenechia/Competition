package com.example.competition;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyCom extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    final String TAG = "MyCom";
    java.util.List<String> liList = new ArrayList<String>();
    java.util.List<String> herfList = new ArrayList<String>();
    private SimpleAdapter listItemAdapter;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_com);
        ListView listView = findViewById(R.id.lv_detail);
        //listView.setEmptyView(findViewById(R.id.noData));

        adapter = new ArrayAdapter<String>(MyCom.this,android.R.layout.simple_list_item_1,liList);
        listView.setAdapter(adapter);

        InfoManager infoManager = new InfoManager(this);
        for(InfoItem item:infoManager.listAll(DBHelper.TB_NAME)){
            liList.add(item.getInfo());
            Log.i(TAG, "run: 已从数据库中获得info:"+item.getInfo());
            herfList.add(item.getHerf());
            Log.i(TAG, "run: 已从数据库中获得herf:"+item.getHerf());
        }
        adapter.notifyDataSetChanged();



        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

    }

    public void openList(View btn){
        Log.i(TAG, "openOne:open ");
        Intent toList = new Intent(this,List.class);
        startActivity(toList);
    }

    public void refresh(View btn){
        InfoManager infoManager = new InfoManager(this);
        liList.clear();
        herfList.clear();
        for (InfoItem item:infoManager.listAll(DBHelper.TB_NAME)){
            liList.add(item.getInfo());
            Log.i(TAG, "run: 已从数据库中获得info:"+item.getInfo());
            herfList.add(item.getHerf());
            Log.i(TAG, "run: 已从数据库中获得herf:"+item.getHerf());
        }
        Log.i(TAG, "run: 已从数据库中获得数据");

        ListView listView = findViewById(R.id.lv_detail);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
        Log.i(TAG, "onItemLongClick: 长按position"+position);
        //构造对话框提示
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("是否删除消息？").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView listView = findViewById(R.id.lv_detail);
                //adapter.remove(listView.getItemAtPosition(position));
                liList.remove(position);
                adapter.notifyDataSetChanged();

                Log.i(TAG, "onClick: position："+position);

                java.util.List<InfoItem> infoItemsList = new ArrayList<InfoItem>();

                InfoManager infoManager = new InfoManager(MyCom.this);
                infoManager.deleteAll(DBHelper.TB_NAME);
                for (int i = 0;i<liList.size();i++){
                    InfoItem curitem = new InfoItem(liList.get(i), herfList.get(i));
                    infoItemsList.add(curitem);
                }
                infoManager.addAll(infoItemsList,DBHelper.TB_NAME);

                Toast.makeText(MyCom.this,"已删除",Toast.LENGTH_SHORT).show();

                Log.i(TAG, "onItemLongClick: 数据库删除数据"+position);
                Log.i(TAG, "onItemLongClick: 更新");
            }
        }).setNegativeButton("否",null);
        builder.create().show();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: "+position);
        ListView listView = (ListView) findViewById(R.id.lv_detail);
        String searchResult = (String)listView.getItemAtPosition(position);
        Log.i(TAG, "onItemClick: 点击"+id);
        int searchInt = liList.indexOf(searchResult) ;
        Log.i(TAG, "onItemClick: "+searchResult);
        String searchURL = herfList.get(searchInt);
        Log.i(TAG, "onItemClick: "+searchURL);
        Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchURL));
        startActivity(newIntent);
    }
}