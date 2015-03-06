package com.example.myservicedemo;

import com.example.myservicedemo.service.MyService;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	private Button start_service;
	private Button stop_service;
	private Button start_downLoad;
	private Button start_http;
	private Button unbindService;
	private Button bindService;
	private TextView textView1;
	private MyService.MyBinder binder;//注意MyBinder类必须为public
	private String url="";
	private String state="";//设置一个状态
	private ServiceConnection serviceConnection=null;
	private ComponentName componentName=null;
	private int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initServiceConnection();
	}
	/**
	 * 初始化控件
	 */
	public void initView(){
		textView1=(TextView) findViewById(R.id.textView1);
		start_service=(Button)findViewById(R.id.start_service);
		start_service.setOnClickListener(this);
		stop_service=(Button) findViewById(R.id.stop_service);
		stop_service.setOnClickListener(this);
		start_downLoad=(Button)findViewById(R.id.start_downLoad);
		start_downLoad.setOnClickListener(this);
		start_http=(Button)findViewById(R.id.start_http);
		start_http.setOnClickListener(this);
		bindService=(Button)findViewById(R.id.bindService);
		bindService.setOnClickListener(this);
		unbindService=(Button)findViewById(R.id.unbindService);
		unbindService.setOnClickListener(this);
	}
	/**
	 * 操作Service
	 */
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.start_service:
			//如果未启动服务，则Service生命周期为onCreate(),onStartCommand()
			//如果服务已经启动，并且未被stopService(),那么启动时生命周期为onStartCommand()
			//可以看出，onCreate()方法跟activity里面的OnCreate()方法基本上差不多,都是初始化用的
			Intent startIntent = new Intent(this, MyService.class); 
			bindService(startIntent, serviceConnection, BIND_AUTO_CREATE); 
            startService(startIntent); 
            i=1;
			break;
		case R.id.stop_service:
			//如果服务启动了，则执行onDestroy()生命周期，否则不执行
			Intent stopIntent = new Intent(this, MyService.class); 
            stopService(stopIntent);
            i=0;
		break;
		case R.id.start_downLoad:
			state="start_downLoad";
			if(i==0){
				Intent intent = new Intent(this, MyService.class); 
				bindService(intent, serviceConnection, BIND_AUTO_CREATE); 
			}
			break;
		case R.id.start_http:
			state="start_http";
			if(i==0){
				Intent intent = new Intent(this, MyService.class); 
				bindService(intent, serviceConnection, BIND_AUTO_CREATE); 
			}
			break;
			
		case R.id.bindService:
			Intent bindIntent = new Intent(this, MyService.class);  
            bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);  
			break;
		case R.id.unbindService:
			 unbindService(serviceConnection);  
			break;
		default:
			
			break;
		}	
	}
	private void initServiceConnection(){
		//判断是否启动服务，避免报错		
		serviceConnection=new ServiceConnection() {
			public void onServiceDisconnected(ComponentName arg0) {
				
			}			
			public void onServiceConnected(ComponentName arg0, IBinder service) {
				componentName=arg0;
				binder=(MyService.MyBinder) service;
				binder.startDownLoad(url);
				binder.startSendHttp(url);
				//String sp=(state.equals("start_download"))?binder.startDownLoad(url) : binder.startSendHttp(url);
			}
		};
	}

}
