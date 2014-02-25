package com.jso.technologies.tarot.scorer.common.enumeration;

import com.jso.technologies.tarot.scorer.Utils.Constantes;

public enum PetiteAuBoutEnum {
	NON(Constantes.PETITE_BOUT_NO_ID), OUI(Constantes.PETITE_BOUT_YES_ID), PERDU(Constantes.PETITE_BOUT_LOST_ID);
	
	private Integer id;
	private String name;
	
	private PetiteAuBoutEnum(Integer id) {
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
	
	public static PetiteAuBoutEnum fromValue(Integer id) {
		if (id != null) {
			for (PetiteAuBoutEnum petiteAuBout : values()) {
				if (petiteAuBout.getId().equals(id)) {
					return petiteAuBout;
				}
			}
		}
		return null;
	}
}
