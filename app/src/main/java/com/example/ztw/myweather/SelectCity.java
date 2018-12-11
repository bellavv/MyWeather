package com.example.ztw.myweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ztw.app.MyApplication;
import com.example.ztw.bean.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ztw on 2018/10/16.
 */
public class SelectCity extends Activity implements View.OnClickListener{
    private ImageView mBackBtn;
    private ListView cityListLv;
    private EditText searchEt;
    private ImageView searchBtn;


    private List<City> mCityList;
    private MyApplication mApplication;
    private ArrayList<String> mArrayList;
    private ArrayAdapter<String> adapter;

    private  String updateCityCode = "-1";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        //查询城市
        searchEt = (EditText)findViewById(R.id.selectcity_search);//搜索城市的文本框
        searchBtn = (ImageView)findViewById(R.id.selectcity_search_button);//搜索按钮
        searchBtn.setOnClickListener(this);//设置监听事件
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);//返回按钮设置

        mApplication = (MyApplication)getApplication();
        mCityList = mApplication.getCityList();
        mArrayList = new ArrayList<String>();
        for(int i=0;i<mCityList.size();i++)
        {
            String No = Integer.toString(i+1);
            String number = mCityList.get(i).getNumber();
            String provinceName = mCityList.get(i).getProvince();
            String cityName = mCityList.get(i).getCity();
            mArrayList.add("No."+No+":"+number+"-"+provinceName+"-"+cityName);
//            String cityName = mCityList.get(i).getCity();
//            mArrayList.add(cityName);
        }

        cityListLv = (ListView)findViewById(R.id.selectcity_lv);//获取列表
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectCity.this,android.R.layout.simple_list_item_1,mArrayList);
        cityListLv.setAdapter(adapter);

        //添加ListView项的点击事件的动作
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String updateCityCode = mCityList.get(position).getNumber();
                updateCityCode = mCityList.get(position).getNumber();
                Log.d("update city code",updateCityCode);
                Log.d("update city code",updateCityCode);
                //用Shareperference存储最近一次的citycode
               // SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference",Activity.MODE_PRIVATE);
                //SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putString("citycode", updateCityCode);
                //editor.commit();
                Intent intent = new Intent();
                intent.putExtra("citycode", updateCityCode);
                startActivity(intent);
                setResult(RESULT_OK);//设置返回结果
                SelectCity.this.finish();//从栈中弹出这个画面
            }
        };
        //为组件绑定监听
        cityListLv.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //获取citycode更新数据
            case R.id.selectcity_search_button:
                String citycode = searchEt.getText().toString();
                Log.d("Search", citycode);
                ArrayList<String> mSearchList = new ArrayList<String>();
                for (int i=0;i<mCityList.size();i++)
                {
                    String No = Integer.toString(i+1);
                    String number = mCityList.get(i).getNumber();
                    String provinceName = mCityList.get(i).getProvince();
                    String cityName = mCityList.get(i).getCity();
                    if(number.equals(citycode)){
                        mSearchList.add("No."+No + ":" + number + "-" + provinceName + "-" + cityName);
                        Log.d("change adapter data","No."+No + ":" + number + "-" + provinceName + "-" + cityName);
                    }
                    adapter = new ArrayAdapter<String>(SelectCity.this,android.R.layout.simple_list_item_1,mSearchList);
                    cityListLv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
//                Intent intent = new Intent(this,MainActivity.class);
//                intent.putExtra("citycode", citycode);
//                startActivity(intent);
            case R.id.title_back:
                Intent i = new Intent();
                i.putExtra("cityCode", updateCityCode);
//                setResult(RESULT_OK, i);
//                finish();
                startActivity(i);
                break;
            default:
                break;

        }

    }
}
