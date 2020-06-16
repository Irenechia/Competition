package com.example.competition;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserManager{
    private DBHelper dbHelper;
    //private String TBNAME;

    public UserManager(Context context) {
        dbHelper = new DBHelper(context);
        //TBNAME = DBHelper.TB_NAME2;
    }

    public void add(UserItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PASSWORD",item.getPassword());
        values.put("USERNAME",item.getUsername());
        values.put("TBNAME",item.getTBNAME());
        db.insert(DBHelper.TB_NAME_Customer,null,values);
        db.close();
    }

    public UserItem findbyUsername(String username){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TB_NAME_Customer,null,"USERNAME=?",new String[]{username},null,null,null);
        UserItem userItem = null;
        if (cursor!=null && cursor.moveToNext()){
            userItem = new UserItem();
            userItem.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
            userItem.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
            userItem.setTBNAME(cursor.getString(cursor.getColumnIndex("TBNAME")));
            cursor.close();
        }
        db.close();
        return userItem;
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TB_NAME_Customer,"ID=?",new String[]{String.valueOf(id)});
        db.close();
    }


    public java.util.List<UserItem> listAll(){
        List<UserItem> userItemList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //显示数据
        Cursor cursor = db.query(DBHelper.TB_NAME_Customer,null,null,null,null,null,null);
        if (cursor!=null){
            userItemList = new ArrayList<UserItem>();
            //将数据转换为item对象
            while(cursor.moveToNext()){
                UserItem item = new UserItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setUsername(cursor.getString(cursor.getColumnIndex("USERNAME")));
                item.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
                item.setTBNAME(cursor.getString(cursor.getColumnIndex("TBNAME")));
                userItemList.add(item);
            }
            cursor.close();
        }
        db.close();
        return userItemList;
    }

}
