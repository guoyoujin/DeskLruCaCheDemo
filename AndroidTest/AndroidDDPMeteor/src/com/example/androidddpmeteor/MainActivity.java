package com.example.androidddpmeteor;


import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends ActivityGroup implements OnCheckedChangeListener {
	
	private static final String RECORD = GroupOneActivity.class.getName();
	private static final String CATEGORY = GroupTwoActivity.class.getName();
	private static final String MORE = GroupThereActivity.class.getName();
	private ActivityGroupManager manager = null;
	private FrameLayout container = null;
	private RadioGroup radioGroup;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    private void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioGroup.setOnCheckedChangeListener(this);
		manager = new ActivityGroupManager();
		container = (FrameLayout) findViewById(R.id.container);
		manager.setContainer(container);
		switchActivity(ActivityGroupManager.RECORD_ACTIVITY_VIEW, RECORD, GroupOneActivity.class);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		Log.e("TAG", "======="+checkedId);
		switch (checkedId) {		
		case R.id.record_button:
			switchActivity(ActivityGroupManager.RECORD_ACTIVITY_VIEW, RECORD, GroupOneActivity.class);
			break;
		case R.id.category_button:
			switchActivity(ActivityGroupManager.CATEGORY_ACTIVITY_VIEW, CATEGORY, GroupTwoActivity.class);
			break;
		case R.id.more_button:
			switchActivity(ActivityGroupManager.MORE_AVTIVITY_VIEW, MORE, GroupThereActivity.class);
			break;			
	
		default:
			break;
		}
	}
	
	private View getActivityView(String activityName, Class<?> activityClass) {
		return getLocalActivityManager().startActivity(activityName,new Intent(MainActivity.this, activityClass)).getDecorView();
	}

	private void switchActivity(int num, String activityName, Class<?> activityClass) {
		manager.showContainer(num, getActivityView(activityName, activityClass));
	}
    
}
