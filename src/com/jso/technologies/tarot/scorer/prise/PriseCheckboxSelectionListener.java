package com.jso.technologies.tarot.scorer.prise;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PriseCheckboxSelectionListener<T> extends PriseElementSelection<T> implements OnCheckedChangeListener {
	private T value;
	
	public PriseCheckboxSelectionListener(PriseActivity activity, Integer elementType, T value) {
		super(activity, elementType);
		this.value = value;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		remove = !isChecked;
		setElementValueInCurrentPrise(value);
	}

}
