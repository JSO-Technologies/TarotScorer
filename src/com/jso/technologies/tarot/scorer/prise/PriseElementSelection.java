package com.jso.technologies.tarot.scorer.prise;

import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.common.bean.Player;
import com.jso.technologies.tarot.scorer.common.bean.Prise;
import com.jso.technologies.tarot.scorer.common.enumeration.ChelemEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PetiteAuBoutEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PoigneeEnum;
import com.jso.technologies.tarot.scorer.common.enumeration.PriseEnum;


public class PriseElementSelection<T> {
	protected PriseActivity activity;
	private Integer elementType;
	
	public PriseElementSelection(PriseActivity priseActivity, Integer type) {
		activity = priseActivity;
		elementType = type;
	}
	
	protected void setElementValueInCurrentPrise(T value) {
		Prise currentPrise = activity.getCurrentPrise();
		
		switch (elementType) {
		case Constantes.PRENEUR_ELEMENT:
			currentPrise.setPreneur((Player) value);
			break;

		case Constantes.CALL_ELEMENT:
			currentPrise.setAppel((Player) value);
			break;
			
		case Constantes.PRISE_ELEMENT:
			currentPrise.setPrise((PriseEnum) value);
			break;
			
		case Constantes.POINTS_ELEMENT:
			currentPrise.setPoints((Integer) value);
			break;
			
		case Constantes.OUDLERS_ELEMENT:
			currentPrise.setNbOudlers((Integer) value);
			break;
			
		case Constantes.PETITE_AU_BOUT_ELEMENT:
			currentPrise.setPetiteAuBout((PetiteAuBoutEnum) value);
			break;
			
		case Constantes.POIGNEE_ELEMENT:
			currentPrise.setPoignee((PoigneeEnum) value);
			break;
			
		case Constantes.CHELEM_ELEMENT:
			currentPrise.setChelem((ChelemEnum) value);
			break;
					
		default:
			break;
		}
	}

}
