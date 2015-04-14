package com.example.androidddpmeteor;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
* 继承于Activity用于以后方便管理
* 
* @author coder
* 
*/
public class BaseActivity extends Activity implements MeteorCallback {
        private TextView tv_title;
        private TextView btn_left, btn_right;
        private LinearLayout linout_title_bar;
        private LinearLayout ly_content;
        public Meteor mMeteor;
        // 内容区域的布局
        private View contentView;
        public Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                //requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.common_title);
                if(mMeteor==null||!mMeteor.isConnected()){
                	mMeteor = new Meteor("ws://guoyoujinlove123456.meteor.com/websocket");
                	mMeteor.setCallback(this);
            		mMeteor.subscribe("exams");
            		mMeteor.subscribe("hospitals");
            		mMeteor.subscribe("exam_parts");
            		mMeteor.subscribe("doctors");
            		mMeteor.subscribe("orders");
            		mMeteor.subscribe("verification_codes");
                }
        		Log.e("TAG", "=====oncreate()======");
                tv_title = (TextView) findViewById(R.id.tv_title);
                btn_left = (TextView) findViewById(R.id.btn_left);
                btn_right = (TextView)findViewById(R.id.btn_right);
                linout_title_bar=(LinearLayout) findViewById(R.id.linout_title_bar);
                ly_content = (LinearLayout) findViewById(R.id.ly_content);
                if(context==null){
                	context=BaseActivity.this;
                }
        }
        
        /***
         * 设置内容区域
         * 
         * @param resId
         *            资源文件ID
         */
        @SuppressWarnings("deprecation")
		@SuppressLint({ "InlinedApi", "NewApi" })
		public void setContentLayout(int resId) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                contentView = inflater.inflate(resId, null);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
                                LayoutParams.FILL_PARENT);
                contentView.setLayoutParams(layoutParams);
                contentView.setBackgroundDrawable(null);
                if (null != ly_content) {
                        ly_content.addView(contentView);
                }

        }

        /***
         * 设置内容区域
         * 
         * @param view
         *            View对象
         */
        public void setContentLayout(View view) {
                if (null != ly_content) {
                        ly_content.addView(view);
                }
        }

        /**
         * 得到内容的View
         * 
         * @return
         */
        public View getLyContentView() {

                return contentView;
        }

        /**
         * 得到左边的按钮
         * 
         * @return
         */
        public TextView getbtn_left() {
                return btn_left;
        }

        /**
         * 得到右边的按钮
         * 
         * @return
         */
        public TextView getbtn_right() {
                return btn_right;
        }

        /**
         * 设置标题
         * 
         * @param title
         */
        public void setTitle(String title) {

                if (null != tv_title) {
                        tv_title.setText(title);
                }

        }

        /**
         * 设置标题
         * 
         * @param resId
         */
        public void setTitle(int resId) {
                tv_title.setText(getString(resId));
        }

        /**
         * 设置左边按钮的图片资源
         * 
         * @param resId
         */
        public void setbtn_leftRes(int resId) {
                if (null != btn_left) {
                        btn_left.setBackgroundResource(resId);
                }

        }

        /**
         * 设置左边按钮的图片资源
         * 
         * @param bm
         */
        @SuppressWarnings("deprecation")
		public void setbtn_leftRes(Drawable drawable) {
                if (null != btn_left) {
                        btn_left.setBackgroundDrawable(drawable);
                }

        }

        /**
         * 设置右边按钮的图片资源
         * 
         * @param resId
         */
        public void setbtn_rightRes(int resId) {
                if (null != btn_right) {
                        btn_right.setBackgroundResource(resId);
                }
        }

        /**
         * 设置右边按钮的图片资源
         * 
         * @param drawable
         */
        @SuppressWarnings("deprecation")
		public void setbtn_rightRes(Drawable drawable) {
                if (null != btn_right) {
                        btn_right.setBackgroundDrawable(drawable);
                }
        }

        /**
         * 隐藏上方的标题栏
         */
        public void hideTitleView() {

                if (null != linout_title_bar) {
                	linout_title_bar.setVisibility(View.GONE);
                }
        }
        /**
         * 显示上方的标题栏
         */
        public void showTitleView() {

                if (null != linout_title_bar) {
                	linout_title_bar.setVisibility(View.VISIBLE);
                }
        }

        /**
         * 隐藏左边的按钮
         */
        public void hidebtn_left() {

                if (null != btn_left) {
                        btn_left.setVisibility(View.GONE);
                }

        }

        /***
         * 隐藏右边的按钮
         */
        public void hidebtn_right() {
                if (null != btn_right) {
                        btn_right.setVisibility(View.GONE);
                }

        }
        /**
         * 结束当前activity
         * 
         */
        public void finishActivity_btn_left(){
        	btn_left.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
        }
        public BaseActivity() {

        }

		@SuppressWarnings("unchecked")
		public static <T extends View> T getAdapterView(View convertView, int id) {
			SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                convertView.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = convertView.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
        
        public static Bitmap getBitmapFromResources(Activity act, int resId) {
        	Resources res = act.getResources();
        	return BitmapFactory.decodeResource(res, resId);
        }
        
        @SuppressWarnings("deprecation")
		public static Drawable convertBitmap2Drawable(Bitmap bitmap) {
        	BitmapDrawable bd = new BitmapDrawable(bitmap);
        	// 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
        	return bd;
        }

		@Override
		public void onConnect() {
			// TODO Auto-generated method stub
			Log.e("TAG", "====onConnect()====");
			
		}

		@Override
		public void onDisconnect(int code, String reason) {
			// TODO Auto-generated method stub
			Log.e("TAG", "code===="+code+"=====reason===="+reason);
			
		}

		@Override
		public void onDataAdded(String collectionName, String documentID,
				String newValuesJson) {
			// TODO Auto-generated method stub
			Log.e("TAG", "=====collectionName====="+collectionName+"====document====="+documentID);
			//Toast.makeText(context, "onDataAdded====collectionName"+collectionName+"newValuesJson======"+newValuesJson, Toast.LENGTH_SHORT).show();

			
		}

		@Override
		public void onDataChanged(String collectionName, String documentID,
				String updatedValuesJson, String removedValuesJson) {
			// TODO Auto-generated method stub
			Log.e("TAG", "onDataChanged======="+"collectionName======"+collectionName+"documentId======"+documentID);
			//Toast.makeText(context, "onDataChanged====collectionName"+collectionName+"documentID======"+documentID+"===updatedValuesJson==="+updatedValuesJson, Toast.LENGTH_SHORT).show();
			
		}

		@Override
		public void onDataRemoved(String collectionName, String documentID) {
			// TODO Auto-generated method stub
			Log.e("TAG", "onDataRemoved========"+"collectionName======"+collectionName+"documentId======"+documentID);
			//Toast.makeText(context, "remove====collectionName"+collectionName+"documentId======"+documentID, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onException(Exception e) {
			// TODO Auto-generated method stub
			Log.e("TAG", "exception======"+e.toString());
		}
}