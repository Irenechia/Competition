package com.example.competition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class List extends AppCompatActivity {

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

}
