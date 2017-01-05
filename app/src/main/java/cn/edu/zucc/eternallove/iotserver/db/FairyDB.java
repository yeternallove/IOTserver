package cn.edu.zucc.eternallove.iotserver.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.eternallove.iotserver.data.ChatMessageBean;


/**
 * @description:
 * @author: eternallove
 * @date: 2016/12/12
 */
public class FairyDB {
    public static final String DB_NAME = "Eternal";
    public static final int VERSION = 1;

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

    public void saveChat(ChatMessageBean chatMessageBean) {
        if (chatMessageBean.getSender_id() == null || chatMessageBean.getRecipient_id() == null)
            return;
        final String sql = "INSERT INTO Chat(sender_id,recipient_id,timestampe,message) VALUES(?,?,?,?)";
        db.execSQL(sql, new Object[]{chatMessageBean.getSender_id(), chatMessageBean.getRecipient_id(),
                chatMessageBean.getTimestampe(), chatMessageBean.getMessage()});
    }

    public ChatMessageBean queryChat(int id) {
        ChatMessageBean chatMessageBean = new ChatMessageBean();
        final String sql = "SELECT sender_id,recipient_id,timestampe,message FROM Chat WHERE id = ?";
        Cursor c = db.rawQuery(sql, new String[]{id + ""});
        if (c.moveToFirst()) {
            chatMessageBean.setId(id);
            chatMessageBean.setSender_id(c.getString(0));
            chatMessageBean.setRecipient_id(c.getString(1));
            chatMessageBean.setTimestampe(c.getLong(2));
            chatMessageBean.setMessage(c.getString(3));
        }
        c.close();
        return chatMessageBean;
    }

    public List<ChatMessageBean> loadChatAll(String recipient_id) {
        List<ChatMessageBean> list = new ArrayList<>();
        ChatMessageBean chatMessageBean;
        final String sql = "SELECT id,sender_id,timestampe,message FROM Chat WHERE recipient_id = ? ORDER BY timestampe DESC";
        Cursor c = db.rawQuery(sql, new String[]{recipient_id});
        while (c.moveToNext()) {
            chatMessageBean = new ChatMessageBean();
            chatMessageBean.setId(c.getInt(0));
            chatMessageBean.setSender_id(c.getString(1));
            chatMessageBean.setRecipient_id(recipient_id);
            chatMessageBean.setTimestampe(c.getLong(2));
            chatMessageBean.setMessage(c.getString(3));
            list.add(chatMessageBean);
        }
        return list;
    }
}
