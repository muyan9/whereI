package com.zcy.wherei;

import android.content.Context;

public class JavaScriptInterface {
	Context mContext;
	/** Instantiate the interface and set the context */

	JavaScriptInterface(Context c) {
		mContext = c;
	}

	public void setTargetPosition(double lng, double lat) {
		Position.latTarget = lat;
		Position.lngTarget = lng;
	}
}
