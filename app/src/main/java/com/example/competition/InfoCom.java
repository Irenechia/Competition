package com.example.competition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfoCom extends AppCompatActivity implements Runnable,AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    Handler handler;
    private String TAG = "InfoCom";
    List<String> liList = new ArrayList<String>();
    List<String> herfList = new ArrayList<String>();
    List<String> resultList = new ArrayList<String>();
    List<String> myInfo = new ArrayList<String>();
    private ProgressDialog progressDialog;
    private String logDate = "";
    private final String  DATE_SP_KEY = "lastInfoStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_com);

        //获取时间
        SharedPreferences sp = getSharedPreferences("Info", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY,"");
        Log.i(TAG, "lastInfoStr: "+logDate);

        showProgressDialog("提示","加载中...");

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1){
                    Log.i(TAG, "handleMessage: adapter");
                    ListView listView = (ListView) findViewById(R.id.lv_comi);
                    ListAdapter adapter = new ArrayAdapter<String>(InfoCom.this,android.R.layout.simple_list_item_1,liList);
                    listView.setAdapter(adapter);
                }
                hideProgressDialog();
            }
        };

        Thread t = new Thread(this);
        t.start();

        ListView listView = (ListView) findViewById(R.id.lv_comi);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void run() {
        //获取加载时间
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i(TAG, "curDateStr: "+curDateStr+"，logDate"+logDate);

        //判断是从哪里获取数据
        if (curDateStr.equals(logDate)){
            //从数据库中获得数据
            Log.i(TAG, "run: 日期相等，从数据库中获得数据");
            InfoManager infoManager = new InfoManager(InfoCom.this);
            for (InfoItem item:infoManager.listAll(DBHelper.TB_NAME2)){
                liList.add(item.getInfo());
                Log.i(TAG, "run: 已从数据库中获得info"+item.getInfo());
                herfList.add(item.getHerf());
                Log.i(TAG, "run: 已从数据库中获得herf"+item.getHerf());
                Log.i(TAG, "run: 已从数据库中获得数据");
            }
        }else{
            Log.i(TAG, "run: 日期不等，从网络中获得数据");
            //获取网络数据
            int page = 0;
            try {
                URL url = new URL("https://it.swufe.edu.cn/index/tzgg.htm");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                InputStream inp = http.getInputStream();

                String html = inputStreamString(inp);
                Log.i(TAG, "run: "+html);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //写数据库的列表
            List<InfoItem> infoItemsList = new ArrayList<InfoItem>();

            //解析HTML
            Document doc = null;
            try {
                //首页数据提取
                Log.i(TAG, "run: 首页数据");
                String urlFirst = "https://it.swufe.edu.cn/index/tzgg.htm";
                doc = Jsoup.connect(urlFirst).get();
                Log.i(TAG, "run: "+doc.title());
                Elements tds = doc.getElementsByTag("td");
                Element tdPage = tds.get(1);
                Log.i(TAG, "run: pageString"+tdPage.text());
                String pageStr = tdPage.text();
                String pageNum = pageStr.substring(pageStr.length()-2,pageStr.length());
                Log.i(TAG, "run: page"+pageNum);
                page = Integer.parseInt(pageNum);


                //获取全部数据数据
                int size1,size2;
                int i = 1;
                String url = "https://it.swufe.edu.cn/index/tzgg.htm";
                for (;i<page+1;i++){
                    Log.i(TAG, "run: 第"+i+"页数据");
                    Log.i(TAG, "run: url:"+url);
                    doc = Jsoup.connect(url).get();
                    Elements lis = doc.getElementsByTag("li");
                    Elements herfs = doc.select("a[href]");


                    size1 = lis.size();
                    size2 = herfs.size();
                    url = herfs.get(size2-2).attr( "abs:href" ).toString();
                    for(int m = 68;m<size1-4;m++){
                        Element lii = lis.get(m);
                        //获取文字
                        Elements span = lii.getElementsByTag("span");
                        liList.add(span.text());
                        Log.i(TAG, "run: span:"+span.text());

                        //获取超链接
                        Element herfi = herfs.get(m+2);
                        herfList.add(herfi.attr( "abs:href" ));
                        Log.i(TAG, "run: herf:"+herfi.attr( "abs:href" ));

                        infoItemsList.add(new InfoItem(span.text(),herfi.attr( "abs:href" )));

                        Log.i(TAG, "run: 已添加"+span.text());
                        Log.i(TAG, "run: 已添加"+herfi.attr("abs:href"));
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //把数据写入数据库
            InfoManager infoManager = new InfoManager(this);
            infoManager.deleteAll(DBHelper.TB_NAME2);
            infoManager.addAll(infoItemsList,DBHelper.TB_NAME2);
            Log.i(TAG, "run: 数据库已更新");

        }
        //记录更新日期
        SharedPreferences sp = getSharedPreferences("Info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(DATE_SP_KEY,curDateStr);
        editor.commit();
        Log.i(TAG, "更新数据结束: "+curDateStr);

        Message msg = handler.obtainMessage(1);
        msg.obj = liList;
        handler.sendMessage(msg);
    }

    private String inputStreamString(InputStream inputStream) throws IOException {
        final  int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"UTF-8");
        for(;;){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }

    public void search(View btn){
        resultList.clear();
        //获取关键字
        EditText input = (EditText) findViewById(R.id.info_keywords);
        String str = input.getText().toString();
        Log.i(TAG, "search: 获取关键字"+str);

        //查找
        String s;
        for (int n = 0;n<liList.size();n++){
            s= liList.get(n);
            Log.i(TAG, "search: 查找:"+s);
            Log.i(TAG, "search: str:"+str);
            Log.i(TAG, "search: "+s.indexOf(str));
            if (s.indexOf(str)!=-1){
                resultList.add(s);
            }
        }
        if (resultList.size()==0){
            Toast.makeText(this,"不存在数据",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "search: 提示");
        }else{
            //显示
            ListView listView = (ListView) findViewById(R.id.lv_comi);
            ListAdapter adapterResult = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,resultList);
            listView.setAdapter(adapterResult);
        }
    }

    //事件监听
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = (ListView) findViewById(R.id.lv_comi);
        String searchResult = (String)listView.getItemAtPosition(position);
        Log.i(TAG, "onItemClick: 点击"+id);
        int searchInt = liList.indexOf(searchResult) ;
        Log.i(TAG, "onItemClick: "+searchResult);
        String searchURL = herfList.get(searchInt);
        Log.i(TAG, "onItemClick: "+searchURL);
        Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchURL));
        startActivity(newIntent);
    }

    /*
     * 提示加载
     */
    public void showProgressDialog(String title, String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(InfoCom.this, title, message, true, false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
        }
        progressDialog.show();
    }

    /*
     * 隐藏提示加载
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
        Log.i(TAG, "onItemLongClick: 长按position"+position);
        //构造对话框提示
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("是否添加到我的消息中？").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick: 对话框处理");
                //将数据加入tb_my
                ListView listView = (ListView) findViewById(R.id.lv_comi);
                String toMy = (String)listView.getItemAtPosition(position);
                Log.i(TAG, "onItemClick: 点击"+id);
                int myInt = liList.indexOf(toMy) ;
                Log.i(TAG, "onItemClick:toMy="+toMy);
                String myURL = herfList.get(myInt);
                Log.i(TAG, "onItemClick: "+myURL);
                InfoManager infoManager = new InfoManager(InfoCom.this);

                //获取MyCom中数据
                for (InfoItem item:infoManager.listAll(DBHelper.TB_NAME)){
                    myInfo.add(item.getInfo());
                    Log.i(TAG, "onClick: MyInfo:"+item.getInfo());
                }
                //判断是否存在

                if (myInfo.indexOf(toMy) !=-1 ){
                    Toast.makeText(InfoCom.this,"不可重复添加",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: 阻止重复添加");
                }else {
                    infoManager.add(new InfoItem(toMy,myURL),DBHelper.TB_NAME);
                    Toast.makeText(InfoCom.this,"已添加",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onItemLongClick: 已添加到TB_MY");
                }
            }
        }).setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
}