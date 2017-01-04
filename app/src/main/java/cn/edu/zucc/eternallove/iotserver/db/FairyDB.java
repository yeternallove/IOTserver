package cn.edu.zucc.eternallove.iotserver.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * @description:
 * @author: eternallove
 * @date: 2016/12/12
 */
public class FairyDB {
    public static final String DB_NAME = "Eternal";
    public static final int VERSION = 3;

    private static FairyDB mfairyDB;
    private SQLiteDatabase db;
    private Context mContext;

    private FairyDB(Context context) {
        this.mContext = context;
        FairyOpenHelper dbHelper = new FairyOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static FairyDB getInstance(Context context) {
        if (mfairyDB == null) {
            mfairyDB = new FairyDB(context);
        }
        return mfairyDB;
    }
}
