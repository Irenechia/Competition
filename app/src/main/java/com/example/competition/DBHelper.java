package com.example.competition;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME1 = "information";
    public static final String TB_NAME1 = "tb_finance";
    public static final String TB_NAME2 = "tb_info";
    public static final String TB_NAME3 = "tb_math";
    public static final String TB_NAME = "tb_my";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context){
        super(context,DB_NAME1,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME1+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURINFORMATION TEXT,CURHERF TEXT)");
        db.execSQL("CREATE TABLE "+TB_NAME2+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURINFORMATION TEXT,CURHERF TEXT)");
        db.execSQL("CREATE TABLE "+TB_NAME3+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURINFORMATION TEXT,CURHERF TEXT)");
        db.execSQL("CREATE TABLE "+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURINFORMATION TEXT,CURHERF TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
