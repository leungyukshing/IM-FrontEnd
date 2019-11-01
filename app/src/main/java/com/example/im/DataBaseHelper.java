package com.example.im;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.PagerAdapter;

import com.example.im.utils.ChatItem;
import com.hdl.elog.ELog;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static DataBaseHelper instance;
    private static Context context;
    private static final String DB_name = "MonniterDB";
    private SQLiteDatabase db;
    public static final String TABLE_NAME = "Alarm";
    public static final String ID = "id";
    public static final String CHATID = "chat_id";
    public static final String SENDERID = "user_id";
    public static final String SENDERNAME = "sender_name";
    public static final String CONTENT = "content";
    public static final String TIME = "time";
    public static final String ENCRYPTKEY = "encryptkey";


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
        String sql = "create table chat_record(id integer primary key autoincrement," +
                                                "chat_id integer," +
                                                "sender_id integer," +
                                                "sender_name text," +
                                                "content text," +
                                                "time datetime," +
                                                "encryptkey text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    public List<ChatItem> queryWithChatID(String chat_id) {
        String selection = "chat_id = ?";
        String[] selectArgs = new String[]{chat_id};
        return query(selection, selectArgs);
    }

    public boolean sendMessage(ChatItem chatItem) {
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put(CHATID, chatItem.getChatID());
        cv.put(SENDERID, chatItem.getSenderID());
        cv.put(SENDERNAME, chatItem.getSenderName());
        cv.put(CONTENT, chatItem.getContent());
        db.endTransaction();
        return true;
    }

    private List<ChatItem> query(String selection, String[] selectArgs) {
        /* distinct: dedup
         * table: table name
         * columns: columns to show
         * selection: select rule
         * selectionArgs: selected value
         * groupBy
         * having
         * orderBy: order
         * limit: page
         */
        /*
        * return an array of ChatRecord
        * */
        List<ChatItem> result = new ArrayList<ChatItem>() {};
        db.beginTransaction();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, CHATID, SENDERID, SENDERNAME, CONTENT, TIME, ENCRYPTKEY}, selection, selectArgs, null, null, null, null);
        // Cursor cursor = db.query("product", null, "code=?", new String[] { code }, null, null, null)
        while (cursor.moveToNext()) {
            ChatItem chatItem = new ChatItem();
            String id = cursor.getString(cursor.getColumnIndexOrThrow(ID));
            String chat_id = cursor.getString(cursor.getColumnIndexOrThrow(CHATID));
            String sender_id = cursor.getString(cursor.getColumnIndexOrThrow(SENDERID));
            String sender_name = cursor.getString(cursor.getColumnIndexOrThrow(SENDERNAME));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(CONTENT));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(TIME));
            String encryptkey = cursor.getString(cursor.getColumnIndexOrThrow(ENCRYPTKEY));

            ELog.e("DB query", "query id: " + id);
            ELog.e("DB query", "query chat_id: " + chat_id);
            ELog.e("DB query", "query sender_id: " + sender_id);
            ELog.e("DB query", "query sender_name" + sender_name);
            ELog.e("DB query", "query content: " + content);
            ELog.e("DB query", "query time: " + time);
            ELog.e("DB query", "query encryptkey: " + encryptkey);
            result.add(chatItem);
        }
        db.endTransaction();
        return result;
    }
}