package com.example.appservicesdemo1;

import com.example.appservicesdemo1.aidl.app.AppServiceRemoteBinder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class AppServices extends Service{
	private String data="Ä¬ÈÏÊý¾Ý";

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		
		return new AppServiceRemoteBinder.Stub() {
			
			@Override
			public void basicTypes(int anInt, long aLong, boolean aBoolean,
					float aFloat, double aDouble, String aString)
					throws RemoteException {
					
				
			}

			@Override
			public void setData(String data) throws RemoteException {
				AppServices.this.data=data;
			}
		};
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.e("Tag", "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	private boolean running=false;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		System.out.print("======================================");
		Log.e("Tag","appservice=======oncreate");
		running=true;
		super.onCreate();
		new Thread(){
			public void run() {
				while(running){
					Log.e("Tag", "======="+data);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.e("Tag","appservice=======destroy");
		super.onDestroy();
	}
	
}
