package com.xiaolan.testdemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

/***
 * 
 * @author 张小_懒 2015/02/07
 * 
 */
@SuppressLint({ "ViewConstructor", "SimpleDateFormat" })
public class Anticlockwise extends Chronometer {
	public Anticlockwise(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		// mTimeFormat = new SimpleDateFormat("mm:ss");
		mTimeFormat = new SimpleDateFormat("s");
		this.setOnChronometerTickListener(listener);
	}

	private long mTime;
	private long mNextTime;
	private OnTimeCompleteListener mListener;
	private SimpleDateFormat mTimeFormat;

	public Anticlockwise(Context context) {
		super(context);

	}

	/**
	 * 重新启动计时
	 */
	public void reStart(long _time_s) {
		if (_time_s == -1) {
			mNextTime = mTime;
		} else {
			mTime = mNextTime = _time_s;
		}
		this.start();
	}

	public void reStart() {
		reStart(-1);
	}

	/**
	 * 继续计时
	 */
	public void onResume() {
		this.start();
	}

	/**
	 * 暂停计时
	 */
	public void onPause() {
		this.stop();
	}

	/**
	 * 设置时间格式
	 * 
	 * @param pattern
	 *            计时格式
	 */
	public void setTimeFormat(String pattern) {
		mTimeFormat = new SimpleDateFormat(pattern);
	}

	@Override
	public void start() {
		this.stop();
		super.start();
	}

	public void setOnTimeCompleteListener(OnTimeCompleteListener l) {
		mListener = l;
	}

	OnChronometerTickListener listener = new OnChronometerTickListener() {
		@Override
		public void onChronometerTick(Chronometer chronometer) {
			if (mNextTime <= 0) {
				if (mNextTime == 0) {
					Anticlockwise.this.stop();
					if (null != mListener)
						mListener.onTimeComplete();
				}
				updateTimeText();
				return;
			}

			mNextTime--;

			updateTimeText();
		}
	};

	/**
	 * 初始化时间
	 * 
	 * @param _time_s
	 */
	public void initTime(long _time_s) {
		mTime = mNextTime = _time_s;
		updateTimeText();
	}

	private void updateTimeText() {
		this.setText(getDate(mNextTime * 1000));
	}

	public interface OnTimeCompleteListener {
		void onTimeComplete();
	}


	private String getDate(long l) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		String h, m, s;

		//long day = _time_s / nd;
		// 计算差多少小时
		long hour = l % nd / nh;
		if (hour < 10)
			h = "0" + hour;
		else
			h = "" + hour;
		// 计算差多少分钟
		long min = l % nd % nh / nm;
		if (min < 10)
			m = "0" + min;
		else
			m = "" + min;
		// 计算差多少秒//输出结果
		long sec = l % nd % nh % nm / ns;
		if (sec < 10)
			s = "0" + sec;
		else
			s = "" + sec;
		return h + ":" + m + ":" + s;
	}
}


