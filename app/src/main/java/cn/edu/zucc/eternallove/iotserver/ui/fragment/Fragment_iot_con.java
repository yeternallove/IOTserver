package cn.edu.zucc.eternallove.iotserver.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.zucc.eternallove.iotserver.R;
import cn.edu.zucc.eternallove.iotserver.data.SendIOTData;
import cn.edu.zucc.eternallove.iotserver.ui.dialog.ColorPickerDialog;
import cn.edu.zucc.eternallove.iotserver.util.DataDeal;


/**
 * @description:
 * @author: eternallove
 * @date: 2017/1/4 17:33
 */
//控制的fragment
public class Fragment_iot_con extends Fragment {

    private static Fragment_iot_con iot_con;
    private SendIOTData sendIOTData;
    private boolean isOpen = false;
    private short heartbeat = 0;
    private int ledColor = 0xFF000000;
    private int led_r = 0;
    private int led_g = 0;
    private int led_b = 0;
    private int moto = 0;

    @BindView(R.id.swi_light)
    Switch swiLed;

    @BindView(R.id.seekBar_moto)
    SeekBar seekBar;

    @BindView(R.id.txt_ledInfo)
    TextView tvInfo;


    @BindView(R.id.img_light)
    ImageView imgLight;

    public static Fragment_iot_con getInstance() {
        if (iot_con == null)
            iot_con = new Fragment_iot_con();
        return iot_con;
    }

    //控制的fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_iot_con, container, false);
        ButterKnife.bind(this, rootView);
        iot_con = this;
        init();
        setStart();
        return rootView;
    }

    private void init(){
        sendIOTData = new SendIOTData();
        seekBar.setMax(10);
        imgLight.setColorFilter(ledColor);
    }

    private void setStart(){
        imgLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ColorPickerDialog(getActivity(),ledColor,"color", new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        ledColor = color;
                        int[] str = DataDeal.intTorgb(color);
                        led_r = str[1];
                        led_g = str[2];
                        led_b = str[3];
                        tvInfo.setText("R : "+led_r+" G : "+led_g+" B :"+led_b);
                        imgLight.setColorFilter(color);
                        controlIOT();
                    }
                }).show();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                moto=i;
                controlIOT();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        swiLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isOpen = isChecked;
                controlIOT();
            }
        });
    }

    public void controlIOT(){
        heartbeat++;
        sendIOTData.setHeartbeat(heartbeat);
        //LED
        sendIOTData.getSensor_dev1().setV1(led_r);
        sendIOTData.getSensor_dev1().setV2(led_g);
        sendIOTData.getSensor_dev1().setV3(led_b);
        //moto
        sendIOTData.getSensor_dev2().setV1(moto);

        if(isOpen){
            sendIOTData.getSensor_dev3().setV1(1);
        }
        else{
            sendIOTData.getSensor_dev3().setV1(0);
        }
    }

    public byte[] send(){
        byte[] sendData = sendIOTData.getBytes();
        Log.d("send", DataDeal.bytesToHexString(sendData));
        return sendData;
    }

    public SendIOTData getSendIOTData() {
        return sendIOTData;
    }

    public void setSendIOTData(SendIOTData sendIOTData) {
        this.sendIOTData = sendIOTData;
    }
}