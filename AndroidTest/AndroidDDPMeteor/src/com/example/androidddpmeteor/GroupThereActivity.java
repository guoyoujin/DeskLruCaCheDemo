package com.example.androidddpmeteor;

import org.codehaus.jackson.map.deser.ValueInstantiators.Base;

import com.example.androidddpmeteor.view.FButton;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class GroupThereActivity extends BaseActivity implements OnClickListener {
	private FButton fButton_login_three,fButton_register_three,fButton_certification_three,fButton_account_balance,fButton_amount_paid,
	fButton_Not_paying,fButton_paying,fButton_method_of_payment,fButton_setting,fButton_opinion,fButton_Contact_us;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_group_there);
		setTitle("我的账户");
		hidebtn_left();
		hidebtn_right();
		initView();
	}
	
	
	
	public void initView(){
		fButton_login_three=(FButton) findViewById(R.id.fButton_login_three);
		fButton_login_three.setOnClickListener(this);
		
		fButton_register_three=(FButton) findViewById(R.id.fButton_register_three);
		fButton_register_three.setOnClickListener(this);

		fButton_certification_three=(FButton) findViewById(R.id.fButton_certification_three);
		fButton_certification_three.setOnClickListener(this);

		fButton_account_balance=(FButton) findViewById(R.id.fButton_account_balance);
		fButton_account_balance.setOnClickListener(this);

		fButton_amount_paid=(FButton) findViewById(R.id.fButton_amount_paid);
		fButton_amount_paid.setOnClickListener(this);

		fButton_Not_paying=(FButton) findViewById(R.id.	fButton_Not_paying);
		fButton_Not_paying.setOnClickListener(this);

		fButton_paying=(FButton) findViewById(R.id.fButton_paying);
		fButton_paying.setOnClickListener(this);

		fButton_method_of_payment=(FButton) findViewById(R.id.fButton_method_of_payment);
		fButton_method_of_payment.setOnClickListener(this);

		fButton_setting=(FButton) findViewById(R.id.fButton_setting);
		fButton_login_three.setOnClickListener(this);

		fButton_opinion=(FButton) findViewById(R.id.fButton_opinion);
		fButton_opinion.setOnClickListener(this);

		fButton_Contact_us=(FButton) findViewById(R.id.fButton_Contact_us);
		fButton_Contact_us.setOnClickListener(this);

	}
	

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.fButton_login_three:
			Intent intent=new Intent(getApplicationContext(),DoctorLoginActivity.class);
			startActivity(intent);
			
			break;
		case R.id.fButton_register_three:
			Intent intentRegister=new Intent(getApplicationContext(),DoctorRegisterActivity.class);
			startActivity(intentRegister);
			break;
		case R.id.fButton_certification_three:
			
			break;
		case R.id.fButton_account_balance:
			
			break;
		case R.id.fButton_amount_paid:
			
			break;
		case R.id.fButton_Not_paying:
			
			break;
		case R.id.fButton_paying:
			
			break;
		case R.id.fButton_method_of_payment:
			
			break;
		case R.id.fButton_setting:
			
			break;
		case R.id.fButton_opinion:
			
			break;
		case R.id.fButton_Contact_us:
			
			break;
		default:
			break;
		}
		
	}

}
