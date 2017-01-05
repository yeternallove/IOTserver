package cn.edu.zucc.eternallove.iotserver.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.zucc.eternallove.iotserver.R;
import cn.edu.zucc.eternallove.iotserver.data.ChatMessageBean;
import cn.edu.zucc.eternallove.iotserver.data.SendIOTData;
import cn.edu.zucc.eternallove.iotserver.db.FairyDB;
import cn.edu.zucc.eternallove.iotserver.ui.adapter.ChatAdapter;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/1/5 00:16
 */

public class Fragment_iot_chat extends Fragment {

    private static final String CHECK = "^\\d{1,10}$";
    private static Fragment_iot_chat iot_chat;

    private FairyDB fairyDB;
    private ChatAdapter adapter;
    private InputMethodManager imm;
    private Pattern regex;
    private List<ChatMessageBean> mPastList;
    private List<ChatMessageBean> mNewList;
    private List<ChatMessageBean> mList;
    private ChatMessageBean mChatMessage;
    private SendIOTData sendIOTData;
    private Fragment_iot_con iot_con;
    private int index;
    private int hindex;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.btn_send)
    Button btnSend;

    @BindView(R.id.edt_send)
    EditText edtSend;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static Fragment_iot_chat getInstance() {
        if (iot_chat == null)
            iot_chat = new Fragment_iot_chat();
        return iot_chat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_iot_chat, container, false);
        ButterKnife.bind(this,rootView);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        fairyDB = FairyDB.getInstance(getActivity());
        iot_con = Fragment_iot_con.getInstance();
        sendIOTData = iot_con.getSendIOTData();
        regex = Pattern.compile(CHECK);

        mList = fairyDB.loadChatAll(ChatAdapter.USER);
        index = 0;
        hindex = Math.min(2,mList.size());
        mPastList = new ArrayList<>();
        for(; index < hindex; index++){
            mPastList.add(mList.get(index));
        }
        mNewList = new ArrayList<>();
        adapter = new ChatAdapter(getActivity(), mPastList, mNewList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        mRecyclerView.setHasFixedSize(true);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetChat().execute((Void) null);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = edtSend.getText().toString();
                if (msg == null || "".equals(msg)) {
                }
                mChatMessage = new ChatMessageBean(ChatAdapter.USER,ChatAdapter.USER, System.currentTimeMillis(), msg);
                chat(mChatMessage);
                send(mChatMessage);
                edtSend.setText("");
            }
        });
        return rootView;
    }

    public void chat(ChatMessageBean chatMessageBean){
        mNewList.add(chatMessageBean);
        adapter.notifyDataSetChanged();
        fairyDB.saveChat(chatMessageBean);
    }

    public void send(ChatMessageBean chatMessageBean){
        ChatMessageBean chatMessage = new ChatMessageBean("hello",ChatAdapter.USER,System.currentTimeMillis(),null);
        Matcher matcher = regex.matcher(chatMessageBean.getMessage());
        String message;
        if (!matcher.matches()) {
            message = "这个格式我发不了~\\(≧▽≦)/~啦啦啦";
            chatMessage.setMessage(message);
        }else {
            message = "已发送了呢";
            chatMessage.setMessage(message);
            sendIOTData.getSensor_dev3().setV2(Integer.valueOf(chatMessageBean.getMessage()));
            iot_con.setSendIOTData(sendIOTData);
        }
        chat(chatMessage);
    }
    private class GetChat extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //TODO : get date from ...then update mChatList
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            hindex = Math.min(hindex+5,mList.size());
            for(; index < hindex; index++){
                mPastList.add(mList.get(index));
            }
            adapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }
}
