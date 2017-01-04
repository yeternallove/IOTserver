package cn.edu.zucc.eternallove.iotserver.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.zucc.eternallove.iotserver.R;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/1/5 00:16
 */

public class Fragment_iot_chat extends Fragment {
    private static Fragment_iot_chat iot_chat;

    public static Fragment_iot_chat getInstance() {
        if (iot_chat == null)
            iot_chat = new Fragment_iot_chat();
        return iot_chat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_iot_chat, container, false);
        return rootView;
    }
}
