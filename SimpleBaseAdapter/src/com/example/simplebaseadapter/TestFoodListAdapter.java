package com.example.simplebaseadapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TestFoodListAdapter extends SimpleBaseAdapter<String>{
	/**
	 * ���ﴫ���data��T�ͣ�������Լ�����
	 * getItemResource()�����listview����
	 * @param context
	 * @param data
	 */
	 public TestFoodListAdapter(Context context, List<String> data) {
	        super(context, data);
	    }
	 	
	    @Override
	    public int getItemResource() {
	        return R.layout.listitem_test;
	    }
	    /**
	     * setConvertView
	     */
	    @Override
	    public View getItemView(int position, View convertView, ViewHolder holder) {
	        TextView text = holder.getView(R.id.textView1);
	        text.setText(data.get(position));
	        return convertView;
	    }

}
