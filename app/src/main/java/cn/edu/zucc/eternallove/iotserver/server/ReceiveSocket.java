package cn.edu.zucc.eternallove.iotserver.server;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import cn.edu.zucc.eternallove.iotserver.data.ReceiveIOTData;
import cn.edu.zucc.eternallove.iotserver.ui.fragment.Fragment_iot_dis;
import cn.edu.zucc.eternallove.iotserver.util.DataDeal;

/**
 * Created by falling on 2015/12/19.
 */
public class ReceiveSocket extends Thread {
    private ReceiveIOTData d;
    private byte[] bytes = new byte[57];
    private Socket s = null;
    private InputStream is;
    private DataInputStream in;
    private static ReceiveSocket mreceiveSocket;


    private ReceiveSocket(Socket s) {
        this.s = s;
    }

    public synchronized static ReceiveSocket getInstance(Socket s) {
        if (mreceiveSocket == null) {
            mreceiveSocket = new ReceiveSocket(s);
        }
        return mreceiveSocket;
    }

    @Override
    public void run() {
        deal();
    }

    private void deal() {
        try {
            while (SocketServer.isContinue) {
                is = s.getInputStream();
                in = new DataInputStream(is);
                in.read(bytes);
                Log.d("receiver", DataDeal.bytesToHexString(bytes));
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putByteArray("Receiver", bytes);
                msg.setData(b);
                Fragment_iot_dis.myHandle.sendMessage(msg);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                in.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

