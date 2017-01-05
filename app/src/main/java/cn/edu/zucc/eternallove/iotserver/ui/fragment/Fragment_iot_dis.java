package cn.edu.zucc.eternallove.iotserver.ui.fragment;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

import cn.edu.zucc.eternallove.iotserver.R;
import cn.edu.zucc.eternallove.iotserver.data.ReceiveIOTData;
import cn.edu.zucc.eternallove.iotserver.server.SocketServer;
import cn.edu.zucc.eternallove.iotserver.util.MyHandle;
import cn.edu.zucc.eternallove.iotserver.util.DataDeal;


/**
 * @description:
 * @author: eternallove
 * @date: 2017/1/4 17:33
 */

//显示的fragment
public class Fragment_iot_dis extends Fragment {
    private static Fragment_iot_dis iot_dis;
    private static SocketServer server;
    public Switch swiPort;//socket开关
    public static MyHandle myHandle ;
    private TextView txt_port;
    private TextView txt_host;
    private TextView txt_info;
    private static int port = 8888;
    private String isOpen = "";

    public static Fragment_iot_dis getInstance() {
        if (iot_dis == null)
            iot_dis = new Fragment_iot_dis();
        return iot_dis;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_iot_dis, container, false);
        myHandle = new MyHandle(this);
        txt_port = (TextView)rootView.findViewById(R.id.txt_port);
        txt_host = (TextView)rootView.findViewById(R.id.txt_host);
        txt_info = (TextView)rootView.findViewById(R.id.txt_info);
        swiPort = (Switch)rootView.findViewById(R.id.swiLink) ;
        txt_port.setText(String.valueOf(port));

        // socket的开关
        swiPort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //开启socket服务
                    server = new SocketServer(getActivity(),port);
                    server.start();
                    String ip = getHostIP();
                    if(ip == null){
                        swiPort.setChecked(false);
                        return;
                    }
                    txt_host.setText(ip);
                    SocketServer.isContinue = true;
//                    Toast.makeText(getContext(), "Socket 服务开启，端口号为:" + port, Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        //关闭socket服务
                        if (!SocketServer.sst.isClosed()) {
                            SocketServer.sst.close();//关闭Server socket
                            SocketServer.isContinue = false; //接入的socket不在继续执行
                            Toast.makeText(getContext(), "Socket 服务关闭", Toast.LENGTH_SHORT).show();
                            System.out.println("Server close");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return rootView;
    }

    public void changeUI(byte[] message) {
        ReceiveIOTData d = null;
        Log.d("receiver", DataDeal.bytesToHexString(message));
        try {
            d = new ReceiveIOTData(message);

            if(d.getSensor_data_frame3().getV1()==1){
                isOpen = "开";
            }
            else
                isOpen = "关";
            String info = "";
            info = "心跳包 ："+d.getHearbeat()+"\n"
                    +"iotID ："+String.valueOf(d.getID())+"\n"
                    +"传感器数量 ："+d.getSensor_num()+"\n"
                    +"温度 ："+String.valueOf(d.getSensor_data_frame1().getV1())+"\n"
                    +"湿度 ："+String.valueOf(d.getSensor_data_frame1().getV2())+"\n"
                    +"R ："+String.valueOf(d.getSensor_data_frame2().getV1())+"\n"
                    +"G ："+String.valueOf(d.getSensor_data_frame2().getV2())+"\n"
                    +"B ："+String.valueOf(d.getSensor_data_frame2().getV3())+"\n"
                    +"红外 ："+isOpen+"\n";
            txt_info.setText(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHostIP() {
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        if(!wifiManager.isWifiEnabled()){
            return null;
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    private String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }


}
