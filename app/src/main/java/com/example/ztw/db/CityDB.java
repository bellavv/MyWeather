package com.example.ztw.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ztw.bean.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ztw on 2018/10/20.
 */
public class CityDB {
    public static final String CITY_DB_NAME = "city.db";
    private static final String CITY_TABLE_NAME = "city";
    private SQLiteDatabase db;
    public CityDB(Context context, String path)
    {
        db = context.openOrCreateDatabase(path, Context.MODE_PRIVATE, null);
    }
    public List<City> getAllCity()
    {
        List<City> list = new ArrayList<City>();
        Cursor c = db.rawQuery("SELECT * from " + CITY_TABLE_NAME, null);
        while (c.moveToNext())
        {
            String province = c.getString(c.getColumnIndex("prov ince"));
            String city = c.getString(c.getColumnIndex("city"));
            String number = c.getString(c.getColumnIndex("number "));
            String allPY = c.getString(c.getColumnIndex("allpy") );
            String allFirstPY = c.getString(c.getColumnIndex("al lfirstpy"));
            String firstPY = c.getString(c.getColumnIndex("first py"));
            City item = new City(province, city, number, firstPY , allPY,allFirstPY);
            list.add(item);
        }
        return list;
    }

}
