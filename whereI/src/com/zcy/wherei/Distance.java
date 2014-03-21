package com.zcy.wherei;

public class Distance {

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		double lat1 = 43.79832744677656;
//		double lng1 = 87.55322456359863;
//		double lat2 = 43.794765163184934;
//		double lng2 = 87.54328318786621;
//		
//		double r = Distance.GetDistance(new Point(lat1, lng1), new Point(lat2, lng2));
//		System.out.println(r);
//	}
	private static double EARTH_RADIUS = 6378137;//地球半径
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}

	public static double GetDistance(Point p1, Point p2)
	{
	   double radLat1 = rad(p1.lat);
	   double radLat2 = rad(p2.lat);
	   double a = radLat1 - radLat2;
	   double b = rad(p1.lng) - rad(p2.lng);

	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   s = Math.round(s);
	   return s;
	}
}
