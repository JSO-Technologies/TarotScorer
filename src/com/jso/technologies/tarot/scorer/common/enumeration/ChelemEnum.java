package com.jso.technologies.tarot.scorer.common.enumeration;

import com.jso.technologies.tarot.scorer.Utils.Constantes;

public enum ChelemEnum {
	NON(Constantes.CHELEM_NO_ID), NOT_ANNOUNCED(Constantes.CHELEM_NOT_ANNOUNCED_ID), ANNOUNCED(Constantes.CHELEM_ANNOUNCED_ID), LOST(Constantes.CHELEM_LOST_ID);
	
	
	private Integer id;
	private String name;
	
	private ChelemEnum(Integer id) {
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
		return name;
	}
	
	public static ChelemEnum fromValue(Integer id) {
		if (id != null) {
			for (ChelemEnum chelem : values()) {
				if (chelem.getId().equals(id)) {
					return chelem;
				}
			}
		}
		return null;
	}
}
