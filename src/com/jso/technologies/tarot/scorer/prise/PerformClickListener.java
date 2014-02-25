package com.jso.technologies.tarot.scorer.prise;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

public class PerformClickListener implements OnClickListener {

	private RadioButton radioButton;

	public PerformClickListener(RadioButton radioButton) {
		this.radioButton = radioButton;
	}

	@Override
	public void onClick(View arg0) {
		radioButton.performClick();
	}

}
