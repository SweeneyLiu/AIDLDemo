package com.lsw.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lsw.aidlserver.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private IMyAidlInterface aidl;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = 0;
                try {
                    result = aidl.add(12, 12);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "远程回调结果:" + result);
            }
        });
    }

    private void bindService() {
        Intent intent = new Intent();
        //Android 5.0开始，启动服务必须使用显示的，不能用隐式的
        intent.setComponent(new ComponentName("com.lsw.aidlserver", "com.lsw.aidlserver.MyAidlService"));
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //绑定服务成功回调
            aidl = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //服务断开时回调
            aidl = null;
        }
    };

}


