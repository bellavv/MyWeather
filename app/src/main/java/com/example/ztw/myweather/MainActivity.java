package com.example.ztw.myweather;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.widget.TextView;
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


    private TextView cityTv, timeTv, humidityTv, weekTv, pmDataTv,pmQualityTv,temperatureTv, climateTv, windTv, city_name_Tv;
    private ImageView weatherImg, pmImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);

        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
            Log.d("my Weather", "网络OK");
            Toast.makeText(MainActivity.this,"网络OK！ ", Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.d("my Weather", "网络挂了" );
            Toast.makeText(MainActivity.this,"网络挂了！", Toast.LENGTH_LONG).show();
        }
        initView();
    }

    void initView(){
        city_name_Tv = (TextView)findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView)findViewById(R.id.time);
        humidityTv = (TextView)findViewById(R.id.humidity);
        weekTv = (TextView)findViewById(R.id.week_today);
        pmDataTv = (TextView)findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        pmImg = (ImageView)findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);
        city_name_Tv.setText("N/A");
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");
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

