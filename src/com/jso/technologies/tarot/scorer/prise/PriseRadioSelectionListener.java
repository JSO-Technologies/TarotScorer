package com.jso.technologies.tarot.scorer.prise;

import java.util.List;

import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @author Jimmy
 *
 * @param <T> - value type in Prise.java
 */
public class PriseRadioSelectionListener<T> extends PriseElementSelection<T> implements OnCheckedChangeListener {
	private int nthPlayer;
	private List<RadioButton> radios;
	private T value;
	
	public PriseRadioSelectionListener(PriseActivity activity, Integer elementType, List<RadioButton> radiosList, int i, T value) {
		super(activity, elementType);
		this.nthPlayer = i;
		this.radios = radiosList;
		this.value = value;
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean checked) {
		if(checked){
			for(int i = 0; i < radios.size(); ++i){
				if(i != nthPlayer){
					radios.get(i).setChecked(false);
				}
			}
			setElementValueInCurrentPrise(value);
		}
	}

}
