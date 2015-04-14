package com.example.androidddpmeteor;

import im.delight.android.ddp.ResultListener;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.androidddpmeteor.view.Bodies;
import com.example.androidddpmeteor.view.ExamPart;

public class ChooseExamPartActivity extends BaseActivity {
	private ExpandableListView expandableListView_exam_part;
	private ArrayList<Exam> exam_list=new ArrayList<Exam>();
	private int hospital_index=-1;
	private int exam_id=-1;
	private ArrayList<Exam>choose_exam_part_list=new ArrayList<Exam>();
	private ArrayList<ExamPart> examParts_list=new ArrayList<ExamPart>();
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_choose_exam_part);
		finishActivity_btn_left();
		exam_id=getIntent().getIntExtra("exam_id", 0);
		exam_list=(ArrayList<Exam>) getIntent().getSerializableExtra("exam_list");
		hospital_index=getIntent().getIntExtra("hospital_index", -1);
		Log.e("TAG", exam_list.get(exam_id).getHospital().get(hospital_index).toString().toString());
		if(hospital_index!=-1){
			mMeteor.call("examPartsCount", new Object[]{exam_list.get(exam_id).getHospital().get(hospital_index).getHospital_id()},new ResultListener() {			
				@Override
				public void onSuccess(String result) {
					Log.e("TAG", "result==========="+result);
					String json=(String) JSON.parse(result);
					ArrayList<ExamPart> examPart_list=new ArrayList<ExamPart>();
					try {
						JSONObject jsonObjects=new JSONObject(json);
						JSONArray jsonArrays=jsonObjects.optJSONArray("exams");
						JSONObject jsonObject=jsonArrays.optJSONObject(0);
						JSONArray jsonArray=jsonObject.optJSONArray("categories");
						int length=jsonArray.length();
						for(int i=0;i<length;i++){
							JSONObject jsonObject2=jsonArray.optJSONObject(i);
							ExamPart exampart=new ExamPart();
							exampart.setName(jsonObject2.optString("name"));
							JSONArray jsonArray2=jsonObject2.optJSONArray("bodies");
							int length_bodies=jsonArray2.length();
							ArrayList<Bodies> bodies_list=new ArrayList<Bodies>();
							for(int j=0; j<length_bodies;j++){
								JSONObject jsonObject3=jsonArray2.optJSONObject(j);
								Bodies bodies=new Bodies();
								bodies.setName(jsonObject3.optString("name"));
								bodies.setPrice(jsonObject3.optInt("price",0));
								bodies_list.add(bodies);
							}
							exampart.setBoides(bodies_list);
							examPart_list.add(exampart);
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg=new Message();
					msg.what=1;
					msg.obj=examPart_list;
					handler.sendMessage(msg);
					
				}			
				@Override
				public void onError(String error, String reason, String details) {
					// TODO Auto-generated method stub
					Log.e("TAG", "error==========="+error);
					Log.e("TAG", "reason==========="+reason);
					Log.e("TAG", "details==========="+details);
				}
			});
		}
		initView();
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				
				break;
			case 1:
				examParts_list=(ArrayList<ExamPart>) msg.obj;
				for(int i=0;i<examParts_list.size();i++){
					Log.e("TAG", examParts_list.get(i).getName());
				}
				ExpandableListAdapter adapter=new ExpandableListAdapter();
				expandableListView_exam_part.setAdapter(adapter);
				break;

			default:
				break;
			}
			
		};
	};
	public void initView(){
		expandableListView_exam_part=(ExpandableListView) findViewById(R.id.expandableListView_exam_part);
		getbtn_right().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("exParts_list", examParts_list);
				ChooseExamPartActivity.this.setResult(
						Result.CHOOSE_EXAM_PART, intent);
				ChooseExamPartActivity.this.finish();
				
			}
		});
	}
	
	public class ExpandableListAdapter extends BaseExpandableListAdapter{
         //自己定义一个获得文字信息的方法  
         TextView getTextView() {  
             AbsListView.LayoutParams lp = new AbsListView.LayoutParams(  
                     ViewGroup.LayoutParams.MATCH_PARENT, 64);  
             TextView textView = new TextView(ChooseExamPartActivity.this);  
             textView.setLayoutParams(lp);  
             textView.setGravity(Gravity.CENTER_VERTICAL);  
             textView.setPadding(36, 0, 0, 0);  
             textView.setTextSize(20);  
             textView.setTextColor(Color.BLACK);  
             return textView;  
         }  
		 @Override  
         public int getGroupCount() {  
             return examParts_list.size();  
         }  

         @Override  
         public Object getGroup(int groupPosition) { 
        	 Log.e("TAG", examParts_list.get(groupPosition).getName());
             return examParts_list.get(groupPosition).getName();  
         }  

         @Override  
         public long getGroupId(int groupPosition) {  
             return groupPosition;  
         }  

         @Override  
         public int getChildrenCount(int groupPosition) {  
             return examParts_list.get(groupPosition).getBoides().size();  
         }  

         @Override  
         public Object getChild(int groupPosition, int childPosition) {  
             return examParts_list.get(groupPosition).getBoides().get(childPosition).getName();  
         }  

         @Override  
         public long getChildId(int groupPosition, int childPosition) {  
             return childPosition;  
         }  

         @Override  
         public boolean hasStableIds() {  
             return true;  
         }  

         @Override  
         public View getGroupView(int groupPosition, boolean isExpanded,  
                 View convertView, ViewGroup parent) {  
             LinearLayout ll = new LinearLayout(getApplicationContext());  
             ll.setOrientation(0);  
             TextView textView = getTextView();  
             textView.setTextColor(Color.BLACK);  
             textView.setText(getGroup(groupPosition).toString()+"");  
             ll.addView(textView);  

             return ll;  
         }  

         @Override  
         public View getChildView(final int groupPosition, final int childPosition,  
                 boolean isLastChild, View convertView, ViewGroup parent) {  
             View view=LinearLayout.inflate(getApplicationContext(), R.layout.exam_part_list_check, null);
             final CheckBox checkBox1=(CheckBox) view.findViewById(R.id.checkBox1);
             checkBox1.setText(getChild(groupPosition, childPosition).toString());  
             checkBox1.setChecked(examParts_list.get(groupPosition).getBoides().get(childPosition).getisIschecked());
             view.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View arg0) {
					if(examParts_list.get(groupPosition).getBoides().get(childPosition).getisIschecked()){
						examParts_list.get(groupPosition).getBoides().get(childPosition).setIschecked(false);
		        		 checkBox1.setChecked(false);
		        	 }else{
		        		 examParts_list.get(groupPosition).getBoides().get(childPosition).setIschecked(true);
		        		 checkBox1.setChecked(true);
		        	 }
				}
			});
             return view;  
         }  

         @Override  
         public boolean isChildSelectable(int groupPosition,int childPosition) { 
        	 Toast.makeText(getApplicationContext(), "groupPosition="+groupPosition+"==childPosition="+childPosition, Toast.LENGTH_SHORT).show();
             return true;  
         }  
		
	}
}
