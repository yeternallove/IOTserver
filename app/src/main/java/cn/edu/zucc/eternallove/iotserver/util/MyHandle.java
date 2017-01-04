package cn.edu.zucc.eternallove.iotserver.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import cn.edu.zucc.eternallove.iotserver.data.ReceiveIOTData;
import cn.edu.zucc.eternallove.iotserver.ui.fragment.Fragment_iot_dis;


/**
 * Created by Michael on 2016/12/27 0027.
 */

public class MyHandle extends Handler {
    private Fragment_iot_dis fragment_iot_dis;
    public MyHandle(Fragment_iot_dis fragment_iot_dis) {
        this.fragment_iot_dis = fragment_iot_dis;
    }

    // 子类必须重写此方法,接受数据
    @Override
    public void handleMessage(Message msg) {
        Log.d("MyHandler", "handleMessage......");
        super.handleMessage(msg);
        Bundle b = msg.getData();
        byte[] receiver = b.getByteArray("Receiver");
        fragment_iot_dis.changeUI(receiver);

    }

}
