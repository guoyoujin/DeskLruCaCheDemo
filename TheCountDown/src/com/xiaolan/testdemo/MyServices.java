package com.xiaolan.testdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MyServices extends Service{
	public static final String ACTION = "com.xiaolan.testdemo.MyServices";  
	private String tag="TAG";
	private MyBinder mBinder = new MyBinder();  
	private CallBack callBack;
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
		private TextView tv;
		private long time;
		Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					tv.setText(msg.obj.toString());
					break;

				default:
					break;
				}
			};
		};
		public String setTime(long hour,long min,long seconds,TextView tv){
			time=hour*60*60+min*60+seconds;
			Log.e("TAG", "time==="+time);
			Log.e("TAG",getDate(time*1000));
			this.tv=tv;
			initThread();
			return null;
		}
		
		public MyServices getServices(){
			return MyServices.this;
		}
		public void initThread(){
			new Thread(new Runnable() {			
				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep(1000);
							
							String text=getDate(time*1000);
							if(callBack!=null){
								callBack.onDataChange(text);
							}
							Message msg=new Message();
							msg.what=0;
							msg.obj=text;
							handler.sendMessage(msg);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(time<=0){
							return;
						}else{
							time--;
						}
					}
				}
			}).start();
		}
		private String getDate(long l) {
			long nd = 1000 * 24 * 60 * 60;
			long nh = 1000 * 60 * 60;
			long nm = 1000 * 60;
			long ns = 1000;
			String h, m, s;

			//long day = _time_s / nd;
			// 计算差多少小时
			long hour = l % nd / nh;
			if (hour < 10)
				h = "0" + hour;
			else
				h = "" + hour;
			// 计算差多少分钟
			long min = l % nd % nh / nm;
			if (min < 10)
				m = "0" + min;
			else
				m = "" + min;
			// 计算差多少秒//输出结果
			long sec = l % nd % nh % nm / ns;
			if (sec < 10)
				s = "0" + sec;
			else
				s = "" + sec;
			return h + ":" + m + ":" + s;
		}
		
	}
	public CallBack getCallBack(){
		return callBack;	
	}
	public void setCallBack(CallBack callBack){
		this.callBack=callBack;
	}
	
	public static interface CallBack{
		void onDataChange(String data);
	}
	
}
