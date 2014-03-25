package com.zcy.wherei;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PositionService extends IntentService {

	public PositionService() {
		//必须实现父类的构造方法
		super("IntentServiceDemo");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.w("test","onBind");
		return super.onBind(intent);
	}


	@Override
	public void onCreate() {
		Log.w("test","onCreate");
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.w("test","onStart");
		super.onStart(intent, startId);
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.w("test","onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}


	@Override
	public void setIntentRedelivery(boolean enabled) {
		super.setIntentRedelivery(enabled);
		Log.w("test","setIntentRedelivery");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//Intent是从Activity发过来的，携带识别参数，根据参数不同执行不同的任务
		String action = intent.getExtras().getString("param");
		if (action.equals("oper1")) {
			Log.w("test","Operation1");
		}else if (action.equals("oper2")) {
			Log.w("test","Operation2");
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		Log.w("test","onDestroy");
		super.onDestroy();
	}

}
