package com.lsw.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyAidlService extends Service {
    public MyAidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IMyAidlInterface.Stub(){
        @Override
        public int add(int value1, int value2) throws RemoteException {
            return value1 + value2;
        }
    };
}
