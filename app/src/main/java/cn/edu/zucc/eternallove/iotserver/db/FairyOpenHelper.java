package cn.edu.zucc.eternallove.iotserver.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @description:
 * @author: eternallove
 * @date: 2016/12/12
 */
public class FairyOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_CHAT = "create table Chat(id integer primary key autoincrement,"
            + "sender_id char(20),"
            + "recipient_id char(20),"
            + "timestampe integer ,"
            + "message text )";

    public FairyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CHAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Chat");
        onCreate(db);
    }
}
