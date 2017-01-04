package cn.edu.zucc.eternallove.iotserver.ui.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.zucc.eternallove.iotserver.R;
import cn.edu.zucc.eternallove.iotserver.data.ChatMessageBean;
import cn.edu.zucc.eternallove.iotserver.db.FairyDB;
import cn.edu.zucc.eternallove.iotserver.util.DateUtil;

/**
 * @description:
 * @author: eternallove
 * @date: 2016/12/25
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private static final int TYPE_MESSAGE_SEND = 0x1;
    private static final int TYPE_MESSAGE_RECEIVED = 0x2;
    private final String mID;
    private List<ChatMessageBean> mPastList;
    private List<ChatMessageBean> mNewList;

    private Context mContext;
    private LayoutInflater layoutInflater;
    private ClipboardManager cmb;
    private FairyDB fairyDB;

    public ChatAdapter(Context context, List<ChatMessageBean> pastList, List<ChatMessageBean> newList) {
        this.mPastList = pastList;
        this.mNewList = newList;
        this.mContext = context;
        this.mID = PreferenceManager.getDefaultSharedPreferences(context).getString("user_id",null);
        this.layoutInflater = LayoutInflater.from(mContext);
        this.cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        this.fairyDB = FairyDB.getInstance(context);
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageBean chatMessageBean = getRecivedBean(position);
        if(chatMessageBean.getSender_id().equals(mID)){
            return TYPE_MESSAGE_SEND;
        }else {
            return TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_MESSAGE_SEND:
                return new MessageHolder(layoutInflater.inflate(R.layout.item_sent_message, parent, false));
            case TYPE_MESSAGE_RECEIVED:
                return new MessageHolder(layoutInflater.inflate(R.layout.item_chat_message, parent, false));
            default:return null;
        }
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {

        switch (getItemViewType(position)){
            case TYPE_MESSAGE_SEND:
                onBindViewMessageHolder(holder,position);
                break;
            case TYPE_MESSAGE_RECEIVED:
                onBindViewMessageHolder(holder,position);
                break;
            default:break;
        }
    }

    private void onBindViewMessageHolder(ChatHolder holder, int position){
        ChatMessageBean chatMessageBean = getRecivedBean(position);
        MessageHolder messageHolder = (MessageHolder) holder;
        messageHolder.ChatContent.setText(chatMessageBean.getMessage());

//        messageHolder.imgUserhead
        if(position < getItemCount()-1){
            ChatMessageBean chatMessageOld = getRecivedBean(position+1);
            if(chatMessageBean.getTimestampe()-chatMessageOld.getTimestampe() > 1800000){
                messageHolder.Timestamp.setText(DateUtil.getReportDate(chatMessageBean.getTimestampe()));
            }else{
                messageHolder.Timestamp.setVisibility(View.GONE);
            }
        }else {
            messageHolder.Timestamp.setText(DateUtil.getReportDate(chatMessageBean.getTimestampe()));
        }
        messageHolder.UserName.setVisibility(View.GONE);
    }

    private ChatMessageBean getRecivedBean(int position){
        int length = mNewList.size();
        if(position > length ){
            return mPastList.get(position - length - 1);
        }else{
            return mNewList.get(length  - position);
        }
    }

    private boolean removeReciveditem(int position){
        int length = mNewList.size();
        if(position > length ){
            mPastList.remove(position - length - 1);
        }else{
            mNewList.remove(length  - position);
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return mNewList.size()+ mPastList.size();
    }

    static class ChatHolder extends RecyclerView.ViewHolder {

        ChatHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class MessageHolder extends ChatHolder {
        @BindView(R.id.tv_chatcontent)  TextView ChatContent;
        @BindView(R.id.timestamp)       TextView Timestamp;
        @BindView(R.id.tv_userName)     TextView UserName;
        @BindView(R.id.img_userhead)    ImageView Userhead;

        MessageHolder(View itemView) {
            super(itemView);
        }
    }


}
