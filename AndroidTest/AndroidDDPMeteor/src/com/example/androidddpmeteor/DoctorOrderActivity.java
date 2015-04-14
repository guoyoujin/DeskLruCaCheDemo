package com.example.androidddpmeteor;

import im.delight.android.ddp.ResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidddpmeteor.view.Bodies;
import com.example.androidddpmeteor.view.ExamPart;
import com.example.androidddpmeteor.view.Order;

public class DoctorOrderActivity extends BaseActivity {
	@SuppressWarnings("unused")
	private TextView tv_hospital,tv_exam,tv_exam_name,textView_prices;
	private Button button_reset,button_post;
	@SuppressWarnings("unused")
	private EditTextWithDel patient_phone,patient_name,patient_remark;
	private int exam_id=0;
	private ArrayList<Exam> exam_list=new ArrayList<Exam>();
	private LinearLayout choose_hospital_intent,choose_exam_part_intent;
	private int hospital_index=-1;
	private ArrayList<ExamPart> examParts_list=new ArrayList<ExamPart>();
	private int prices=0;
	private ArrayList<Bodies>bodies_Arraylist=new ArrayList<Bodies>();
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_doctor_order);
		exam_id=getIntent().getIntExtra("exam_id", 0);
		exam_list=(ArrayList<Exam>) getIntent().getSerializableExtra("exam_list");
		setTitle("咨询详单");
		initView();
	}
	
	public void initView(){
		patient_remark=(EditTextWithDel) findViewById(R.id.patient_remark);
		textView_prices=(TextView) findViewById(R.id.textView_prices);
		button_reset=(Button) findViewById(R.id.button_reset);
		button_reset.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		button_post=(Button) findViewById(R.id.button_post);
		button_post.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				senorder();	
			}
		});
		patient_phone=(EditTextWithDel) findViewById(R.id.patient_phone);
		patient_name=(EditTextWithDel) findViewById(R.id.patient_name);
		tv_hospital=(TextView) findViewById(R.id.tv_hospital);
		tv_exam=(TextView) findViewById(R.id.tv_exam);
		tv_exam_name=(TextView) findViewById(R.id.tv_exam_name);
		tv_exam_name.setText(exam_list.get(exam_id).getName());
		choose_hospital_intent=(LinearLayout) findViewById(R.id.choose_hospital_intent);
		choose_hospital_intent.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(getApplicationContext(),ChooseHospitalActivity.class);
				intent.putExtra("exam_id", exam_id);
				intent.putExtra("hospital_index", hospital_index);
				intent.putParcelableArrayListExtra("exam_list", (ArrayList<? extends Parcelable>) exam_list);
				startActivityForResult(intent, Result.CHOOSE_HOSPITAL);
				
			}
		});
		choose_exam_part_intent=(LinearLayout) findViewById(R.id.choose_exam_part_intent);
		choose_exam_part_intent.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(getApplicationContext(),ChooseExamPartActivity.class);
				intent.putExtra("exam_id", exam_id);
				intent.putExtra("hospital_index", hospital_index);
				intent.putParcelableArrayListExtra("exam_list", (ArrayList<? extends Parcelable>) exam_list);
				startActivityForResult(intent, Result.CHOOSE_EXAM_PART);
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("TAG", requestCode+"====="+resultCode+"===="+data.toString());
		if(resultCode==Result.CHOOSE_HOSPITAL){
			hospital_index=data.getIntExtra("hospital_index", -1);
			if(hospital_index!=-1){
				tv_hospital.setText("选择医院      "+exam_list.get(exam_id).getHospital().get(hospital_index).getHospital_name());
			}
		}else if(resultCode==Result.CHOOSE_EXAM_PART){
			examParts_list=(ArrayList<ExamPart>) data.getSerializableExtra("exParts_list");
			Log.e("TAG", "======examParts_list====="+examParts_list.toString().toString()+examParts_list.size());
			String tx="选择部位\n";
			for(int i=0;i<examParts_list.size();i++){
				ArrayList<Bodies>boid_list=new ArrayList<Bodies>();
				boid_list=examParts_list.get(i).getBoides();
				bodies_Arraylist.addAll(boid_list);
				for(int j=0;j<boid_list.size();j++){
					if(boid_list.get(j).getisIschecked()){
						tx+=boid_list.get(j).getName()+"\n";
						prices+=boid_list.get(j).getPrice();
					}
				}
			}
			tv_exam.setText(tx);
			textView_prices.setText("快诊咨询费 "+prices+"元");
		}
	}
	
	public void senorder(){
		String patientPhone=patient_phone.getText().toString();
		String patientNname=patient_name.getText().toString();
		String remark=patient_remark.getText().toString();
		Order order=new Order();
		order.setPatient_name(patientNname);
		order.setPatient_phone(patientPhone);
		order.setHospital(exam_list.get(exam_id).getHospital().get(hospital_index).getHospital_name());
		order.setRemark(remark);
		order.setDoctor_id("uND7eBtYcG35RoxnS");
		order.setPrice(prices);
		order.setExam(exam_list.get(exam_id).getName());
		order.setBodies(bodies_Arraylist);
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("patient_name", patientNname);
		Log.e("TAG", order.toString());
		mMeteor.call("ApporderInsert", new Object[]{order}, new ResultListener() {	
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Log.e("TAG", "result==="+result.toString());
				if(result!=null){
					Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
				}
			}
			@Override
			public void onError(String error, String reason, String details) {
				Log.e("TAG", "error==="+error.toString()+"===reason=="+reason+"====details==="+details);
			}
		});
	}

}
