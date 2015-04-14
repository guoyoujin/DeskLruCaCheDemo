package com.example.androidddpmeteor;

import im.delight.android.ddp.MeteorCallback;
import im.delight.android.ddp.ResultListener;
import com.alibaba.fastjson.*;
import com.example.androidddpmeteor.view.FButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class DoctorLoginActivity extends BaseActivity implements OnClickListener,MeteorCallback {
	private EditTextWithDel doctorLoginNameEditText,doctorLoginPasswordEditText;
	private FButton fButton_register,fButton_login;
	private DoctorUser doctoUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_doctor_login);
		setTitle("登录");
		initView();	
		finishActivity_btn_left();
	}
	public void initView(){
		doctorLoginNameEditText=(EditTextWithDel) findViewById(R.id.doctorLoginNameEditText);
		doctorLoginPasswordEditText=(EditTextWithDel) findViewById(R.id.doctorLoginPasswordEditText);
		fButton_register=(FButton) findViewById(R.id.fButton_register);
		fButton_register.setOnClickListener(this);
		fButton_login=(FButton) findViewById(R.id.fButton_login);
		fButton_login.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.fButton_register:
			Intent intentRegister=new Intent(getApplicationContext(),DoctorRegisterActivity.class);
			startActivity(intentRegister);
			break;
		case R.id.fButton_login:
			namePasswordIsNull();
			break;

		default:
			break;
		}
		
	}
	public void namePasswordIsNull(){
		String username=doctorLoginNameEditText.getText().toString().trim();
		String password=doctorLoginPasswordEditText.getText().toString().trim();
		if((username!=null&&!username.equals(""))&&(password!=null&&!password.equals(""))){
			login(username, password);
		}else{
			Toast.makeText(getApplicationContext(), "请输入用户名和密码", Toast.LENGTH_SHORT).show();
		}
	}
	public void login(final String username,final String password){
		mMeteor.loginWithUsername(username, password, new ResultListener() {		
			@Override
			public void onSuccess(String result) {
				Log.e("TAG", "login========="+result);
				doctoUser = JSON.parseObject(result, DoctorUser.class);
				doctoUser.setName(username);
				doctoUser.setPassword(password);
				Log.e("TAG", "user===="+doctoUser.toString());
				Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
			}
			public void onError(String error, String reason, String details) {
				Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
				Log.e("TAG", "error=="+error+"====reason==="+reason+"===details===="+details);
			}
		});
	}
}
