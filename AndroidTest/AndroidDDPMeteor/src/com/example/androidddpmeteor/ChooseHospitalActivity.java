package com.example.androidddpmeteor;

import im.delight.android.ddp.ResultListener;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ChooseHospitalActivity extends BaseActivity {
	private ListView lv_choose_hispital;
	private int exam_id=0;
	private ArrayList<Exam> exam_list=new ArrayList<Exam>();
	private ArrayList<Hospital>hospital_list=new ArrayList<Hospital>();
	private int hospital_index=-1;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_choose_hospital);
		exam_id=getIntent().getIntExtra("exam_id", 0);
		exam_list=(ArrayList<Exam>) getIntent().getSerializableExtra("exam_list");
		hospital_list=(ArrayList<Hospital>) exam_list.get(exam_id).getHospital();
		hospital_index=getIntent().getIntExtra("hospital_index", 0);
		initView();
	}
	
	
	public void initView(){
		lv_choose_hispital=(ListView) findViewById(R.id.lv_choose_hispital);
		lv_choose_hispital.setAdapter(new TestFoodListAdapter(getApplicationContext(), hospital_list));
	}
	
	
	
	
	public class TestFoodListAdapter extends SimpleBaseAdapter<Hospital> {		
	    public TestFoodListAdapter(Context context, List<Hospital> data) {
	        super(context, data);
	    }
	    @Override
	    public int getItemResource() {
	        return R.layout.choose_hospital_list;
	    }
	    @Override
	    public View getItemView(final int position, View convertView, ViewHolder holder) {
	    	final CheckBox btn_list_exam = holder.getView(R.id.checkBox1_hospital_list);
	    	LinearLayout linear_choose_hospital=holder.getView(R.id.linear_choose_hospital);
	    	btn_list_exam.setText(hospital_list.get(position).getHospital_name());
	    	if(position==hospital_index){
	    		btn_list_exam.setChecked(true);
	    	}else{
	    		btn_list_exam.setChecked(false);
	    	}
	    	linear_choose_hospital.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View arg0) {
					btn_list_exam.setChecked(btn_list_exam.isChecked()? false:true);
					if(btn_list_exam.isChecked()){
						Intent intent = new Intent();
						intent.putExtra("hospital_index", position);
						ChooseHospitalActivity.this.setResult(
								Result.CHOOSE_HOSPITAL, intent);
						ChooseHospitalActivity.this.finish();
					}

				}
			});
	    	
	        return convertView;
	    }
	}
}
