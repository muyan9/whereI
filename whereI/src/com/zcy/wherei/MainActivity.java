package com.zcy.wherei;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MainActivity extends Activity {
	public LocationClient mLocationClient = null;
	public BDLocationListener bdLocationListener = new MyLocationListener();
	TextView tv = null;
	static WebView webview = null;
	LocationClientOption locationClientOption = null;
	private Button btnStart;
	private Button btnAlarm;
	private boolean bFlagStarted = false;
	private NotifyLister notify = new NotifyLister();
	private boolean bFlagNotify = true;
	private boolean bFlagVibrate = false;

	private void initLocationClient() {
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.setAK("t7a1o0UMw1Frwq9xYVOUhkYzq");
		mLocationClient.registerLocationListener(bdLocationListener); // 注册监听函数
	}

	private void setLocationClientOption() {
		if (locationClientOption == null)
			locationClientOption = new LocationClientOption();
		locationClientOption.setOpenGps(true);
		locationClientOption.setAddrType("all");// 返回的定位结果包含地址信息
		locationClientOption.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		TextView txDalayRequestPosition = (TextView) findViewById(R.id.dalayRequestPosition);
		int i = Integer.parseInt(txDalayRequestPosition.getText().toString()
				.trim());
		locationClientOption.setScanSpan(i * 1000);// 设置发起定位请求的间隔时间为5000ms
		locationClientOption.disableCache(true);// 禁止启用缓存定位
		// option.setPoiNumber(5); //最多返回POI个数
		// option.setPoiDistance(1000); //poi查询距离
		// option.setPoiExtraInfo(false); //是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(locationClientOption);
	}

	private void initMapView() {
		webview = (WebView) findViewById(R.id.webView1);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webview.loadUrl("http://115.29.138.66/map.html");
		webview.addJavascriptInterface(new JavaScriptInterface(this), "test");
	}

	public void setup(View view) {
		setLocationClientOption();
	}

	public static void marker(View view) {
		if(Position.code==BDLocation.TypeGpsLocation || Position.code==BDLocation.TypeNetWorkLocation)
			webview.loadUrl(String.format("javascript:myPosition(%s,%s,%s);", Position.lng, Position.lat, Position.radius));
	}

	public void refreshWebPage(View view) {
		webview.reload();
	}

	public void zhendong() {
		long l[] = new long[20];
		for (int i = 0; i < 20; i++) {
			l[i] = 1000;
		}
		if(!bFlagVibrate){
			Vibrator vib = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
			vib.vibrate(l, -1);
			bFlagVibrate = true;
			Log.w("zhendong", "kaishi");
		}
	}

	public void vibCancle(View view) {
		if(bFlagVibrate){
			Vibrator vib = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
			vib.cancel();
			bFlagVibrate = false;
			Log.w("zhendong", "tingzhi");
		}
	}

	private void openGPSSettings() {
		// TODO 提示是否开启GPS，并告知可能产生的网络流量
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
			return;
		}

		Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
	}
	
	private void zhendongState() {
		Log.w("zhendongState", "run");
		bFlagNotify = !bFlagNotify;
		if (!bFlagNotify) {
			vibCancle(null);
			btnAlarm.setText("允许震动");
		} else {
			btnAlarm.setText("取消震动");
		}
		
		Log.w("bFlagNotify", String.valueOf(bFlagNotify));
		Log.w("bFlagStarted", String.valueOf(bFlagStarted));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initLocationClient();
		setLocationClientOption();

		initMapView();

		// FIXME 点击在地图上定位按钮，上次定位的标记仍然存在
		// FIXME 在未定位准确的情况下，点击开始按钮，开始震动
		// TODO 学习javadoc
//		this.openGPSSettings();
		
		notify.mVibrator01 = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);

		btnStart = (Button) findViewById(R.id.btnStart);

		// 开始/停止按钮
		btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!bFlagStarted) {
					setLocationClientOption();
					mLocationClient.start();
					btnStart.setText("停止");
					bFlagStarted = true;
				} else {
					mLocationClient.stop();
					bFlagStarted = false;
					btnStart.setText("开始");
					vibCancle(null);
				}
			}
		});
		
		btnAlarm = (Button) findViewById(R.id.btnAlarm);

		// 取消报警按钮
		btnAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				zhendongState();
			}
		});
		
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				if(bFlagStarted){
//					marker(null);
					TextView tvLat = (TextView)findViewById(R.id.editLat);
					TextView tvLng = (TextView)findViewById(R.id.editLng);
					TextView tv = (TextView)findViewById(R.id.editDistance);
					int d = Integer.parseInt(tv.getText().toString().trim());
					if(Position.lat!=0 && Position.lng!=0){
						Point curPoint = new Point(Position.lat, Position.lng);
						Point toPoint = new Point(Position.latTarget, Position.lngTarget);
						Log.w("distance", String.valueOf(Distance.GetDistance(curPoint, toPoint)));
						if(Distance.GetDistance(curPoint, toPoint)<d)
							if(bFlagNotify && bFlagStarted)
								zhendong();
						else{
							vibCancle(null);
						}
					}
				}
			}
		};
		
		timer.schedule(timerTask, 0, 5000);
	}

	@Override
	public void onDestroy() {
		mLocationClient.stop();
		Log.w("destroy", "oo");
		vibCancle(null);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
