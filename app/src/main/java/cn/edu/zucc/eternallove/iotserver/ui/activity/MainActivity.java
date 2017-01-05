package cn.edu.zucc.eternallove.iotserver.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.IOException;

import cn.edu.zucc.eternallove.iotserver.R;
import cn.edu.zucc.eternallove.iotserver.server.SocketServer;
import cn.edu.zucc.eternallove.iotserver.ui.fragment.Fragment_iot_chat;
import cn.edu.zucc.eternallove.iotserver.ui.fragment.Fragment_iot_con;
import cn.edu.zucc.eternallove.iotserver.ui.fragment.Fragment_iot_dis;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("传感器"); //显示toolbar的默认title
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //左右切换fragment的时候改变title
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //页面切换的时候改变title
                if (position == 0) {
                    setTitle("传感器");
                }
                else if (position == 1) {
                    setTitle("控制器");
                }else if (position == 2) {
                    setTitle("消息");
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        //socket 在程序处于后台或者屏幕关闭的时候关闭socket
        try {
            if (SocketServer.sst!=null && !SocketServer.sst.isClosed()) {
                SocketServer.sst.close(); //关闭Server socket
                SocketServer.isContinue = false;//停止 socket继续执行
                Fragment_iot_dis.getInstance().swiPort.setChecked(false);
                System.out.println("Server close");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return Fragment_iot_dis.getInstance();
                case 1: return Fragment_iot_con.getInstance();
                case 2:return Fragment_iot_chat.getInstance();
                default:return null;
                }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            //总共三个界面，
            return 3;
        }
    }
}
