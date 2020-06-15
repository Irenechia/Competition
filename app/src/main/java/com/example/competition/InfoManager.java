package com.example.competition;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class InfoManager {

    private DBHelper dbHelper;
    //private String TBNAME;

    public InfoManager(Context context) {
        dbHelper = new DBHelper(context);
        //TBNAME = DBHelper.TB_NAME2;
    }

    //添加数据
    public void add(InfoItem item,String TBNAME){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CURINFORMATION",item.getInfo());
        values.put("CURHERF",item.getHerf());
        db.insert(TBNAME,null,values);
        db.close();
    }

    //添加所有数据
    public void addAll(List<InfoItem> list,String TBNAME){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (InfoItem item : list){
            ContentValues values = new ContentValues();
            values.put("CURINFORMATION",item.getInfo());
            values.put("CURHERF",item.getHerf());
            db.insert(TBNAME,null,values);
        }
        db.close();
    }

    //清除数据
    public void delete(int id,String TBNAME){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,"ID=?",new String[]{String.valueOf(id)});
        db.close();
    }


    public void deleteAll(String TBNAME){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    //显示数据
    public List<InfoItem> listAll(String TBNAME){
        List<InfoItem> infoItemList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //显示数据
        Cursor cursor = db.query(TBNAME,null,null,null,null,null,null);
        if (cursor!=null){
            infoItemList = new ArrayList<InfoItem>();
            //将数据转换为item对象
            while(cursor.moveToNext()){
                InfoItem item = new InfoItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setInfo(cursor.getString(cursor.getColumnIndex("CURINFORMATION")));
                item.setHerf(cursor.getString(cursor.getColumnIndex("CURHERF")));
                infoItemList.add(item);
            }
            cursor.close();
        }
        db.close();
        return infoItemList;
    }

    //通过id查询
    public InfoItem findbyID(int id,String TBNAME){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,"ID=?",new String[]{String.valueOf(id)},null,null,null);
        InfoItem infoItem = null;
        if (cursor!=null && cursor.moveToNext()){
            infoItem = new InfoItem();
            infoItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            infoItem.setInfo(cursor.getString(cursor.getColumnIndex("CURINFORMATION")));
            infoItem.setHerf(cursor.getString(cursor.getColumnIndex("CURHERF")));
            cursor.close();
        }
        db.close();
        return infoItem;
    }
}
