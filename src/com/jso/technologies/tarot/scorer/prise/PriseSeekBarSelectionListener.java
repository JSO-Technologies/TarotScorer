package com.jso.technologies.tarot.scorer.prise;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.jso.technologies.tarot.scorer.Utils.Constantes;

public class PriseSeekBarSelectionListener extends PriseElementSelection<Integer> implements OnSeekBarChangeListener {
	private boolean updatePointsTextViewWithValue;
	
	public PriseSeekBarSelectionListener(PriseActivity activity, Integer elementType, boolean updateTextView) {
		super(activity, elementType);
		updatePointsTextViewWithValue = updateTextView;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if(updatePointsTextViewWithValue) {
			TextView view = activity.getSelectedPointsTextView();
			view.setText(progress + " / " + (Constantes.TOTAL_POINTS - progress));
		}
		
		setElementValueInCurrentPrise(progress);
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
	}

}
