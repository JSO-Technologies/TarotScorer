package com.jso.technologies.tarot.scorer.prise;

import com.jso.technologies.tarot.scorer.Utils.Constantes;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;

public class PriseAdjustementPointListener implements OnClickListener {

	public static final String MINUS = "MINUS";
	public static final String PLUS = "PLUS";
	private PriseActivity activity;
	private String operation;

	public PriseAdjustementPointListener(PriseActivity priseActivity, String operation) {
		this.activity = priseActivity;
		this.operation = operation;
	}

	@Override
	public void onClick(View arg0) {
		SeekBar pointsSeekBar = activity.getPointsSeekBar();
		Integer points = pointsSeekBar.getProgress();
		
		if(MINUS.equals(operation) && points != 0) {
			pointsSeekBar.setProgress(points - 1);
		}
		else if(PLUS.equals(operation) && points != Constantes.TOTAL_POINTS) {
			pointsSeekBar.setProgress(points + 1);
		}
	}

}
