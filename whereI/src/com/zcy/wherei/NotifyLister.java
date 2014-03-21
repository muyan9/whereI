package com.zcy.wherei;
import android.os.Vibrator;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDNotifyListener;

/**
	 * 位置提醒回调函数
	 */
	public class NotifyLister extends BDNotifyListener{
		public Vibrator mVibrator01;
		public void onNotify(BDLocation mlocation, float distance){
			mVibrator01.vibrate(1000);
		}
	}