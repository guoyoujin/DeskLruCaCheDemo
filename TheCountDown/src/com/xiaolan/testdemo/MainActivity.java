package com.xiaolan.testdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaolan.testdemo.Anticlockwise.OnTimeCompleteListener;

public class MainActivity extends Activity implements OnTimeCompleteListener {

    private TextView mTv;
	private Anticlockwise mTimer;
	private SharedPreferences sp;
	private Editor edt;
	private int hour=10;
	private int min=0;
	private int seconds=0;
	public MyServices.MyBinder binder;//注意MyBinder类必须为public
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent startIntent = new Intent(this, MyServices.class); 
		bindService(startIntent, conn, BIND_AUTO_CREATE); 
        startService(startIntent); 
        initSpAndEdt();
        initView();
    }
	private void initSpAndEdt(){
		sp=getSharedPreferences("Time", Context.MODE_PRIVATE);
		hour=sp.getInt("hour", 10);
		min=sp.getInt("min", 0);
		seconds=sp.getInt("seconds", 0);
		edt=sp.edit();
	}
	
	public void setSp(){
		String time=mTimer.getText().toString();
		int hour=Integer.parseInt(time.substring(0,2));
		int min=Integer.parseInt(time.substring(3,5));
		int seconds=Integer.parseInt(time.substring(6, 8));
		Log.e("TAG", hour+"===="+min+"===="+seconds);
		edt.putInt("hour", hour);
		edt.putInt("min", min);
		edt.putInt("seconds", seconds);
		edt.commit();

	}
	private void initView() {
		mTv = (TextView) findViewById(R.id.tv);
		//初始化文本内容
		mTv.setText("欢迎光临 : ");
		
		mTimer = (Anticlockwise) findViewById(R.id.timer);
		mTimer.setTimeFormat("HH:mm:ss");
		mTimer.setOnTimeCompleteListener(this);
		int h = hour;
		int m = min;
		int s = seconds;
		mTimer.reStart(h*60*60+m*60+s);
		
	}

	/**
	 * 时间到了会回调这个
	 */
	@Override
	public void onTimeComplete() {
		mTv.setText("谢谢惠顾 : ");
		MediaPlayer music;
		music = MediaPlayer.create(this, R.raw.ok);
		music.start();
		Toast.makeText(getApplicationContext(), "时间到...", Toast.LENGTH_LONG).show();
	}
	@Override
	protected void onDestroy() {
		setSp();
		super.onDestroy();
	}
	ServiceConnection conn = new ServiceConnection() {  
        public void onServiceConnected(ComponentName name, IBinder service) {  
            Log.e("TAG", "onServiceConnected");  
            binder=(MyServices.MyBinder) service;
            binder.setTime(hour, min, seconds, mTv);
        }  
        public void onServiceDisconnected(ComponentName name) {  
            Log.e("TAG", "onServiceDisconnected");  
        }  
    }; 
    
    
	

}

