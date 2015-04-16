package com.example.appservicedemo2;

import com.example.appservicesdemo1.aidl.app.AppServiceRemoteBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener, ServiceConnection {
	Intent i = new Intent();
	private EditText editText1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.findViewById(R.id.btnStartService).setOnClickListener(this);
		this.findViewById(R.id.btnStopService).setOnClickListener(this);	
		this.findViewById(R.id.btn_bind).setOnClickListener(this);
		this.findViewById(R.id.btn_unbind).setOnClickListener(this);
		i.setComponent(new ComponentName("com.example.appservicesdemo1",
				"com.example.appservicesdemo1.AppServices"));
		editText1=(EditText)this.findViewById(R.id.editText1);	
		this.findViewById(R.id.btn_insert).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btnStartService:

			startService(i);
			break;
		case R.id.btnStopService:

			stopService(i);
			break;
			
			
		case R.id.btn_bind:
			bindService(i, this, Context.BIND_AUTO_CREATE);
			break;
			
			
		case R.id.btn_unbind:
			unbindService(this);
			break;
		case R.id.btn_insert:
			if(binder!=null){
				try {
					binder.setData(editText1.getText().toString());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onServiceConnected(ComponentName arg0, IBinder arg1) {
		// TODO Auto-generated method stub
		Log.e("Tag", "BindServic=======e"+arg1);
		binder=AppServiceRemoteBinder.Stub.asInterface(arg1);
	}

	@Override
	public void onServiceDisconnected(ComponentName arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private AppServiceRemoteBinder binder=null;
	
	

}
