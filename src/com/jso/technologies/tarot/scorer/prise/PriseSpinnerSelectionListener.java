package com.jso.technologies.tarot.scorer.prise;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class PriseSpinnerSelectionListener<T> extends PriseElementSelection<T> implements OnItemSelectedListener {

	public PriseSpinnerSelectionListener(PriseActivity activity, Integer elementType) {
		super(activity, elementType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		setElementValueInCurrentPrise((T)parent.getItemAtPosition(position));
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
