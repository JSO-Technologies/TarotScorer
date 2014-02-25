package com.jso.technologies.tarot.scorer.Utils;

import java.util.HashMap;
import java.util.Map;

import com.jso.technologies.tarot.scorer.R;

import android.app.Activity;
import android.app.ProgressDialog;

public class DialogHandler {
	private static Map<Activity, ProgressDialog> waitingDialog = new HashMap<Activity, ProgressDialog>();
	
	public static void dismissWaitingDialog(Activity activity) {
		if(waitingDialog.get(activity) != null) {
			waitingDialog.get(activity).dismiss();
			waitingDialog.remove(activity);
		}
	}

	public static void showWaitingDialog(Activity activity) {
		dismissWaitingDialog(activity);
		waitingDialog.put(activity, ProgressDialog.show(activity, "", activity.getString(R.string.waiting), true));
	}

}
