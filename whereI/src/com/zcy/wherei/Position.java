package com.zcy.wherei;

import com.baidu.location.BDLocation;

public class Position {

	public static String time = "";
	public static int code = 0;
	public static double lat = 0;
	public static double lng = 0;
	public static double radius = 0;
	public static double speed = 0;
	public static String address = "";
	public static int numSatellite = 0;
	
	public static double lngTarget = 0;
	public static double latTarget = 0;

	public static void setValue(BDLocation location) {
		time = location.getTime();
		code = location.getLocType();
		lat = location.getLatitude();
		lng = location.getLongitude();
		radius = location.getRadius();
		speed = location.getSpeed();
		numSatellite = location.getSatelliteNumber();
		address = location.getAddrStr();
	}

	public static String debugString() {
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(time);
		sb.append("\nerror code : ");
		sb.append(code);
		sb.append("\nlat : ");
		sb.append(lat);
		sb.append("\nlng : ");
		sb.append(lng);
		sb.append("\nradius : ");
		sb.append(radius);

		if (code == BDLocation.TypeGpsLocation) {
			sb.append("\nspeed : ");
			sb.append(speed);
			sb.append("\nsatellite : ");
			sb.append(numSatellite);
		} else if (code == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(address);
		}
		return sb.toString();
	}
}
