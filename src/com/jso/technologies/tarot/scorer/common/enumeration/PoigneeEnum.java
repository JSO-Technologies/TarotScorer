package com.jso.technologies.tarot.scorer.common.enumeration;

import com.jso.technologies.tarot.scorer.Utils.Constantes;

public enum PoigneeEnum {
	NON(Constantes.POIGNEE_NO_ID), SIMPLE(Constantes.POIGNEE_SIMPLE_ID), DOUBLE(Constantes.POIGNEE_DOUBLE_ID), TRIPLE(Constantes.POIGNEE_TRIPLE_ID);

	private Integer id;
	private String name;

	private PoigneeEnum(Integer id) {
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

	public static PoigneeEnum fromValue(Integer id) {
		if (id != null) {
			for (PoigneeEnum poignee : values()) {
				if (poignee.getId().equals(id)) {
					return poignee;
				}
			}
		}
		return null;
	}
}
