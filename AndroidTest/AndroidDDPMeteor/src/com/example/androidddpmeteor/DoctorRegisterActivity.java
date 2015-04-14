package com.example.androidddpmeteor;

import im.delight.android.ddp.ResultListener;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.androidddpmeteor.view.FButton;

public class DoctorRegisterActivity extends BaseActivity implements OnClickListener {
	private EditTextWithDel doctorRegisterNameEditText,doctorRegisterPasswordEditText;
	private FButton fButton_register,fButton_Register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_doctor_register);
		setTitle("注册");
		initView();
		finishActivity_btn_left();
	}

	public void initView(){
		doctorRegisterNameEditText=(EditTextWithDel) findViewById(R.id.doctorRegisterNameEditText);
		doctorRegisterPasswordEditText=(EditTextWithDel) findViewById(R.id.doctorRegisterPasswordEditText);
		fButton_register=(FButton) findViewById(R.id.fButton_register);
		fButton_register.setOnClickListener(this);
		fButton_Register=(FButton) findViewById(R.id.fButton_Register);
		fButton_Register.setOnClickListener(this);
	}
	public void namePasswordIsNull(){
		String username=doctorRegisterNameEditText.getText().toString().trim();
		String password=doctorRegisterPasswordEditText.getText().toString().trim();
		if((username!=null&&!username.equals(""))&&(password!=null&&!password.equals(""))){
			Register(username, password);
		}else{
			Toast.makeText(getApplicationContext(), "请输入用户名和密码", Toast.LENGTH_SHORT).show();
		}
	}
	public void Register(final String username,final String password){
//		mMeteor.registerAndLogin(username, null, password, new ResultListener() {		
//			@Override
//			public void onSuccess(String result) {
//				// TODO Auto-generated method stub
//				Log.e("TAG", "result====="+result);
//				Toast.makeText(getApplicationContext(), "这册成功", Toast.LENGTH_SHORT).show();
//
//			}			
//			@Override
//			public void onError(String error, String reason, String details) {
//				// TODO Auto-generated method stub
//				Log.e("TAG", "error=="+error+"====reason==="+reason+"===details===="+details);
//				Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
//			}
//		});
		HashMap<String,Object> profile=new HashMap<String, Object>();
		profile.put("name", username);
		profile.put("mobile", username);
		profile.put("name", username);
		//profile.put("username", username);
		mMeteor.registerAndLogin(username, null, password, profile, new ResultListener() {
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Log.e("TAG", "result===="+result.toString());
				if(result!=null){
					Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onError(String error, String reason, String details) {
				// TODO Auto-generated method stub
				Log.e("TAG", "error=="+error+"====reason==="+reason+"===details===="+details);
				Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.fButton_register:
			Intent intent=new Intent(getApplicationContext(),DoctorLoginActivity.class);
			startActivity(intent);		
			break;
		case R.id.fButton_Register:
			namePasswordIsNull();
			break;
		default:
			break;
		}
	}

}
