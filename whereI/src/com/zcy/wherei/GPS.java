package com.zcy.wherei;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class GPS {
	private LocationManager locationManager;
	private LocationListener locationListener = null;
	private Activity activity;
	private String provider;
	
	public void setLocationListener(LocationListener locationListener){
		this.locationListener = locationListener;
		locationManager.requestLocationUpdates(provider, 3000, 0, locationListener);
//		locationManager.removeGpsStatusListener(listener)
	}
	public void setActivity(Activity activity){
		this.activity = activity;
	}
	
	private void init(){
		this.openGPSSettings();
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) activity.getSystemService(context);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// 表明所要求的经纬度的精度
		criteria.setAltitudeRequired(false); // 高度信息是否需要提供
		criteria.setBearingRequired(false); // 压力（气压？）信息是否需要提供
		criteria.setCostAllowed(true); // 是否会产生费用
		criteria.setPowerRequirement(Criteria.POWER_LOW);// 最大需求标准
		provider = locationManager.getBestProvider(criteria, true);
	}
	public GPS(){
		init();
	}
	
	public GPS(Activity activity){
		this.setActivity(activity);
		init();
	}
	
	private void openGPSSettings() {
		LocationManager alm = (LocationManager) activity
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(activity, "GPS模块正常", Toast.LENGTH_SHORT).show();
			return;
		}

		Toast.makeText(activity, "请开启GPS！", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		activity.startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
	}

	public Location getLocation() {
		return locationManager.getLastKnownLocation(provider);
	}
	
//	private final LocationListener locationListener = new LocationListener() {
//		public void onLocationChanged(Location location) {
//			updateWithNewLocation(location);
//		}
//
//		// Provider被disable时触发此函数，比如GPS被关闭
//		public void onProviderDisabled(String provider) {
//			updateWithNewLocation(null);
//		}
//
//		// Provider被enable时触发此函数，比如GPS被打开
//		public void onProviderEnabled(String provider) {
//		}
//
//		// Provider的转态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//		}
//	};
}
