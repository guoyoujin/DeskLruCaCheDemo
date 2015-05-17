package com.example.testcutontopview;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.cutontopviewlibrary.Crouton;
import com.example.cutontopviewlibrary.Style;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void onClick(View view){
    	Style croutonStyle = Style.ALERT;
    	switch (view.getId()) {
		case R.id.button1:
			Crouton.makeText(MainActivity.this, "网络为链接", croutonStyle).show();
			//Crouton.makeText(Activity, CharSequence, Style).show();
			//Crouton.makeText(Activity, int, Style).show();
			//Crouton.makeText(Activity, int, Style, int).show();
			//也可以自定义布局
			//Crouton.makeText(Activity, int, Style, ViewGroup).show();
			break;

		case R.id.button2:
			ViewGroup views=(ViewGroup) LayoutInflater.from(getApplicationContext()).inflate(R.layout.crouton_custom_view, null);
			Crouton.makeText(MainActivity.this, "===", croutonStyle, views).show();
			break;
		}
    }
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	//   注意一定要在activity destroy的时候调用该方法哦
    	Crouton.cancelAllCroutons();
    }   
}
