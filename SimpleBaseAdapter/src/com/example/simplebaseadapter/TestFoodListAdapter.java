package com.example.simplebaseadapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TestFoodListAdapter extends SimpleBaseAdapter<String>{
	/**
	 * 这里传入的data是T型，你可以自己定义
	 * getItemResource()是你的listview布局
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
