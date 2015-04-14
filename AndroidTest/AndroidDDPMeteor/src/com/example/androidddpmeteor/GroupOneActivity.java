package com.example.androidddpmeteor;

import im.delight.android.ddp.MeteorCallback;
import im.delight.android.ddp.ResultListener;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.alibaba.fastjson.JSON;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class GroupOneActivity extends BaseActivity implements MeteorCallback{
	private ArrayList<Exam> exam_list=new ArrayList<Exam>();
	private ListView listView_exams;
	private TestFoodListAdapter adapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_group_one);
		initView();
		setTitle("邀请患者");
		hidebtn_left();
		hidebtn_right();
	}
	public void initView(){
		
		listView_exams=(ListView) findViewById(R.id.listView_exams);
		listView_exams.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent=new Intent(getApplicationContext(), DoctorOrderActivity.class);
				intent.putExtra("exam_id", position);
				intent.putParcelableArrayListExtra("exam_list", (ArrayList<? extends Parcelable>) exam_list);
				startActivity(intent);
			}
		});
		mMeteor.call("exams", new ResultListener() {	
			@Override
			public void onSuccess(String result) {
				Log.e("TAG", "result==========" + result);
				Message msg=new Message();
				msg.what=0;
				msg.obj=result;
				handler.sendMessage(msg);
			}		
			@Override
			public void onError(String error, String reason, String details) {
				Log.e("TAG", "error==========" + error);
				Log.e("TAG", "reason==========" + reason);
				Log.e("TAG", "details==========" + details);				
			}
		});
	}

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result=(String)msg.obj;
				initjson(result);
				break;
			case 1:
				exam_list=(ArrayList<Exam>) msg.obj;
				if(adapter==null){
					 adapter=new TestFoodListAdapter(GroupOneActivity.this, exam_list);
				}else{
					adapter=null;
					adapter=new TestFoodListAdapter(GroupOneActivity.this, exam_list);
				}
				listView_exams.setAdapter(adapter);
				break;
			default:
				break;
			}
		};
	};
	@Override
	public void onConnect() {
		Log.e("TAG", "Connected");
		
		
	}
	public void initjson(final String result){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String results=	(String) JSON.parse(result);
					List<Exam>exams=new ArrayList<Exam>();
					JSONObject jsonObjects=new JSONObject(results);
					JSONArray jsonArray=jsonObjects.optJSONArray("exams");
					for(int i=0;i<jsonArray.length();i++){
						JSONObject jsonObject=jsonArray.optJSONObject(i);
						Exam exam=new Exam();
						exam.set_id(jsonObject.optString("_id"));
						exam.setName(jsonObject.optString("name"));
						JSONArray jsonArray2=jsonObject.optJSONArray("hospitals");
						if(jsonArray2!=null&&jsonArray2.length()>0){
							List<Hospital>hospitals=new ArrayList<Hospital>();
							for(int j=0;j<jsonArray2.length();j++){
								JSONObject jsonObject2=jsonArray2.optJSONObject(j);
								Hospital hospital=new Hospital();
								hospital.setHospital_id(jsonObject2.optString("hospital_id"));
								hospital.setHospital_name(jsonObject2.optString("hospital_name"));
								hospital.setExam_part_id(jsonObject2.optString("exam_part_id"));
								hospitals.add(hospital);
							}
							exam.setHospital(hospitals);
						}
						exams.add(exam);
					}
					Log.e("TAG", exams.toString());
					Message msg=new Message();
					msg.what=1;
					msg.obj=exams;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}).start();
	}

	
	public void onDestroy() {
		super.onDestroy();
		mMeteor.disconnect();
	}
	public class TestFoodListAdapter extends SimpleBaseAdapter<Exam> {		
	    public TestFoodListAdapter(Context context, List<Exam> data) {
	        super(context, data);
	    }
	    @Override
	    public int getItemResource() {
	        return R.layout.exam_list;
	    }
	    @Override
	    public View getItemView(final int position, View convertView, ViewHolder holder) {
	        Button btn_list_exam = holder.getView(R.id.btn_list_exam);
	        btn_list_exam.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(getApplicationContext(), DoctorOrderActivity.class);
					intent.putExtra("exam_id", position);
					intent.putExtra("exam_list", exam_list);
					startActivity(intent);
				}
			});
	        btn_list_exam.setText(exam_list.get(position).getName());
	        return convertView;
	    }
	}

}
