package com.example.ztw.myweather;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.widget.Toast;
import android.widget.ImageView;

import java.io.*;
import java.net.*;
import java.lang.*;

import com.example.ztw.NetUtil;

/**
 * Created by ztw on 2018/9/28.
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private ImageView mUpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);
        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
            Log.d("my Weather", "网络OK");
            Toast.makeText(MainActivity.this,"网络OK！ ", Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.d("my Weather", "网络挂了" );
            Toast.makeText(MainActivity.this,"网络挂了！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.title_update_btn){
            SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            String cityCode = sharedPreferences.getString("main_city_code","101010100");
            Log.d("my Weather",cityCode);
            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("my Weather", "网络OK");
                queryWeatherCode(cityCode);
            }
            else
            {
                Log.d("my Weather", "网络挂了");
                Toast.makeText(MainActivity.this,"网络挂了！",Toast.LENGTH_LONG).show();
            }
        }
    }
    /**
     *
     * @param cityCode
     */
    private void queryWeatherCode(String cityCode)  {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;
        Log.d("my Weather", address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con=null;
                try{
                    URL url = new URL(address);
                    con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("GET" );
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while((str=reader.readLine()) != null){
                        response.append(str);
                        Log.d("my Weather", str);
                    }
                    String responseStr=response.toString();
                    Log.d("my Weather", responseStr);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(con != null){
                        con.disconnect();
                    }
                }
            }
        }).start();
    }
}

