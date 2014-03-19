package com.zcy.wherei;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListener implements BDLocationListener {
	public double lat;
	public double lng;
	public double radius;
	
	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null)
			return;
		
		Position.setValue(location);
		MainActivity.marker(null);
		Log.i("where", Position.debugString());
	}

	public void onReceivePoi(BDLocation poiLocation) {
		// 将在下个版本中去除poi功能
		if (poiLocation == null) {
			return;
		}

		StringBuffer sb = new StringBuffer(256);
		sb.append("Poi time : ");
		sb.append(poiLocation.getTime());
		sb.append("\nerror code : ");
		sb.append(poiLocation.getLocType());
		sb.append("\nlatitude : ");
		sb.append(poiLocation.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(poiLocation.getLongitude());
		sb.append("\nradius : ");
		sb.append(poiLocation.getRadius());
		if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(poiLocation.getAddrStr());
		}

		if (poiLocation.hasPoi()) {
			sb.append("\nPoi:");
			sb.append(poiLocation.getPoi());
		} else {
			sb.append("noPoi information");
		}
		// logMsg(sb.toString());
	}
}
