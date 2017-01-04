package cn.edu.zucc.eternallove.iotserver.server;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by falling on 2015/12/1.
 */
public class SocketServer extends Thread {
    public static ServerSocket sst = null;
    public static boolean isContinue = true;//表示接入的Socket是否继续执行
    private int mPort;
    private Context mContext;

    public SocketServer(Context context,int port) {
        this.mContext = context;
        this.mPort = port;
    }

    @Override
    public void run() {
        startServer();
    }

    private void startServer() {
        try {
            sst = new ServerSocket(mPort);
            while (true) {
                Log.i("SocketServer","Server start!");
                Socket s = sst.accept();
                ReceiveSocket.getInstance(s).start();
                SendSocket.getInstance(s).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
