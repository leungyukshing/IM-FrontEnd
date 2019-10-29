package com.example.im;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.PagerAdapter;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static DataBaseHelper instance;
    private static Context context;
    private static final String DB_name = "MonniterDB";
    private SQLiteDatabase db;
    public static final String TABLE_NAME = "Alarm";


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DataBaseHelper getInstance(Context context1) {
        context = context1;
        if (instance == null) {
            synchronized (DataBaseHelper.class) {
                if (instance == null) {
                    instance = new DataBaseHelper(context1, DB_name, null, 1);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void query(String selection, String[] selectArgs) {
        
    }
}