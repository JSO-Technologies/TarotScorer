package com.jso.technologies.tarot.scorer.common.bean;

import java.util.ArrayList;
import java.util.List;


/**
 * Bean Option
 * @author Jimmy
 *
 */
public class Option {
	public static final String SEPARATOR = ";";
	private Integer id;
	private String value;

	public Option(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public List<Integer> getPlayerIdList() {
		List<Integer> result = null;
		if(value != null) {
			result = new ArrayList<Integer>();
			String[] splittedValue = value.split(SEPARATOR);
			for(String nextId : splittedValue) {
				if(!"".equals(nextId.trim())) {
					result.add(Integer.parseInt(nextId));
				}
			}
		}
		return result;
	}
}
