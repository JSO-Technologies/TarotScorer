package com.jso.technologies.tarot.scorer.Utils;

import android.app.Activity;
import android.widget.Toast;

public class MessageUtils {
	public static void showToast(Activity activity, Integer messageId) {
		String message = activity.getString(messageId);
		Toast toast = Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}
	
	public static void showToast(Activity activity, String message) {
		Toast toast = Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}
}
