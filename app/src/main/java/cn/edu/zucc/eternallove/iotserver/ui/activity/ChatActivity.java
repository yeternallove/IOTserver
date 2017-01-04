package cn.edu.zucc.eternallove.iotserver.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.eternallove.iotserver.R;
import cn.edu.zucc.eternallove.iotserver.data.ChatMessageBean;
import cn.edu.zucc.eternallove.iotserver.ui.adapter.ChatAdapter;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/1/4 17:33
 */

public class ChatActivity extends Fragment{

    private RecyclerView mRecyclerView;
    private ChatAdapter adapter;
    private List<ChatMessageBean> mPastList;
    private List<ChatMessageBean> mNewList;

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ChatActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_iot_chat);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mPastList = new ArrayList<>();
        mNewList = new ArrayList<>();
        adapter = new ChatAdapter(getActivity(), mPastList, mNewList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        mRecyclerView.setHasFixedSize(true);
        return view;
    }
}
