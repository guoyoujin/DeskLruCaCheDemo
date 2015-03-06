package com.example.myservicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service{
	private String tag="TAG";
	private MyBinder mBinder = new MyBinder();  
	public IBinder onBind(Intent arg0) {
		Log.e(tag, "onBind()");
		return mBinder;
		
	}
	/**
	 * 只在第一次startService(name)时候执行该方法
	 */
	public void onCreate() {
		super.onCreate();
		Log.e(tag, "onCreate()");
	}
	
	/**
	 * 每次startService()时都执行
	 */
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(tag, "onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}
	/**
	 * stopService()时执行
	 */
	public void onDestroy() {
		super.onDestroy();
		Log.e(tag, "onDestroy()");
	}
	
	public class MyBinder extends Binder{
		public String startDownLoad(String url){
			Log.e(tag, "startDownLoad()");
			return null;
		}
		public String startSendHttp(String url){
			Log.e(tag, "startSendHttp()");
			return null;
		}
	}
	
}
