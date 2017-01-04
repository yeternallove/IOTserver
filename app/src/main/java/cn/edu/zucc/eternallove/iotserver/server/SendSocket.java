package cn.edu.zucc.eternallove.iotserver.server;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import cn.edu.zucc.eternallove.iotserver.ui.fragment.Fragment_iot_con;

/**
 * Created by Michael on 2016/12/29 0029.
 */

public class SendSocket extends Thread {
    private Socket s = null;
    private DataOutputStream out;
    private OutputStream os;
    private static SendSocket msendSocket;

    private SendSocket(Socket s) {
        this.s = s;
    }

    public synchronized static SendSocket getInstance(Socket s) {
        if (msendSocket == null) {
            msendSocket = new SendSocket(s);
        }
        return msendSocket;
    }

    @Override
    public void run() {
        deal();
    }

    private void deal() {
        try {
            while (SocketServer.isContinue) {
                os = s.getOutputStream();
                out = new DataOutputStream(os);
                sleep(100);
                send(Fragment_iot_con.getInstance().controlIOT());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
                os.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void send(byte[] sendData) throws IOException {
        out.write(sendData);
        out.flush();
    }
}
