package com.example.androidddpmeteor;

import im.delight.android.ddp.ResultListener;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.androidddpmeteor.view.Order;
import com.example.androidddpmeteor.view.Orders;

public class GroupTwoActivity extends BaseActivity {
	private ListView listView_order;
	private ArrayList<Orders> order_arrayList=new ArrayList<Orders>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_group_two);
		setTitle("订单详情");
		hidebtn_left();
		hidebtn_right();
		order_list();
		initView();
	}
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				TestFoodListAdapter adapter=new TestFoodListAdapter(context, order_arrayList);
				listView_order.setAdapter(adapter);
				break;
			default:
				break;
			}
		};
	};
	public void initView(){
		listView_order=(ListView) findViewById(R.id.listView_order);
	}
	
	public void order_list(){
		mMeteor.call("AppOrderList", new Object[]{"uND7eBtYcG35RoxnS"}, new ResultListener() {			
			@Override
			public void onSuccess(String result) {
				String json=(String) JSON.parse(result);
				Log.e("TAG", "result_order========"+json);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(json);
					org.json.JSONArray jsonArray=jsonObject.optJSONArray("order");
					for(int i=0;i<jsonArray.length();i++){
						JSONObject jsonObject2=jsonArray.optJSONObject(i);
						Orders order=new Orders();
						order.setExam(jsonObject2.optString("exam"));
						order.setHospital(jsonObject2.optString("hospital"));
						order.setPatient_name(jsonObject2.optString("patient_name"));
						order.setPatient_phone(jsonObject2.optString("patient_phone"));
						order.setPrice(jsonObject2.optInt("price"));
						order.setRemark(jsonObject2.optString("remark"));
						order.setState(jsonObject2.optInt("state"));
						order.setOrder_code(jsonObject2.optString("order_code"));
						order_arrayList.add(order);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg=new Message();
				msg.what=1;
				handler.sendMessage(msg);
				
			}
			
			@Override
			public void onError(String error, String reason, String details) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public class TestFoodListAdapter extends SimpleBaseAdapter<Orders> {		
	    public TestFoodListAdapter(Context context, ArrayList<Orders> data) {
	        super(context, data);
	    }
	    @Override
	    public int getItemResource() {
	        return R.layout.order_list_view;
	    }
	    @Override
	    public View getItemView(final int position, View convertView, ViewHolder holder) {
	        TextView textView_order_state = holder.getView(R.id.textView_order_state);
	        TextView textView_order_code = holder.getView(R.id.textView_order_code);
	        TextView textView_order_patient_info = holder.getView(R.id.textView_order_patient_info);
	        TextView textView_order_exam = holder.getView(R.id.textView_order_exam);
	        TextView textView_order_hospital = holder.getView(R.id.textView_order_hospital);
	        TextView textView_order_price = holder.getView(R.id.textView_order_price);
	        if(order_arrayList.get(position).getState()==0){
		        textView_order_state.setText("未处理");
	        }else if(order_arrayList.get(position).getState()==1){
		        textView_order_state.setText("处理中");
	        }else if(order_arrayList.get(position).getState()==2){
		        textView_order_state.setText("已完成");
	        }else if(order_arrayList.get(position).getState()==3){
		        textView_order_state.setText("已付款");
	        }
	        textView_order_code.setText("订单编号: "+order_arrayList.get(position).getOrder_code()+"");
	        textView_order_patient_info.setText("患者姓名:  "+order_arrayList.get(position).getPatient_name()+"      患者电话: "+order_arrayList.get(position).getPatient_phone());
	        textView_order_exam.setText("检查项目: "+order_arrayList.get(position).getExam());
	        textView_order_hospital.setText("所在医院: "+order_arrayList.get(position).getHospital());
	        textView_order_price.setText("快诊咨询费: "+order_arrayList.get(position).getPrice()+"");
	        return convertView;
	    }
	}
}
