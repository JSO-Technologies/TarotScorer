package com.jso.technologies.tarot.scorer.common.enumeration;

import android.app.Activity;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;

public enum PriseEnum {
	PETITE(Constantes.PRISE_ID), GARDE(Constantes.GARDE_ID), GARDE_SANS(Constantes.GARDE_SANS_ID), GARDE_CONTRE(Constantes.GARDE_CONTRE_ID);

	private Integer id;
	private String name;

	private static Activity activity;

	private PriseEnum(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		if(name == null || "".equals(name)) {
			PriseEnum.initEnumNames(activity);
		}
		return name;
	}

	public static PriseEnum fromValue(Integer id) {
		if (id != null) {
			for (PriseEnum prise : values()) {
				if (prise.getId().equals(id)) {
					return prise;
				}
			}
		}
		return null;
	}

	public static void initEnumNames(Activity activityParam) {
		if(activityParam != null) {
			activity = activityParam;
			PriseEnum.PETITE.setName(activity.getString(R.string.petite));
			PriseEnum.GARDE.setName(activity.getString(R.string.garde));
			PriseEnum.GARDE_SANS.setName(activity.getString(R.string.garde_sans));
			PriseEnum.GARDE_CONTRE.setName(activity.getString(R.string.garde_contre));

			PetiteAuBoutEnum.NON.setName(activity.getString(R.string.no));
			PetiteAuBoutEnum.OUI.setName(activity.getString(R.string.yes));
			PetiteAuBoutEnum.PERDU.setName(activity.getString(R.string.lost));

			PoigneeEnum.NON.setName(activity.getString(R.string.no));
			PoigneeEnum.SIMPLE.setName(activity.getString(R.string.simple_poignee));
			PoigneeEnum.DOUBLE.setName(activity.getString(R.string.double_poignee));
			PoigneeEnum.TRIPLE.setName(activity.getString(R.string.triple_poignee));

			ChelemEnum.NON.setName(activity.getString(R.string.no_chelem));
			ChelemEnum.NOT_ANNOUNCED.setName(activity.getString(R.string.not_announced_chelem));
			ChelemEnum.ANNOUNCED.setName(activity.getString(R.string.announced_chelem));
			ChelemEnum.LOST.setName(activity.getString(R.string.chelem_lost));
		}
	}
}
